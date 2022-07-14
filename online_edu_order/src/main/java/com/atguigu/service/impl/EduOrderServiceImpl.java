package com.atguigu.service.impl;

import com.atguigu.entity.EduOrder;
import com.atguigu.entity.EduPayLog;
import com.atguigu.mapper.EduOrderMapper;
import com.atguigu.service.EduOrderService;
import com.atguigu.service.EduPayLogService;
import com.atguigu.utils.HttpClient;
import com.atguigu.utils.HttpClientUtils;
import com.atguigu.utils.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-12
 */
@Service
public class EduOrderServiceImpl extends ServiceImpl<EduOrderMapper, EduOrder> implements EduOrderService {
    @Autowired
    private EduPayLogService payLogService;
    //支付id
    @Value("${wx.pay.app_id}")
    private String WX_PAY_APP_ID;
    //商户号
    @Value("${wx.pay.mch_id}")
    private String WX_PAY_MCH_ID;
    //终端ip地址 服务所在ip地址
    @Value("${wx.pay.spbill_create_ip}")
    private String WX_PAY_SPBILL_IP;
    //支付后回调
    @Value("${wx.pay.notify_url}")
    private String WX_PAY_NOTIFY_URL;
    //密钥 签名
    @Value("${wx.pay.xml_key}")
    private String WX_PAY_XML_KEY;

    /**
     * 创建订单
     *
     * @param courseId
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberId) {
        //随机生成一个带时间的订单号
        String orderNo = OrderNoUtil.getOrderNo();
        //创建订单信息对象
        EduOrder order = new EduOrder();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle("尚硅谷Spring5视频教程");
        //课程封面
        order.setCourseCover("https://img11.360buyimg.com/cms/jfs/t1/210187/16/19810/1368582/6247ff52Ee33868cd/47df0a2bbe369a79.jpg");
        //老师名称
        order.setTeacherName("王老师");
        //会员昵称
        order.setNickName("隔壁老樊");
        //会员id
        order.setMemberId(memberId);
        //会员手机号
        order.setMobile("13177586278");
        //支付总金额
        order.setTotalFee(new BigDecimal(0.01));
        //支付类型,1微信,2支付宝
        order.setPayType(1);
        //支付状态,0未支付,1已支付
        order.setStatus(0);
        //把订单信息存到订单表中
        baseMapper.insert(order);
        //f.返回订单号给前端
        return orderNo;
    }

    /**
     * 使用订单信息向微信服务器下单,生成二维码
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createPayQrCode(String orderNo) throws Exception {
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        //拿到订单信息
        EduOrder order = baseMapper.selectOne(wrapper);
        //a.拼接组织好微信下单需要的数据
        Map<String, String> requestParams = new HashMap<>();
        //公众账号ID
        requestParams.put("appid", WX_PAY_APP_ID);
        //商户号
        requestParams.put("mch_id", WX_PAY_MCH_ID);
        //随机字符串,使用微信提供的工具类
        requestParams.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述
        requestParams.put("body", order.getCourseTitle());//拿到订单信息
        //商户订单号
        requestParams.put("out_trade_no", orderNo);
        //标价金额
        /**
         * 两笔单位都是分
         * order.getTotalFee():获得总费用
         * multiply:乘法
         */
        String totalFee = order.getTotalFee().multiply(new BigDecimal(100)).intValue() + "";
        requestParams.put("total_fee", totalFee);
        //终端IP
        requestParams.put("spbill_create_ip", WX_PAY_SPBILL_IP);
        //通知地址,扫完后跳转的地址
        requestParams.put("notify_url", WX_PAY_NOTIFY_URL);
        //交易类型
        requestParams.put("trade_type", "NATIVE");

        //b.该接口接受的是xml
        String xmlParams = WXPayUtil.generateSignedXml(requestParams, WX_PAY_XML_KEY);
        //c.调用接口去微信下单
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpClient.setXmlParam(xmlParams);
        httpClient.setHttps(true);
        httpClient.post();
        //d.得到返回值 进行解析
        String content = httpClient.getContent();
        Map<String, String> txRetMap = WXPayUtil.xmlToMap(content);
        String qrCodeUrl = txRetMap.get("code_url");

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("qrCodeUrl", qrCodeUrl);
        retMap.put("orderNo", orderNo);
        retMap.put("totalFee", order.getTotalFee());
        //暂时不知道为什么要传
        retMap.put("courseId", order.getCourseId());
        return retMap;
    }

    @Override
    public Map<String, String> queryPayState(String orderNo) {
        try {
            //把参数封装成一个map接口 然后在进行转换为xml
            HashMap<String, String> requestParam = new HashMap<>();
            //公众账号ID
            requestParam.put("appid", WX_PAY_APP_ID);
            //商户号
            requestParam.put("mch_id", WX_PAY_MCH_ID);
            //商户订单号
            requestParam.put("out_trade_no", orderNo);
            //随机字符串
            requestParam.put("nonce_str", WXPayUtil.generateNonceStr());

            String xmlParam = WXPayUtil.generateSignedXml(requestParam, WX_PAY_XML_KEY);
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            //对请求接口添加参数
            client.setXmlParam(xmlParam);
            client.setHttps(true);
            client.post();
            //请求之后微信那边给的响应信息
            String content = client.getContent();
            Map<String, String> retMap = WXPayUtil.xmlToMap(content);
            return retMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Transactional
    @Override
    public void updateOrderState(Map<String, String> txRetMap) throws ParseException {
        //a.修改商户系统订单状态
        //商户系统订单id就是out_trade_no
        String orderNo = txRetMap.get("out_trade_no");
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        EduOrder order = baseMapper.selectOne(wrapper);
        order.setStatus(1);
        baseMapper.updateById(order);

        //先查询支付日志里面是否有记录
        QueryWrapper<EduPayLog> logWrapper = new QueryWrapper<EduPayLog>();
        logWrapper.eq("order_no",orderNo);
        EduPayLog payLog = payLogService.getOne(logWrapper);
        if(payLog==null){
            //b.往支付日志里面添加记录
            payLog = new EduPayLog();
            payLog.setOrderNo(orderNo);
            //20220712142214
            String timeEnd = txRetMap.get("time_end");
            Date payTime=new SimpleDateFormat("yyyyMMddHHmmss").parse(timeEnd);
            payLog.setPayTime(payTime);
            payLog.setTotalFee(order.getTotalFee());
            //微信那边的订单id
            payLog.setTransactionId(txRetMap.get("transaction_id"));
            payLog.setTradeState(txRetMap.get("trade_state"));
            payLog.setPayType(1);
            payLogService.save(payLog);
        }
    }
}

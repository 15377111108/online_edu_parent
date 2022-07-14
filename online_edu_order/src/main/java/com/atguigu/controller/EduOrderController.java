package com.atguigu.controller;


import com.atguigu.entity.EduOrder;
import com.atguigu.response.RetVal;
import com.atguigu.service.EduOrderService;
import com.atguigu.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class EduOrderController {
    @Autowired
    private EduOrderService orderService;
    /**
     * 根据课程id下单
     * @param courseId
     * @param request
     * @return
     */
    @GetMapping("/createOrder/{courseId}")
    public RetVal createOrder(@PathVariable String courseId, HttpServletRequest request){
        //获取令牌中的会员信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //订单id 需要课程id和会员客户id才能确定订单id
        String orderNo = orderService.createOrder(courseId,memberId);
        return RetVal.success().data("orderNo",orderNo);
    }
    /**
     * 根据订单号查询订单信息
     */
    @GetMapping("/getOrderByOrderNo/{orderNo}")
    public RetVal getOrderByOrderNo(@PathVariable String orderNo){
        QueryWrapper<EduOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        //根据订单号查询订单信息
        EduOrder order = orderService.getOne(wrapper);
        return RetVal.success().data("orderInfo",order);
    }
    /**
     * 用订单号微信服务器下单生成二维码
     */
    @GetMapping("/createPayQrCode/{orderNo}")
    public RetVal createPayQrCode(@PathVariable String orderNo) throws Exception {
        Map<String,Object> map =  orderService.createPayQrCode(orderNo);
        return RetVal.success().data(map);
    }
    @GetMapping("queryPayState/{orderNo}")
    public RetVal queryPayState(@PathVariable String orderNo) throws Exception {
        Map<String, String> retMap = orderService.queryPayState(orderNo);
        if(retMap.get("trade_state").equals("SUCCESS")){
            //修改订单
            orderService.updateOrderState(retMap);
            return RetVal.success().message("支付成功");
        }else{
            return RetVal.error().message("待支付");
        }
    }
}


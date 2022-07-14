package com.atguigu.controller;


import com.atguigu.entity.MemberCenter;
import com.atguigu.service.MemberCenterService;
import com.atguigu.utils.HttpClientUtils;
import com.atguigu.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;
/**
 * Date:2022/7/11
 *
 * @author:yz
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Value("${wx.open.app_id}")
    private String WX_OPEN_APPID;
    @Value("${wx.open.app_secret}")
    private String WX_OPEN_APPSECRET;
    @Value("${wx.open.redirect_url}")
    private String WX_OPEN_REDIRECT_URL;

    @Autowired
    private MemberCenterService memberCenterService;

    //1.生成二维码
    @GetMapping("login")
    public String qrCode() throws Exception{
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String state="atguigu";
        //对回调路径进行编码
        String encodeUrl = URLEncoder.encode(WX_OPEN_REDIRECT_URL, "utf-8");
        //拼接字符串
        String qrCodeUrl = String.format(baseUrl, WX_OPEN_APPID, encodeUrl, state);
        return "redirect:"+qrCodeUrl;
    }
    //2.微信扫描成功之后的回调接口
    @GetMapping("callback")
    public String callback(String code) throws Exception{
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        baseAccessTokenUrl = String.format(baseAccessTokenUrl, WX_OPEN_APPID,WX_OPEN_APPSECRET , code);
        //模拟发起http请求
        String retVal = HttpClientUtils.get(baseAccessTokenUrl);
        //通过把字符串转换为json 转换为map
        Gson gson = new Gson();
        HashMap retMap = gson.fromJson(retVal, HashMap.class);
        String accessToken=(String)retMap.get("access_token");
        String openid=(String)retMap.get("openid");
        //通过accessToken+openid获取微信个人信息
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        userInfoUrl = String.format(userInfoUrl, accessToken,openid);
        //模拟发起http请求
        String userInfoVal = HttpClientUtils.get(userInfoUrl);
        HashMap userInfoMap = gson.fromJson(userInfoVal, HashMap.class);
        String nickname=(String)userInfoMap.get("nickname");
        String headimgurl=(String)userInfoMap.get("headimgurl");
        //查询一下数据库中是否已经存在该用户信息
        QueryWrapper<MemberCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        MemberCenter existMemberCenter = memberCenterService.getOne(wrapper);
        if(existMemberCenter==null){
            //把个人信息存储在数据库中
            existMemberCenter = new MemberCenter();
            existMemberCenter.setNickname(nickname);
            existMemberCenter.setAvatar(headimgurl);
            existMemberCenter.setOpenid(openid);
            memberCenterService.save(existMemberCenter);
        }
        String token = JwtUtils.geneJsonWebToken(existMemberCenter);
        return "redirect:http://127.0.0.1:3000?token="+token;
    }

}

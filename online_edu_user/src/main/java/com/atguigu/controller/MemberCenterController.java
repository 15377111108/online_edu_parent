package com.atguigu.controller;


import com.atguigu.response.MemberCenterVo;
import com.atguigu.response.RetVal;
import com.atguigu.service.MemberCenterService;
import com.atguigu.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/member/center")
//跨域问题
@CrossOrigin
public class MemberCenterController {
    @Autowired
    private MemberCenterService memberCenterService;

    /**
     * 统计当天注册人数
     * @return
     */
    @GetMapping("queryRegisterNum/{day}")
    public RetVal queryRegisterNum(@PathVariable String day){
        Integer registerNum = memberCenterService.queryRegisterNum(day);
        return RetVal.success().data("registerNum",registerNum);
    }
    //2.通过token取得用户信息
    @GetMapping("getUserInfoByToken/{token}")
    public RetVal getUserInfoByToken(@PathVariable("token") String token){
        MemberCenterVo memberCenterVo = new MemberCenterVo();
        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String id = (String)claims.get("id");
        memberCenterVo.setId(id);
        memberCenterVo.setNickname(nickname);
        memberCenterVo.setAvatar(avatar);
        return RetVal.success().data("memberCenterVo",memberCenterVo);
    }

}


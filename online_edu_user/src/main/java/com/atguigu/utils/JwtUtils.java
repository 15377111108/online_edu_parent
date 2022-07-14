package com.atguigu.utils;

import com.atguigu.entity.MemberCenter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtUtils {
    public static final String SUBJECT = "guli";
    //秘钥
    public static final String APPSECRET = "guli";
    public static final long EXPIRE = 1000 * 60 * 30;  //过期时间，毫秒，30分钟


    /**
     * 根据对象生成jwt的token字符串
     */
    public static String geneJsonWebToken(MemberCenter member) {

        if (member == null || StringUtils.isEmpty(member.getId())
                || StringUtils.isEmpty(member.getNickname())
                || StringUtils.isEmpty(member.getAvatar())) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", member.getId())
                .claim("nickname", member.getNickname())
                .claim("avatar", member.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 校验jwt token
     */
    public static Claims checkJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
        return claims;
    }

    public static void main(String[] args) {
        MemberCenter memberCenter = new MemberCenter();
        memberCenter.setId("8");
        memberCenter.setAvatar("xxxx");
        memberCenter.setNickname("zhangsan");
        String token = geneJsonWebToken(memberCenter);
        System.out.println(token);

        Claims claims = checkJWT(token);
        String id =(String) claims.get("id");
        String avatar =(String) claims.get("avatar");
        String nickname =(String) claims.get("nickname");
        System.out.println(id);
        System.out.println(avatar);
        System.out.println(nickname);
    }


}

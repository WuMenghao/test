package com.brillilab.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class JwtTest {
    @Test
    public void createJwt() throws UnsupportedEncodingException {
    //https://blog.csdn.net/niceyoo/article/details/90743199
                String token = Jwts.builder()
                //主题 放入用户名
                .setSubject("niceyoo")
                //自定义属性 放入用户拥有请求权限
                .claim("authorities","admin")
                //失效时间
                        .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                //签名算法和密钥
                        .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString("tmax".getBytes("UTF-8")))
                        .compact();
                System.out.println(token);


    }
}

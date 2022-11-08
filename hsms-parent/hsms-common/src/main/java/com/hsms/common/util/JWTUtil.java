package com.hsms.common.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author haotchen
 * @time 2022/11/8-12:57
 */
public class JWTUtil {

    /**
     * 封装body 和过期时间,返回Token字符串
     * @param body     token 的载体,封装用户的信息
     * @param secret    token 的秘钥
     * @param millisecond  token 的过期时间
     * @return String
     */
    public static String jwtBuilder(Map body, String secret, Long millisecond) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date()) // 创建签证时间
                .setClaims(body) // 设置自定义内容,传参是map
                .setExpiration(new Date(System.currentTimeMillis() + millisecond)) // 设置过期时间,当前时间加上一个时间值 毫秒
                .signWith(SignatureAlgorithm.HS256,secret);// ** 主要 加密算法和盐

        // 生成并令牌
        return jwtBuilder.compact();
    }

    /**
     * 解析Token 检查是否过期,获取body内容
     * @param token   Token令牌
     * @param secret   token秘钥
     * @return Jws
     */
    public static Jws<Claims> parseToken(String token, String secret){
        return  Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
    }

}

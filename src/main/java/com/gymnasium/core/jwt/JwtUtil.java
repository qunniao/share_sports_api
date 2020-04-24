package com.gymnasium.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
@Component
public class JwtUtil {
    //token过期时间
    private static String tokenExpireTime ;

    @Value("${jwt.tokenExpireTime}")
    public void setTokenExpireTime(String tokenExpireTime) {
        JwtUtil.tokenExpireTime = tokenExpireTime;
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static Map verify(String token, String username, String secret) {

        Map result = new HashMap<String, Object>(2);

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", username)
                    .build();
            verifier.verify(token);
            result.put("isSuccess", true);
            result.put("ex", null);
//            return true;
        } catch (Exception exception) {
            result.put("isSuccess", false);
            result.put("exception", exception);
//           return false;
        }
        return result;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成签名,30min后过期
     *
     * @param userId 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String userId, String secret) {
        try {
            //token过期时间 单位为毫秒， 24 小时
            Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 24);
            //密码MD5加密
//            Object md5Password = new SimpleHash("MD5", secret, username, 1);
//            Algorithm algorithm = Algorithm.HMAC256(String.valueOf(md5Password));
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
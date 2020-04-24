package com.gymnasium.Util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final long EXPIRE_TIME = 60 * 60 * 1000 * 24 * 356;

    private static final String TOKEN_SECRET = "n61e825d105445m3l258u43q2n2w4z511";

    private static final String GYMSHOPID_KEY = "gymShopId";

    /**
     * 生成签名,EXPIRE_TIME后过期
     * @param username 用户名
     * @return 加密的token
     */
    public static String sign(String username, int userId, String passWord, String identity) {

        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //附带username,userId信息,生成签名
            return JWT.create().withHeader(header).withClaim("account", username)
                    .withClaim("userId", userId)
                    .withClaim("passWord", passWord)
                    .withClaim("identity", identity)
                    .withExpiresAt(date)
                    .sign(algorithm);

        } catch (Exception e) {
            return null;
        }

    }


    public static Map<String, String> verifys(String token) {
        Map<String, String> map = new HashMap<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            map.put("identity", jwt.getClaim("identity").asString());
            map.put("account", jwt.getClaim("account").asString());
            map.put("userId", jwt.getClaim("userId").asString());
            map.put("passWord", jwt.getClaim("passWord").asString());
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return data
     */
    public static String getData(String token, String data) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(data).asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取数据
     * @param token
     * @return
     */
    public static Integer getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asInt();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密字符串
     * @param obj
     * @return
     */
    public static String sign(String obj) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String sign = JWT.create().withClaim(GYMSHOPID_KEY, obj).sign(algorithm);

            return sign;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密字符串
     * @param sing 需要解密字符串
     * @return
     */
    public static String verify(String sing) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(sing);

            return jwt.getClaim(GYMSHOPID_KEY).asString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        String sign = sign("aaaaa");
        System.out.println(sign);
        String val = verify(sign);
        System.out.println(val);
    }

}

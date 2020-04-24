package com.gymnasium.core.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by Administrator on 2019/1/6.
 */
@Data
public class JwtToken implements AuthenticationToken {
    /**
     * 秘钥
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

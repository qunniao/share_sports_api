package com.gymnasium.core.jwt;

import cn.hutool.db.nosql.redis.RedisDS;
import com.gymnasium.login.Service.UserManageService;
import com.gymnasium.personnel.PO.UserManagePO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2019/1/6.
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    private UserManageService userManageService;

    public JwtFilter(UserManageService userManageService){

        this.userManageService = userManageService;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse){
        //获取请求头token
        AuthenticationToken token = this.createToken(servletRequest, servletResponse);
        if (token.getPrincipal() == null) {
            handler401(servletResponse, 100401, "无权访问，当前是匿名访问，请先登录！");
            return false;
        } else {
            try {
                this.getSubject(servletRequest, servletResponse).login(token);
                return true;
            } catch (Exception e) {
                String msg = e.getMessage();
                //token错误
                if (msg.contains("incorrect")) {
                    handler401(servletResponse, 100401, msg);
                    return false;
                    //token过期
               } else if (msg.contains("expired")) {

////                    尝试刷新token
//                    if (this.refreshToken(servletRequest, servletResponse)) {
//                        return true;
//                    } else {
                        handler401(servletResponse, 100402, "token已过期,请重新登录");
                        return false;
                }
                handler401(servletResponse, 100403, msg);
                return false;
            }
        }
    }

    /**
     * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     * @param servletRequest
     * @param servletResponse
     * @return
     */
    private boolean refreshToken(ServletRequest servletRequest, ServletResponse servletResponse) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取header，tokenStr
        String oldToken = request.getHeader("Authorization");
        String userId = JwtUtil.getUsername(oldToken);
        String key = "token_jwt_" + userId;
        //获取redis tokenStr
//        String redisUserInfo = jedis.get(key);
        String redisUserInfo = "";
        if (redisUserInfo != null) {
            if (oldToken.equals(redisUserInfo)) {
                UserManagePO userManagePO = userManageService.queryUserByUserId(userId);
                //重写生成token(刷新)
                String newTokenStr = JwtUtil.sign(userManagePO.getUserId(),userManagePO.getPassWord());
                JwtToken jwtToken = new JwtToken(newTokenStr);
//                jedis.setex(key,30*60, newTokenStr);
                SecurityUtils.getSubject().login(jwtToken);
                response.setHeader("Authorization", newTokenStr);
                return true;
            }
        }
        return false;
    }

    /**
     * token有问题
     *
     * @param response
     * @param code
     * @param msg
     */
    private void handler401(ServletResponse response, int code, String msg) {
        try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.getWriter().write("{\"code\":" + code + ", \"msg\":\"" + msg + "\"}");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 支持跨域
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Origin,X-Requested-With,Content-Type,Accept");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(httpServletRequest, httpServletResponse);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("Authorization");
        return new JwtToken(token);
    }
}

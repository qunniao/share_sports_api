package com.gymnasium.core.wxpay;

import cn.jiguang.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 王志鹏
 * @title: IpUtils
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/28 11:00
 */
public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}

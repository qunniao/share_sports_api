package com.gymnasium.Enums;

/**
 * @author 王志鹏
 * @title: UrlEnums
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/3/29 16:04
 */
public enum  UrlEnums {

    GET_AUTH_ACCESSTOKEN("https://api.weixin.qq.com/wxa/getpaidunionid","auth.getAccessToken"),
    CODE2SESSION("https://api.weixin.qq.com/sns/jscode2session","code2Session"),
    GET_ACCESSTOKEN("https://api.weixin.qq.com/cgi-bin/token","getAccessToken"),
    GET_ACCESS_TOKEN("https://api.weixin.qq.com/sns/oauth2/access_token", "get_Access_token"),
    REFRESH_ACCESS_TOKEN("https://api.weixin.qq.com/sns/oauth2/refresh_token", "refresh_Access_token"),
    CHEEK_ACCESS_TOKEN("https://api.weixin.qq.com/sns/auth", "get_Access_token"),
    GET_USERFOR_WX("https://api.weixin.qq.com/sns/userinfo", "getUserForWX")
    ;

    private String url;//url

    private String remarks;//备注

    UrlEnums(String url, String remarks) {
        this.url = url;
        this.remarks = remarks;
    }

    public String getUrl() {
        return url;
    }

    public String getRemarks() {
        return remarks;
    }
}

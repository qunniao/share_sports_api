package com.gymnasium.login.VO;

import com.gymnasium.Util.ReadProperties;
import lombok.Data;

/**
 * @author 王志鹏
 * @title: WxloginVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/3/28 17:52
 */

@Data
public class WxloginVO {

    public static final String Appid = ReadProperties.getProperties_1("", "Appid");
    public static final String AppSecret = ReadProperties.getProperties_1("", "AppSecret");
    public static final String WEB_ID = ReadProperties.getProperties_1("", "WebId");
    public static final String WEB_SECRET = ReadProperties.getProperties_1("", "WebSecret");
    public static final String mch_id = ReadProperties.getProperties_1("", "mch_id");

    private String appid;
    private String secret;
    private String code;
    private String openid;
    private String access_token;
    private String refresh_token;

}

package com.gymnasium.user.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 边书恒
 * @Title: WxDTO
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/22 20:51
 */
@Data
public class WxDTO implements Serializable {

    private String country;
    private String unionId;
    private String watermark;
    private String gender;
    private String province;
    private String city;
    private String avatarUrl;
    private String openId;
    private String nickName;
    private String language;


}

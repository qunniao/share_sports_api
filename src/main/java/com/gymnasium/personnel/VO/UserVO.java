package com.gymnasium.personnel.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: UserVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 15:13
 */

@Data
public class UserVO implements Serializable {

    private Integer uid;

    @Column(name = "oneUserId")
    @ApiModelProperty(value = "一级代理", name = "oneUserId", example = "LF_046587")
    private String oneUserId;

    @Column(name = "towUsuerId")
    @ApiModelProperty(value = "二级代理", name = "towUsuerId", example = "LF_046587")
    private String towUsuerId;

    @Column(name = "energy")
    @ApiModelProperty(value = "能量值表主键", name = "energy", example = "0.000")
    private Double energy;

    @Column(name = "role")
    @ApiModelProperty(value = "能量值表主键", name = "role", example = "0.000")
    private Integer role;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "FG_046587")
    private String userId;

    @Column(name = "unionId")
    @ApiModelProperty(value = "微信unionId", name = "unionId;", example = "oy_LM5I3jcI--jySxskn6ooi1ZbA")
    private String unionId;

    @Column(name = "openid")
    @ApiModelProperty(value = "微信openid", name = "openid;", example = "oy_LM5I3jcI--jySxskn6ooi1ZbA")
    private String openid;

    @Column(name = "webid")
    @ApiModelProperty(value = "公众号webid", name = "webid;", example = "oy_LM5I3jcI--jySxskn6ooi1ZbA")
    private String webid;

    @Column(name = "userName")
    @ApiModelProperty(value = "用户真实姓名", name = "userName", example = "小王")
    private String userName;

    @Column(name = "userNike")
    @ApiModelProperty(value = "用户昵称", name = "userNike", example = "陈楠")
    private String userNike;

    @Column(name = "userPassword")
    @ApiModelProperty(value = "用户密码MD5", name = "userPassword", example = "afdasfafdasfdsadfasfa")
    private String userPassword;

    @Column(name = "salt")
    @ApiModelProperty(value = "盐", name = "salt", example = "小王")
    private String salt;

    @Column(name = "userPhone")
    @ApiModelProperty(value = "用户电话", name = "userPhone", example = "12345678945")
    private String userPhone;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别0男,1女", name = "sex", example = "1")
    private Integer sex;

    @Column(name = "level")
    @ApiModelProperty(value = "会员等级,0初始,1体验,2大众,3精英,4皇家", name = "level", example = "0")
    private Integer level;

    @Column(name = "jiguangUsername")
    @ApiModelProperty(value = "极光用户名", name = "jiguangUsername;", example = "xxx")
    private String jiguangUsername;

    @Column(name = "jiguangPassword")
    @ApiModelProperty(value = "极光密码", name = "jiguangPassword;", example = "xxx")
    private String jiguangPassword;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-03-25 15:19:49")
    private Timestamp createTime;

    @Column(name = "avatarUrl")
    @ApiModelProperty(value = "头像", name = "createTime", example = "2019-03-25 15:19:49")
    private String avatarUrl;

    @Column(name = "type")
    @ApiModelProperty(value = "0未健身,1正在健身,2等待核销", name = "createTime", example = "0")
    private Integer type;

    @Column(name = "serialId")
    @ApiModelProperty(value = "健身流水号,未健身应该为null", name = "serialId", example = "1")
    private String serialId;

    @Column(name = "identityNumber")
    @ApiModelProperty(value = "身份证号", name = "identityNumber", example = "24689645844")
    private String identityNumber;
}

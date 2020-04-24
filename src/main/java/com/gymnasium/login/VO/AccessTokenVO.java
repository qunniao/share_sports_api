package com.gymnasium.login.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: AccessTokenVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 10:46
 */

@Data
@ApiModel(value = "AccessTokenVO", description = "微信凭证VO")
public class AccessTokenVO implements Serializable {

    @ApiModelProperty(value = "获取到的凭证", name = "access_token", example = "ACCESS_TOKEN")
    private String access_token;

    @ApiModelProperty(value = "凭证有效时间，单位：秒", name = "expires_in", example = "7200")
    private Integer expires_in;
}

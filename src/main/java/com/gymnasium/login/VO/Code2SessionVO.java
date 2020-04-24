package com.gymnasium.login.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: Code2SessionVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 9:35
 */
@Data
@ApiModel(value = "Code2SessionVO", description = "微信登录Code2Session")
public class Code2SessionVO implements Serializable {

    @ApiModelProperty(value = "会话密钥", name = "session_key", example = "")
    private String session_key;

    @ApiModelProperty(value = "用户唯一标识", name = "openid", example = "")
    private String openid;
}

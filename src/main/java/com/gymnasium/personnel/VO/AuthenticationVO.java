package com.gymnasium.personnel.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/3 19:29
 * @Description:
 */
@Data
public class AuthenticationVO {

    private Integer id;

    private Integer userId;

    private String trueName;

    private String idCard;

    private String image;

    private Integer reviewState;

    private String reject;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}

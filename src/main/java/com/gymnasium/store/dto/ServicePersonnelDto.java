package com.gymnasium.store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gymnasium.personnel.VO.UserManageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:22
 * @Description:
 */
@Data
public class ServicePersonnelDto implements Serializable {

    private Integer id;
    private String name;
    private String nick;
    private Integer managerId;
    private String workNumber;
    private String cover;
    private Date createTime;
    private Date updateTime;
    private Integer status;

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserManageVO userManageVO;
}

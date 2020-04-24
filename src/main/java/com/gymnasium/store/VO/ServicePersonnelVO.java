package com.gymnasium.store.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ServicePersonnelVO implements Serializable {

    private Integer id;
    private String name;
    private String nick;
    private Integer managerId;
    private String workNumber;
    private String cover;
    private Date createTime;
    private Date updateTime;
    private Integer status;

    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
}

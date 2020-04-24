package com.gymnasium.store.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:35
 * @Description:
 */
@Data
public class AgentVO implements Serializable {

    private Integer id;
    private Integer operationId;
    private Integer userId;
    private String agentCode;
    private Integer level;
    private String title;
    private String address;
    private String servicePhone;
    private String trueName;
    private String idCard;
    private String phone;
    private String region;
    private String username;
    private String password;
    private String qrCode;
    private Integer auditStatus;
    private Date createTime;
    private Date updateTime;
    private Integer status;
}

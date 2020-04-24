package com.gymnasium.store.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:45
 * @Description:
 */
@Data
public class ServiceCommentVO implements Serializable {

    private Integer id;
    private Integer userId;
    private Integer serviceId;
    private Integer level;
    private String content;
    private Integer solved;
    private Date createTime;
    private Date updateTime;
    private Integer status;
}

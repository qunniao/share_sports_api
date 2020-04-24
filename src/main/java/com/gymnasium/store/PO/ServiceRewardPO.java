package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:45
 * @Description:
 */
@Data
@Entity
@Table(name = "service_reward")
@ApiModel(value = "ServiceRewardPO", description = "打赏客服")
public class ServiceRewardPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "service_id")
    @ApiModelProperty(value = "客服id", name = "serviceId", example = "1")
    private Integer serviceId;

    @Column(name = "money")
    @ApiModelProperty(value = "打赏金额", name = "money", example = "1")
    private BigDecimal money;

    @Column(name = "content")
    @ApiModelProperty(value = "评价内容", name = "content", example = "1")
    private String content;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;
}

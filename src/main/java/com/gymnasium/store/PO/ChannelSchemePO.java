package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:05
 * @Description:
 */
@Data
@Entity
@Table(name = "channel_scheme")
@DynamicUpdate
@ApiModel(value = "ChannelSchemePO", description = "渠道方案")
public class ChannelSchemePO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "设置人id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "operator_id")
    @ApiModelProperty(value = "操作人id", name = "operatorId", example = "1")
    private Integer operatorId;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品id", name = "productId", example = "1")
    private Integer productId;

    @Column(name = "channel_code")
    @ApiModelProperty(value = "渠道编码", name = "channelCode", example = "1")
    private String channelCode;

    @Column(name = "channel_price")
    @ApiModelProperty(value = "渠道价", name = "channelPrice", example = "1")
    private BigDecimal channelPrice;

    @Column(name = "level")
    @ApiModelProperty(value = "渠道等级：1.一级渠道2.二级渠道", name = "level", example = "1")
    private Integer level;

    @Column(name = "type")
    @ApiModelProperty(value = "类型", name = "type", example = "1")
    private Integer type;

    @Column(name = "member_level")
    @ApiModelProperty(value = "会员等级", name = "memberLevel", example = "1")
    private Integer memberLevel;

    @Column(name = "profit")
    @ApiModelProperty(value = "利润", name = "profit", example = "1")
    private BigDecimal profit;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id",insertable = false, updatable = false)
    private ProductPO productPO;
}

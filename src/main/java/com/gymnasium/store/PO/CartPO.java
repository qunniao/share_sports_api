package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/20 17:43
 * @Description:
 */
@Entity
@Getter
@Setter
@Table(name = "cart")
@ApiModel(value = "CartPO", description = "购物车")
public class CartPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Long id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品id", name = "productId", example = "1")
    private Integer productId;

    @Column(name = "number")
    @ApiModelProperty(value = "数量", name = "number", example = "1")
    private Integer number;

    @Column(name = "settlement_type")
    @ApiModelProperty(value = "支付类型", name = "settlementType", example = "1")
    private Integer settlementType;

    @Column(name = "style")
    @ApiModelProperty(value = "款式", name = "style", example = "1")
    private String style;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ProductPO productPO;
}

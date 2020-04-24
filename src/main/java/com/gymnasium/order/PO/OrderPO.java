package com.gymnasium.order.PO;

import com.gymnasium.card.PO.ActivationPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: OrderPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 15:00
 */


@Data
@Entity
@Table(name = "order", catalog = "gymnasium")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "order", description = "会员订单办理记录")
public class OrderPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "订单表", name = "id", example = "1")
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty(value = "会员名称", name = "name", example = "1")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "会员等级", name = "level", example = "1")
    @Column(name = "level")
    private Integer level;

    @ApiModelProperty(value = "电话", name = "phone", example = "111321546974")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty(value = "金额", name = "price", example = "1")
    @Column(name = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "订单详情", name = "productItem", example = "办理黄金会员,无活动")
    @Column(name = "product_item")
    private String productItem;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @ApiModelProperty(value = "创建时间", name = "updateTime", example = "1")
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @ApiModelProperty(value = "订单号", name = "phone", example = "111da321546974")
    @Column(name = "order_number")
    private String orderNumber;

    @ApiModelProperty(value = "能量值", name = "energy", example = "999")
    private BigDecimal energy;

    @ApiModelProperty(value = "1:开会员2:充值能量值3 购买会员卡", name = "type", example = "1")
    private Integer type;

    @ApiModelProperty(value = "订单状态1.待付款2.已付款", name = "orderState", example = "1")
    @Column(name = "order_state")
    private Integer orderState;

    @ApiModelProperty(value = "数量,仅限购买未激活的会员卡使用", name = "number", example = "1")
    @Column(name = "number")
    private Integer number;

    @ApiModelProperty(value = "收货地址,仅限购买未激活的会员卡使用", name = "address", example = "杭州市")
    @Column(name = "address")
    private String address;

    @ApiModelProperty(value = "快递单号", name = "courier_number", example = "464fds5fs")
    @Column(name = "courier_number")
    private String courierNumber;

    @ApiModelProperty(value = "配送方式", name = "delivery_way", example = "中通")
    @Column(name = "delivery_way")
    private String deliveryWay;

    @ApiModelProperty(value = "付款时间", name = "payTime", example = "")
    @Column(name = "pay_time")
    private Date payTime;

    @ApiModelProperty(value = "发货时间", name = "deliveryTime", example = "")
    @Column(name = "delivery_time")
    private Date deliveryTime;

    @ApiModelProperty(value = "收货时间", name = "receivingTime", example = "")
    @Column(name = "receiving_time")
    private Date receivingTime;

    @ApiModelProperty(value = "会员卡价格", name = "memberPrice", example = "")
    @Column(name = "member_price")
    private BigDecimal memberPrice;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private List<ActivationPO> activationPO;
}

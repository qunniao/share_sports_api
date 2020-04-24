package com.gymnasium.store.PO;

import com.gymnasium.order.PO.CouponPO;
import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 19:22
 * @Description:
 */
@Data
@Entity
@Table(name = "shop_order", catalog ="gymnasium")
@ApiModel(value = "ShopOrderPO", description = "商城订单")
public class ShopOrderPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Long id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "superior")
    @ApiModelProperty(value = "上级id", name = "userId", example = "1")
    private Integer superior;

    @Column(name = "order_number")
    @ApiModelProperty(value = "订单编号", name = "orderNumber", example = "1")
    private String orderNumber;

    @Column(name = "contact_name")
    @ApiModelProperty(value = "联系人姓名", name = "contactName", example = "1")
    private String contactName;

    @Column(name = "phone")
    @ApiModelProperty(value = "手机号", name = "phone", example = "1")
    private String phone;

    @Column(name = "address")
    @ApiModelProperty(value = "收货地址", name = "address", example = "1")
    private String address;

    @Column(name = "address_id")
    @ApiModelProperty(value = "收货地址id", name = "addressId", example = "1")
    private Integer addressId;

    @Column(name = "total_price")
    @ApiModelProperty(value = "商品总价", name = "totalPrice", example = "1")
    private BigDecimal totalPrice;

    @Column(name = "pay_price")
    @ApiModelProperty(value = "实付款", name = "payPrice", example = "1")
    private BigDecimal payPrice;

    @Column(name = "discount")
    @ApiModelProperty(value = "优惠价", name = "discount", example = "1")
    private BigDecimal discount;

    @Column(name = "freight")
    @ApiModelProperty(value = "运费", name = "freight", example = "1")
    private BigDecimal freight;

    @Column(name = "order_state")
    @ApiModelProperty(value = "订单状态：0.已取消1.待付款2.待发货3.待收货4.已完成", name = "orderState", example = "1")
    private Integer orderState;

    @Column(name = "courier_number")
    @ApiModelProperty(value = "快递单号", name = "courierNumber", example = "1")
    private String courierNumber;

    @Column(name = "delivery_way")
    @ApiModelProperty(value = "配送方式", name = "deliveryWay", example = "1")
    private String deliveryWay;

    @Column(name = "settlement_type")
    @ApiModelProperty(value = "结算方式：1.现金2.能量值", name = "settlementType", example = "1")
    private Integer settlementType;

    @Column(name = "coupon_id")
    @ApiModelProperty(value = "优惠券id", name = "couponId", example = "1")
    private Integer couponId;

    @Column(name = "pay_way")
    @ApiModelProperty(value = "付款方式1.钱包能量值2.微信3.支付宝", name = "payWay", example = "1")
    private Integer payWay;

    @Column(name = "remark")
    @ApiModelProperty(value = "订单备注", name = "remark", example = "1")
    private String remark;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "pay_time")
    @ApiModelProperty(value = "付款时间", name = "payTime", example = "1")
    private Date payTime;

    @Column(name = "delivery_time")
    @ApiModelProperty(value = "发货时间", name = "deliveryTime", example = "1")
    private Date deliveryTime;

    @Column(name = "receiving_time")
    @ApiModelProperty(value = "收货时间", name = "receivingTime", example = "1")
    private Date receivingTime;

    @Column(name = "last_time")
    @ApiModelProperty(value = "更新时间", name = "lastTime", example = "1")
    private Date lastTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态1.正常0.删除", name = "status", example = "1")
    private Integer status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",referencedColumnName = "id",insertable = false,updatable = false)
    private List<OrderProductPO> orderProductPO;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id",referencedColumnName = "uid",insertable = false,updatable = false)
    private UserPO userPO;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id",insertable = false,updatable = false)
    private CouponPO couponPO;
}

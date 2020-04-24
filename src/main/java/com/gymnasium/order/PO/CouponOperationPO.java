package com.gymnasium.order.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: CouponPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 9:29
 */
@Data
@Entity
@Table(name = "order_coupon_operation", catalog = "gymnasium")
@ApiModel(value = "CouponOperationPO", description = "优惠券使用记录表")
public class CouponOperationPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "operator_id")
    @ApiModelProperty(value = "操作人id(核销客服人员)", name = "operatorId", example = "1")
    private String operatorId;

    @Column(name = "coupon_id")
    @ApiModelProperty(value = "优惠卷id", name = "couponId", example = "1")
    private Integer couponId;

    @Column(name = "coupon_code")
    @ApiModelProperty(value = "优惠券编码", name = "couponCode", example = "1")
    private String couponCode;

    @Column(name = "type")
    @ApiModelProperty(value = "优惠券类型1.特权券2.礼包券3.商城券", name = "type", example = "1")
    private Integer type;

    @Column(name = "issue_id")
    @ApiModelProperty(value = "发放人编号", name = "issueId", example = "1")
    private String issueId;

    @Column(name = "verify_way")
    @ApiModelProperty(value = "核销方式1.人工2.自动", name = "verifyWay", example = "1")
    private Integer verifyWay;

    @Column(name = "verify_state")
    @ApiModelProperty(value = "核销状态: 0.未核销,1.用户申请核销,2.已核销,3.已拒绝", name = "verifyState", example = "1")
    private Integer verifyState;

    @Column(name = "status")
    @ApiModelProperty(value = "1.正常可使用0.已使用或失效", name = "status", example = "1")
    private Integer status;

    @Column(name = "verify_time")
    @ApiModelProperty(value = "核销时间", name = "verifyTime", example = "1")
    private Timestamp verifyTime;

    @Column(name = "expiration_time")
    @ApiModelProperty(value = "过期时间", name = "expirationTime", example = "1")
    private Timestamp expirationTime;

    @Column(name = "create_time")
    @ApiModelProperty(value = "发放时间(创建时间)", name = "createTime", example = "1")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Timestamp updateTime;

    @OneToOne(targetEntity = CouponPO.class)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id", insertable = false, updatable = false,unique = false)
    private CouponPO couponPO;
}
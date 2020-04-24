package com.gymnasium.order.PO;

import com.gymnasium.stadium.PO.CardTypePO;
import com.gymnasium.stadium.PO.GymShopPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 王志鹏
 * @title: ActivationCode
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/16 16:33
 */

@Data
@Entity
@Table(name = "order_activation_code", catalog = "gymnasium")
@ApiModel(value = "ActivationCodePO", description = "转卡订单")
public class ActivationCodePO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "order_number")
    @ApiModelProperty(value = "订单号", name = "orderNumber", example = "1")
    private String orderNumber;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "店家编号", name = "gymShopId", example = "1")
    private String gymShopId;

    @Column(name = "activationCode")
    @ApiModelProperty(value = "激活码", name = "activationCode", example = "1")
    private String activationCode;

    @Column(name = "energy")
    @ApiModelProperty(value = "消耗能量值", name = "energy", example = "1")
    private Double energy;

    @Column(name = "type")
    @ApiModelProperty(value = "0初始,1退卡,2待激活,3已激活", name = "type", example = "1")
    private Integer type;

    @Column(name = "reservedName")
    @ApiModelProperty(value = "预留姓名", name = "reservedName", example = "1")
    private String reservedName;

    @Column(name = "reservedPhone")
    @ApiModelProperty(value = "预留手机号", name = "reservedPhone", example = "1")
    private String reservedPhone;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Timestamp createTime;

    @Column(name = "cardTypeId")
    @ApiModelProperty(value = "卡片类型主键", name = "cardTypeId", example = "1")
    private Integer cardTypeId;

    @Column(name = "card_account")
    @ApiModelProperty(value = "卡账号", name = "cardAccount", example = "1")
    private String cardAccount;

    @Column(name = "card_password")
    @ApiModelProperty(value = "密码", name = "cardPassword", example = "1")
    private String cardPassword;

    @Column(name = "docking_name")
    @ApiModelProperty(value = "对接人姓名", name = "dockingName", example = "1")
    private String dockingName;

    @Column(name = "docking_phone")
    @ApiModelProperty(value = "电话", name = "dockingPhone", example = "1")
    private String dockingPhone;

    @Column(name = "handle_time")
    @ApiModelProperty(value = "办理时间", name = "handleTime", example = "1")
    private Date handleTime;

    @Column(name = "refund_time")
    @ApiModelProperty(value = "激活时间", name = "refundTime", example = "1")
    private Date refundTime;

    @Column(name = "review_state")
    @ApiModelProperty(value = "审核状态：0.待审核1.审核通过2.拒绝", name = "reviewState", example = "1")
    private Integer reviewState;

    @Column(name = "review_time")
    @ApiModelProperty(value = "审核时间", name = "reviewTime", example = "1")
    private Date reviewTime;

    @ManyToOne
    @JoinColumn(name = "gymShopId",referencedColumnName = "gymShopId",insertable = false,updatable = false)
    @ApiModelProperty(value = "店家编号", name = "gymShopId", example = "1")
    private GymShopPO gymShopPO;

    @OneToOne
    @JoinColumn(name = "cardTypeId",referencedColumnName = "id",insertable = false,updatable = false)
    @ApiModelProperty(value = "店家编号", name = "gymShopId", example = "1")
    private CardTypePO cardTypePO;

}

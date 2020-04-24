package com.gymnasium.store.PO;

import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/29 09:50
 * @Description:
 */
@Getter
@Setter
@Entity
@Table(name = "commission")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "CommissionPO", description = "佣金表")
public class CommissionPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "role")
    @ApiModelProperty(value = "身份：0.用户1.代理2.渠道", name = "role", example = "1")
    private Integer role;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "superior")
    @ApiModelProperty(value = "上级id", name = "superior", example = "1")
    private Integer superior;

    @Column(name = "second_level")
    @ApiModelProperty(value = "二级id", name = "secondLevel", example = "1")
    private Integer secondLevel;

    @Column(name = "balance")
    @ApiModelProperty(value = "账户余额", name = "balance", example = "1")
    private BigDecimal balance;

    @Column(name = "profits")
    @ApiModelProperty(value = "总累计利润", name = "profits", example = "1")
    private BigDecimal profits;

    @Column(name = "can_carry")
    @ApiModelProperty(value = "可提现金额", name = "canCarry", example = "1")
    private BigDecimal canCarry;

    @Column(name = "wait_cashed")
    @ApiModelProperty(value = "待打款金额", name = "waitCashed", example = "1")
    private BigDecimal waitCashed;

    @Column(name = "applied")
    @ApiModelProperty(value = "已申请金额", name = "applied", example = "1")
    private BigDecimal applied;

    @Column(name = "cashed")
    @ApiModelProperty(value = "已提现金额", name = "cashed", example = "1")
    private BigDecimal cashed;

    @Column(name = "invalid")
    @ApiModelProperty(value = "无效金额", name = "invalid", example = "1")
    private BigDecimal invalid;

    @Column(name = "wait_receive")
    @ApiModelProperty(value = "待收货金额", name = "waitReceive", example = "1")
    private BigDecimal waitReceive;

    @Column(name = "no_settlement")
    @ApiModelProperty(value = "未结算金额", name = "noSettlement", example = "1")
    private BigDecimal noSettlement;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态:0.删除1.未审核2. 通过-1.拒绝", name = "status", example = "1")
    private Integer status;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id",referencedColumnName = "uid",insertable = false,updatable = false)
    private UserPO userPO;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "superior",referencedColumnName = "uid",insertable = false,updatable = false)
    private UserPO superiorUser;
}

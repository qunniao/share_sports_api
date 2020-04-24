package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/30 18:42
 * @Description:
 */
@Data
@Entity
@Table(name = "cash_flow")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CashFlowPO", description = "资金流水")
public class CashFlowPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "order_number")
    @ApiModelProperty(value = "订单编号", name = "orderNumber", example = "1")
    private String orderNumber;

    @Column(name = "flow_number")
    @ApiModelProperty(value = "流水号", name = "flowNumber", example = "1")
    private String flowNumber;

    @Column(name = "title")
    @ApiModelProperty(value = "项目名称", name = "title", example = "1")
    private String title;

    @Column(name = "content")
    @ApiModelProperty(value = "交易内容", name = "content", example = "1")
    private String content;

    @Column(name = "money")
    @ApiModelProperty(value = "订单编号", name = "money", example = "1")
    private BigDecimal money;

    @Column(name = "type")
    @ApiModelProperty(value = "类型:1.佣金2.其他", name = "type", example = "1")
    private Integer type;

    @Column(name = "role")
    @ApiModelProperty(value = "身份", name = "role", example = "1")
    private Integer role;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id", name = "orderId", example = "1")
    private Long orderId;

    @Column(name = "superior")
    @ApiModelProperty(value = "上级id", name = "superior", example = "1")
    private Integer superior;

    @Column(name = "second_level")
    @ApiModelProperty(value = "二级id", name = "secondLevel", example = "1")
    private Integer secondLevel;

    @Column(name = "way")
    @ApiModelProperty(value = "方式1.收入2.支出", name = "way", example = "1")
    private Integer way;

    @Column(name = "client_commission")
    @ApiModelProperty(value = "客户佣金", name = "clientCommission", example = "1")
    private BigDecimal clientCommission;

    @Column(name = "agent_commission")
    @ApiModelProperty(value = "代理佣金", name = "agentCommission", example = "1")
    private BigDecimal agentCommission;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;
}

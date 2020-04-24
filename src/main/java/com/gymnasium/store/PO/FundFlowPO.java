package com.gymnasium.store.PO;

import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/31 14:07
 * @Description:
 */
@Data
@Entity
@Table(name = "fund_flow")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FundFlowPO", description = "资金流水")
public class FundFlowPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "flow_number")
    @ApiModelProperty(value = "流水号", name = "flowNumber", example = "1")
    private String flowNumber;

    @Column(name = "transaction_type")
    @ApiModelProperty(value = "交易类型1.收入2.支出", name = "transactionType", example = "1")
    private Integer transactionType;

    @Column(name = "type")
    @ApiModelProperty(value = "流水类型:1.付款2.佣金3.提现", name = "type", example = "1")
    private Integer type;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单", name = "orderId", example = "1")
    private Long orderId;

    @Column(name = "title")
    @ApiModelProperty(value = "项目名称", name = "title", example = "1")
    private String title;

    @Column(name = "content")
    @ApiModelProperty(value = "交易内容", name = "content", example = "1")
    private String content;

    @Column(name = "money")
    @ApiModelProperty(value = "金额", name = "money", example = "1")
    private BigDecimal money;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id",referencedColumnName = "uid",insertable = false,updatable = false)
    private UserPO userPO;

    @OneToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ShopOrderPO shopOrderPO;
}

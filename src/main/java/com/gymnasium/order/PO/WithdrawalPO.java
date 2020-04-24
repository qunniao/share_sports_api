package com.gymnasium.order.PO;

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
 * @ClassName: gymnasium
 * @Date: 2019/7/2 20:12
 * @Description:
 */
@Data
@Entity
@Table(name = "withdrawal")
public class WithdrawalPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户Id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "true_name")
    @ApiModelProperty(value = "真实姓名", name = "trueName", example = "1")
    private String trueName;

    @ApiModelProperty(value = "账户", name = "account", example = "1")
    private String account;

    @ApiModelProperty(value = "金额", name = "amount", example = "1")
    private BigDecimal amount;

    @ApiModelProperty(value = "reviewState", name = "审核状态0.待审核1.同意转账2.拒绝", example = "1")
    @Column(name = "review_state")
    private Integer reviewState;

    @ApiModelProperty(value = "refuse", name = "拒绝原因", example = "1")
    private String refuse;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "last_time")
    @ApiModelProperty(value = "修改时间", name = "lastTime", example = "1")
    @UpdateTimestamp
    private Date lastTime;

    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;
}

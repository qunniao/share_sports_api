package com.gymnasium.card.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 王志鹏
 * @title: Activation
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 9:50
 */

@Data
@Entity
@Table(name = "card_activation", catalog = "gymnasium")
@ApiModel(value = "ActivationPO会员激活卡", description = "会员激活卡")
public class ActivationPO implements Serializable {

    @Id
    @Column(name = "cid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "cid", example = "1")
    private Integer cid;

    @Column(name = "cardNum")
    @ApiModelProperty(value = "卡号", name = "cardNum", example = "1")
    private String cardNum;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id", name = "orderId", example = "1")
    private Integer orderId;

    @Column(name = "cardPassWord")
    @ApiModelProperty(value = "卡密", name = "cardPassWord", example = "1")
    private String cardPassWord;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "icreateTimed", example = "1")
    private Timestamp createTime;

    @Column(name = "type")
    @ApiModelProperty(value = "卡类型0体验会员,1大众会员,2精英会员3,皇家会员", name = "type", example = "0,1,2")
    private Integer type;

    @Column(name = "status")
    @ApiModelProperty(value = "0无效,1有效", name = "status", example = "1")
    private Integer status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", referencedColumnName = "cid")
    private List<ActivationRecordPO> activationRecordPO;
}

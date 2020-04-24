package com.gymnasium.card.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: ActivationRecordPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 10:09
 */
@Data
@Entity
@Table(name = "card_record_activation", catalog = "gymnasium")
@ApiModel(value = "ActivationRecordPO", description = "激活卡操作记录")
public class ActivationRecordPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "cardNum")
    @ApiModelProperty(value = "卡号", name = "cardNum", example = "2016124455")
    private String cardNum;

    @Column(name = "card_id")
    @ApiModelProperty(value = "卡信息主键", name = "cardId", example = "1")
    private Integer cardId;

    @Column(name = "buyUserId")
    @ApiModelProperty(value = "购买人userId", name = "buyUserId", example = "1")
    private String buyUserId;

    @Column(name = "useUserId")
    @ApiModelProperty(value = "使用人userId", name = "useUserId", example = "1")
    private String useUserId;

    @Column(name = "name")
    @ApiModelProperty(value = "使用人姓名", name = "name", example = "张三")
    private String name;

    @Column(name = "phone")
    @ApiModelProperty(value = "phone", name = "手机号", example = "13516155")
    private String phone;

    @Column(name = "payType")
    @ApiModelProperty(value = "付款方式0,微信1,银行卡,2支付宝", name = "payType", example = "1")
    private Integer payType;

    @Column(name = "type")
    @ApiModelProperty(value = "操作类型0购买,1激活,2退卡", name = "type", example = "1")
    private Integer type;

    @Column(name = "operTime")
    @ApiModelProperty(value = "操作时间", name = "operTime", example = "1")
    private Timestamp operTime;
}

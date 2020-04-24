package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: RedemptionCode
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/16 14:49
 */

@Data
@Entity
@Table(name = "order_expensesrecord", catalog = "gymnasium")
@ApiModel(value = "RedemptionCodePO", description = "转卡表")
public class RedemptionCodePO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "店家编号", name = "gymShopId", example = "1")
    private String gymShopId;

    @Column(name = "code")
    @ApiModelProperty(value = "兑换码", name = "code", example = "1312sdfs")
    private String code;

    @Column(name = "energy")
    @ApiModelProperty(value = "能量值", name = "energy", example = "1")
    private Double energy;

    @Column(name = "type")
    @ApiModelProperty(value = "0退卡,1待激活,2已激活", name = "type", example = "1")
    private Integer type;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注", name = "remark", example = "1")
    private String remarks;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Timestamp createTime;
}

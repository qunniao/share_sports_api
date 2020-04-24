package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: CardPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/15 9:19
 */
@Data
@Entity
@Table(name = "order_card_type", catalog = "gymnasium")
@ApiModel(value = "CardPO", description = "健身房卡类型")
public class CardTypePO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "店家编号", name = "gymShopId", example = "1")
    private String gymShopId;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", name = "name", example = "1")
    private String name;

    @Column(name = "marketPrice")
    @ApiModelProperty(value = "市场价格", name = "marketPrice", example = "1")
    private String marketPrice;

    @Column(name = "energy")
    @ApiModelProperty(value = "消耗能量值", name = "energy", example = "1")
    private Double energy;

    @Column(name = "status")
    @ApiModelProperty(value = "0无效1有效", name = "status", example = "1")
    private Integer status;

    @Column(name = "url")
    @ApiModelProperty(value = "图片连接", name = "url", example = "1fdsdfsdfsf")
    private String url;

    @Column(name = "remakes")
    @ApiModelProperty(value = "描述", name = "remakes", example = "无")
    private String remakes;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Timestamp createTime;

    @Column(name = "service_phone")
    @ApiModelProperty(value = "客服热线", name = "servicePhone", example = "1")
    private String servicePhone;


    @Column(name = "work_number")
    @ApiModelProperty(value = "客服工号", name = "workNumber", example = "1")
    private String workNumber;

}

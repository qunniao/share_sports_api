package com.gymnasium.system.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: SysMenberCityPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 16:59
 */

@Data
@Entity
@Table(name = "sys_menber_city", catalog = "gymnasium")
@ApiModel(value = "SysMenberCityPO", description = "会员卡地区佣金表")
public class SysMenberCityPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    @Column(name = "id")
    private Integer id;

    @Column(name = "mid")
    @ApiModelProperty(value = "会员卡表主键", name = "mid", example = "0")
    private Integer mid;

    @Column(name = "onePrice")
    @ApiModelProperty(value = "一级代理佣金", name = "onePrice", example = "0")
    private Double onePrice;

    @Column(name = "twoPrice")
    @ApiModelProperty(value = "二级级代理佣金", name = "twoPrice", example = "0")
    private Double twoPrice;

    @Column(name = "cityName")
    @ApiModelProperty(value = "城市名称", name = "cityName", example = "0")
    private String cityName;
}

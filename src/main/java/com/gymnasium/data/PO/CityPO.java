package com.gymnasium.data.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author 王志鹏
 * @title: CityPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 10:32
 */
@Data
@Entity
@Table(name = "data_city", catalog = "gymnasium")
@ApiModel(value = "City对象", description = "全国地址信息")
public class CityPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "city主键", name = "id", example = "1")
    @Column(name = "id")
    private Integer id;//city主键

    @ApiModelProperty(value = "父级id", name = "pid", example = "913")
    @Column(name = "pid")
    private Integer pid;//父级id

    @ApiModelProperty(value = "简称", name = "shortName", example = "润州")
    @Column(name = "shortName")
    private String shortName;//简称

    @ApiModelProperty(value = "名称", name = "name", example = "润州区")
    @Column(name = "name")
    private String name; //名称

    @ApiModelProperty(value = "全称", name = "mergerName", example = "中国,江苏省,镇江市,润州区")
    @Column(name = "mergerName")
    private String mergerName;//全称

    @ApiModelProperty(value = "层级", name = "level", example = "1,2,3")
    @Column(name = "level")
    private Short level; //层级 1 2 3省市区县

    @ApiModelProperty(value = "拼音", name = "pinyin", example = "runzhou")
    @Column(name = "pinyin")
    private String pinyin; //拼音

    @ApiModelProperty(value = "长途区号", name = "code", example = "0575")
    @Column(name = "code")
    private String code; //长途区号

    @ApiModelProperty(value = "邮编", name = "zipCode", example = "212005")
    @Column(name = "zipCode")
    private String zipCode; //邮编

    @ApiModelProperty(value = "首字母", name = "first", example = "H")
    @Column(name = "first")
    private String first; //首字母

    @ApiModelProperty(value = "经度", name = "lng", example = "119.41134")
    @Column(name = "lng")
    private String lng; //经度

    @ApiModelProperty(value = "纬度", name = "lat", example = "32.19523")
    @Column(name = "lat")
    private String lat; //纬度

}

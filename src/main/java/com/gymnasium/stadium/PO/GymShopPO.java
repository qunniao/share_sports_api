package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 王志鹏
 * @title: GymShopDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/2 13:37
 */

@Getter
@Setter
@Entity
@Table(name = "gym_shop", catalog = "gymnasium")
@ApiModel(value = "GymShopPO对象", description = "健身房信息")
public class GymShopPO implements Serializable {

    @Id
    @Column(name = "gid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "gid健身房", name = "gid", example = "1")
    private Integer gid;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "SY_afa1651")
    private String gymShopId;

    @Column(name = "gymName")
    @ApiModelProperty(value = "健身房名称", name = "gymName", example = "SYSO店")
    private String gymName;

    @Column(name = "score")
    @ApiModelProperty(value = "健身房评分max 5", name = "score", example = "5")
    private Integer score;

    @Column(name = "gymPhone")
    @ApiModelProperty(value = "健身房联系电话", name = "gymPhone", example = "16845634184")
    private String gymPhone;

    @Column(name = "gymEnergy")
    @ApiModelProperty(value = "健身房消耗能量值", name = "gymEnergy", example = "12")
    private Integer gymEnergy;

    @Column(name = "label")
    @ApiModelProperty(value = "特色标签", name = "label", example = "1,2,3")
    private String label;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1,2,3")
    private Timestamp createTime;

    @Column(name = "longitude")
    @ApiModelProperty(value = "经度", name = "longitude", example = "0.00000")
    private Double longitude;

    @Column(name = "latitude")
    @ApiModelProperty(value = "纬度", name = "latitude", example = "0.000000")
    private Double latitude;

    @Column(name = "area")
    @ApiModelProperty(value = "面积", name = "area", example = "1000")
    private Double area;

    @Column(name = "unit")
    @ApiModelProperty(value = "单位", name = "unit", example = "每2小时")
    private String unit;

    @Column(name = "businessTime")
    @ApiModelProperty(value = "营业时间", name = "businessTime", example = "周一到周日 早10:00-晚10:00")
    private String businessTime;

    @Column(name = "image")
    @ApiModelProperty(value = "健身房头像", name = "image", example = "group1/M00/00/00/rBGlPFyutBWAJruRAAFhc5VxyE0480.png")
    private String image;

    @Column(name = "address")
    @ApiModelProperty(value = "健身房地址", name = "address", example = "健身房地址")
    private String address;

    @Column(name = "status")
    @ApiModelProperty(value = "0禁用,1启用", name = "status", example = "1")
    private Integer status;

    @Column(name = "preferential")
    @ApiModelProperty(value = "优惠信息", name = "preferential", example = "无")
    private String preferential;

    @Column(name = "qrCodeUrl")
    @ApiModelProperty(value = "二维码url", name = "qrCodeUrl", example = "group1/M00xxxx")
    private String qrCodeUrl;

    @Column(name = "type")
    @ApiModelProperty(value = "类型:0默认,1建设房,2工作室", name = "type", example = "group1/M00xxxx")
    private Integer type;

    @Column(name = "cityId")
    @ApiModelProperty(value = "城市编号地址数据主键", name = "cityId", example = "1")
    private Integer cityId;

    @Column(name = "district_id")
    @ApiModelProperty(value = "区域id", name = "districtId", example = "1")
    private Integer districtId;

    @Column(name = "matching")
    @ApiModelProperty(value = "是否开放匹配机制 0.不开放1.开发", name = "matching", example = "1")
    private Integer matching;

    @Transient
    private List<GymBuildingPO> gymBuildingPOS;

    @Transient
    private List<GymSubjectPO> gymSubjectPOS;

    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.REMOVE})
    @JoinColumn(name = "gid", referencedColumnName = "gid")
    private List<GymBuildingPO> gymBuildingPOList;

    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.REMOVE})
    @JoinColumn(name = "gid", referencedColumnName = "gid")
    private List<GymSubjectPO> gymSubjectPOList;


}
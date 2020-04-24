package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GymFitnessRecordPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/24 14:22
 */
@Data
@Entity
@Table(name = "gym_fitness_record", catalog = "gymnasium")
@ApiModel(value = "GymFitnessRecordPO对象", description = "健身记录")
public class GymFitnessRecordPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "serialId")
    @ApiModelProperty(value = "健身流水号", name = "serialId", example = "SsdfY_afa1651")
    private String serialId;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "SY_afa1651")
    private String gymShopId;

    @Column(name = "userId")
    @ApiModelProperty(value = "人员编号", name = "userId", example = "SY_afa1651")
    private String userId;

    @Column(name = "startTime")
    @ApiModelProperty(value = "健身开始时间", name = "startTime", example = "2019-03-25 15:19:49")
    private Timestamp startTime;

    @Column(name = "endTime")
    @ApiModelProperty(value = "健身结束时间", name = "endTime", example = "2019-03-25 15:19:49")
    private Timestamp endTime;

    @Column(name = "towEndTime")
    @ApiModelProperty(value = "核销结束时间", name = "towEndTime", example = "2019-03-25 15:19:49")
    private Timestamp towEndTime;

    @Column(name = "peopleNum")
    @ApiModelProperty(value = "人员数量", name = "peopleNum", example = "SY_afa1651")
    private Integer peopleNum;

    @Column(name = "energy")
    @ApiModelProperty(value = "消耗能量值", name = "energy", example = "SY_afa1651")
    private Double energy;

    @Column(name = "second")
    @ApiModelProperty(value = "用时 秒", name = "second", example = "60")
    private Integer second;

    @Column(name = "userType")
    @ApiModelProperty(value = "0未健身,1正在健身,2等待核销", name = "userType", example = "60")
    private Integer userType;

    @Column(name = "type")
    @ApiModelProperty(value = "核销:0异常,1正常", name = "type", example = "60")
    private Integer type;

    @Column(name = "partnerType")
    @ApiModelProperty(value = "搭伙:0为未搭伙:1搭伙中", name = "partnerType", example = "0")
    private Integer partnerType;


    @Column(name = "lat")
    private Double lat;
    @Column(name = "lnt")
    private Double lnt;

    @OneToOne(targetEntity = GymShopPO.class)
    @JoinColumn(name = "gymShopId", referencedColumnName = "gymShopId", insertable = false, updatable = false)
    private GymShopPO gymShopPO;
}

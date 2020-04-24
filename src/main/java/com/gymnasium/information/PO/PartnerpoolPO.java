package com.gymnasium.information.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: Partnerpool
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/18 10:02
 */

@Data
@Entity
@Table(name = "information_partnerpool", catalog = "gymnasium")
@ApiModel(value = "PartnerpoolPO搭伙池", description = "搭伙池用户匹配信息")
public class PartnerpoolPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "FG_046587")
    private Integer id;

    @Column(name = "startTime1")
    @ApiModelProperty(value = "开始时间1", name = "startTime1", example = "2018-05-03 00:01:06")
    private Timestamp startTime1;

    @Column(name = "startTime2")
    @ApiModelProperty(value = "开始时间2", name = "startTime2", example = "2018-05-03 00:01:06")
    private Timestamp startTime2;

    @Column(name = "startTime3")
    @ApiModelProperty(value = "开始时间3", name = "startTime3", example = "2018-05-03 00:01:06")
    private Timestamp startTime3;

    @Column(name = "startTime4")
    @ApiModelProperty(value = "开始时间4", name = "startTime4", example = "2018-05-03 00:01:06")
    private Timestamp startTime4;

    @Column(name = "endTime1")
    @ApiModelProperty(value = "结束时间1", name = "endTime1", example = "2019-05-03 00:01:06")
    private Timestamp endTime1;

    @Column(name = "endTime2")
    @ApiModelProperty(value = "结束时间2", name = "endTime2", example = "2019-05-03 00:01:06")
    private Timestamp endTime2;

    @Column(name = "endTime3")
    @ApiModelProperty(value = "结束时间3", name = "endTime3", example = "2019-05-03 00:01:06")
    private Timestamp endTime3;

    @Column(name = "endTime4")
    @ApiModelProperty(value = "结束时间4", name = "endTime4", example = "2019-05-03 00:01:06")
    private Timestamp endTime4;

    @Column(name = "regionId")
    @ApiModelProperty(value = "区域 城市表主键", name = "regionId", example = "1")
    private int regionId;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    //------------------多选Strat-----------------------
    @Column(name = "purpose")
    @ApiModelProperty(value = "健身目的 多选", name = "purpose", example = "1")
    private String purpose;

    @Column(name = "figure")
    @ApiModelProperty(value = "身材多选", name = "figure", example = "1")
    private String figure;

    @Column(name = "job")
    @ApiModelProperty(value = "职业类型多选", name = "job", example = "1")
    private String job;

    //------------------多选End-----------------------

    @Column(name = "body")
    @ApiModelProperty(value = "体型", name = "body", example = "1")
    private Integer body;

    @Column(name = "gender")
    @ApiModelProperty(value = "性别 0男1女", name = "gender", example = "1")
    private Integer gender;

    @Column(name = "age")
    @ApiModelProperty(value = "年龄", name = "age", example = "1")
    private Integer age;

    @Column(name = "level")
    @ApiModelProperty(value = "等级", name = "level", example = "1")
    private Integer level;

    @Column(name = "income")
    @ApiModelProperty(value = "收入类型", name = "income", example = "1")
    private Integer income;

    @Column(name = "status")
    @ApiModelProperty(value = "0禁用,1启用", name = "status", example = "1")
    private Integer status;

    @Column(name = "startAge")
    @ApiModelProperty(value = "最小年年龄", name = "startAge", example = "30")
    private Integer startAge;

    @Column(name = "endAge")
    @ApiModelProperty(value = "最大年龄", name = "endAge", example = "18")
    private Integer endAge;

}

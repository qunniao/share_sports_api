package com.gymnasium.information.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: PartnerpoolVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/18 14:18
 */
@Data
public class PartnerpoolVO {

    @ApiModelProperty(value = "开始时间", name = "type", example = "2018-05-03 00:01:06")
    private Timestamp startTime;

    @ApiModelProperty(value = "结束时间", name = "name", example = "2019-05-03 00:01:06")
    private Timestamp endTime;

    @ApiModelProperty(value = "区域 城市表主键", name = "regionId", example = "1")
    private Integer regionId;

    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @ApiModelProperty(value = "健身目的", name = "purpose", example = "1")
    private String purpose;

    @ApiModelProperty(value = "体型", name = "body", example = "1")
    private Integer body;

    @ApiModelProperty(value = "性别 0男1女", name = "gender", example = "1")
    private Integer gender;

    @ApiModelProperty(value = "身材", name = "figure", example = "1")
    private String figure;

    @ApiModelProperty(value = "等级", name = "level", example = "1")
    private Integer level;

    @ApiModelProperty(value = "职业类型", name = "job", example = "1")
    private String job;

    @ApiModelProperty(value = "收入类型", name = "income", example = "1")
    private Integer income;

    @ApiModelProperty(value = "0禁用,1启用", name = "status", example = "1")
    private Integer status;

    @ApiModelProperty(value = "最小", name = "startAge", example = "1")
    private Integer startAge;

    @ApiModelProperty(value = "最大年龄", name = "endAge", example = "1")
    private Integer endAge;
}

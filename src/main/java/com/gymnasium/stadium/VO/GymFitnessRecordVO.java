package com.gymnasium.stadium.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GymFitnessRecordVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/24 17:04
 */
@Data
@ApiModel(value = "GymFitnessRecordVO对象", description = "健身记录信息")
public class GymFitnessRecordVO implements Serializable {

    @ApiModelProperty(value = "健身流水号", name = "serialId", example = "SsdfY_afa1651")
    private String serialId;

    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "SY_afa1651")
    private String gymShopId;

    @ApiModelProperty(value = "人员编号", name = "userId", example = "SY_afa1651")
    private String userId;

    @ApiModelProperty(value = "健身开始时间", name = "startTime", example = "2019-03-25 15:19:49")
    private Timestamp startTime;

    @ApiModelProperty(value = "健身结束时间", name = "endTime", example = "2019-03-25 15:19:49")
    private Timestamp endTime;

    @ApiModelProperty(value = "核销结束时间", name = "towEndTime", example = "2019-03-25 15:19:49")
    private Timestamp towEndTime;

    @ApiModelProperty(value = "人员编号", name = "peopleNum", example = "SY_afa1651")
    private Integer peopleNum;

    @ApiModelProperty(value = "消耗能量值", name = "energy", example = "SY_afa1651")
    private Double energy;

    @ApiModelProperty(value = "备注", name = "remake", example = "用时0天0小时1分钟")
    private String remake;

    @ApiModelProperty(value = "用时 秒", name = "second", example = "60")
    private Integer second;

    @ApiModelProperty(value = "核销:0异常,1正常", name = "type", example = "60")
    private Integer type;

    @ApiModelProperty(value = "0未健身,1正在健身,2等待核销", name = "userType", example = "1")
    private Integer userType;

    @ApiModelProperty(value = "用户手机号", name = "phone", example = "12346548935")
    private String phone;
}

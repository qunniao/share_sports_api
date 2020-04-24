package com.gymnasium.information.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author 王志鹏
 * @Date 2019/5/18 19:48
 **/

@Data
public class PartnerUserVO implements Serializable {

    @ApiModelProperty(value = "用户编号", name = "uesrId", example = "asdffada")
    private String uesrId;

    @ApiModelProperty(value = "userNike", name = "userNike", example = "陈楠")
    private String userNike;

    @ApiModelProperty(value = "头像路径", name = "headUrl", example = "asdffada")
    private String headUrl;

    @ApiModelProperty(value = "开始时间", name = "startTime", example = "2018-05-03 00:01:06")
    private Timestamp startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime", example = "2018-05-03 00:01:06")
    private Timestamp endTime;

    @ApiModelProperty(value = "健身目的", name = "purpose", example = "2018-05-03 00:01:06")
    private String purpose;

    @ApiModelProperty(value = "区域名称", name = "cityName", example = "2018-05-03 00:01:06")
    private String cityName;

    @ApiModelProperty(value = "极光用户名", name = "jiguangUsername;", example = "xxx")
    private String jiguangUsername;

    @ApiModelProperty(value = "极光密码", name = "jiguangPassword;", example = "xxx")
    private String jiguangPassword;

}

package com.gymnasium.stadium.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gymnasium.stadium.PO.GymBuildingPO;
import com.gymnasium.stadium.PO.GymImagesPO;
import com.gymnasium.stadium.PO.GymSubjectPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 王志鹏
 * @title: GymShopVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/11 18:20
 */
@Data
@ApiModel(value = "GymShopVO", description = "健身房信息VO")
public class GymShopVO implements Serializable {

    @ApiModelProperty(value = "gid健身房", name = "gid", example = "1")
    private Integer gid;

    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "SY_afa1651")
    private String gymShopId;

    @ApiModelProperty(value = "健身房名称", name = "gymName", example = "SYSO店")
    private String gymName;

    @ApiModelProperty(value = "健身房评分", name = "score", example = "5.2")
    private Integer score;

    @ApiModelProperty(value = "健身房联系电话", name = "gymPhone", example = "16845634184")
    private String gymPhone;

    @ApiModelProperty(value = "健身房消耗能量值", name = "gymEnergy", example = "12")
    private Integer gymEnergy;

    @ApiModelProperty(value = "特色标签", name = "label", example = "1,2,3")
    private String label;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1,2,3")
    private Timestamp createTime;

    @ApiModelProperty(value = "经度", name = "longitude", example = "0.00000")
    private Double longitude;

    @ApiModelProperty(value = "纬度", name = "latitude", example = "0.000000")
    private Double latitude;

    @ApiModelProperty(value = "面积", name = "area", example = "1000")
    private Double area;

    @ApiModelProperty(value = "单位", name = "unit", example = "每2小时")
    private String unit;

    @ApiModelProperty(value = "营业时间", name = "businessTime", example = "周一到周日 早10:00-晚10:00")
    private String businessTime;

    @ApiModelProperty(value = "健身房头像", name = "image", example = "group1/M00/00/00/rBGlPFyutBWAJruRAAFhc5VxyE0480.png")
    private String image;

    @ApiModelProperty(value = "健身房地址", name = "address", example = "健身房地址")
    private String address;

    @ApiModelProperty(value = "优惠信息", name = "preferential", example = "无")
    private String preferential;

    @ApiModelProperty(value = "城市编号地址数据主键", name = "cityId", example = "1")
    private Integer cityId;

    private Integer districtId;
    private Integer type;
    private Integer matching;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sid;

    @ApiModelProperty(value = "健身房轮播图", name = "image")
    List<GymImagesPO> gymImagesPOList;

    @ApiModelProperty(value = "健身房场地设施", name = "image")
    private List<GymBuildingPO> gymBuildingPOList;

    @ApiModelProperty(value = "健身房课程", name = "image")
    private List<GymSubjectPO> gymSubjectPOList;
}

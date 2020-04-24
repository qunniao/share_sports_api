package com.gymnasium.file.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GymImagesVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/10 14:27
 */

@Data
public class GymImagesVO implements Serializable {

    @ApiModelProperty(value = "店家图片主键", name = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "店家编号", name = "gymShopId", example = "1")
    private String gymShopId;

    @ApiModelProperty(value = "教练编号", name = "userId", example = "1")
    private String userId;

    @ApiModelProperty(value = "图片路径", name = "url", example = "1")
    private String url;

    @ApiModelProperty(value = "图片类型0店家轮播图,1教练展示图片", name = "type", example = "1")
    private Integer type;

    @ApiModelProperty(value = "0禁用,1启用", name = "status", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", name = "status", example = "1")
    private Timestamp createTime;

}

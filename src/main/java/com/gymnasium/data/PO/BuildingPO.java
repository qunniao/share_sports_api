package com.gymnasium.data.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: Building
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 16:57
 */
@Data
@Entity
@Table(name = "data_building", catalog = "gymnasium")
@ApiModel(value = "BuildingPO对象", description = "场地设施")
public class BuildingPO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "设置表主键", name = "bid", example = "1")
    @Column(name = "bid")
    private Integer bid;

    @ApiModelProperty(value = "名称", name = "name", example = "1")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "图片路径", name = "url", example = "asfdafdsaf1")
    @Column(name = "url")
    private String url;

    @ApiModelProperty(value = "选中图片路径", name = "urly", example = "1fasdsfafasfd")
    @Column(name = "urly")
    private String urly;

    @ApiModelProperty(value = "0场地设施,1服务设施", name = "type", example = "1")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty(value = "0无效,1有效", name = "status", example = "1")
    @Column(name = "status")
    private Integer status;

}

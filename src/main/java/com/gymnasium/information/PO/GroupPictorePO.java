package com.gymnasium.information.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GroupPictore
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 13:30
 */
@Data
@Entity
@Table(name = "information_group_pictore", catalog = "gymnasium")
@ApiModel(value = "GroupPictorePO", description = "动态图片")
public class GroupPictorePO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "mid")
    @ApiModelProperty(value = "动态表主键", name = "mid", example = "1")
    private Integer mid;

    @Column(name = "url")
    @ApiModelProperty(value = "图片路径", name = "url", example = "ip+/group1/M00/00/00/rBGlPFzBgYCASVmJAABPhIs93ZU230.JPG")
    private String url;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-05-01 00:00:00")
    private Timestamp createTime;
}
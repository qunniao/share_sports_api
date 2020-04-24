package com.gymnasium.data.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: Scrollimg
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/30 9:35
 */
@Data
@Entity
@Table(name = "wx_scroll_img", catalog = "gymnasium")
@ApiModel(value = "ScrollimgPO对象", description = "轮播图")
public class ScrollimgPO implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id;", example = "1")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", name = "name;", example = "123465")
    private String name;

    @Column(name = "type")
    @ApiModelProperty(value = "类型", name = "type;", example = "1")
    private Integer type;

    @Column(name = "url")
    @ApiModelProperty(value = "路径", name = "url;", example = "123465")
    private String url;

    @Column(name = "toUrl")
    @ApiModelProperty(value = "备用跳转使用url", name = "toUrl;", example = "123465")
    private String toUrl;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime;", example = "123465")
    private Timestamp createTime;
}

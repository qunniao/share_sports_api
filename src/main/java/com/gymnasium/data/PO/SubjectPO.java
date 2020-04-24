package com.gymnasium.data.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: SubjectPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 17:09
 */

@Data
@Entity
@Table(name = "data_subject", catalog = "gymnasium")
@ApiModel(value = "SubjectPO对象", description = "课程")
public class SubjectPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "设置表主键", name = "sid", example = "1")
    @Column(name = "sid")
    private Integer sid;

    @ApiModelProperty(value = "课程名称", name = "name", example = "舞蹈 ")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "图片路径", name = "url", example = "1")
    @Column(name = "url")
    private String url;

    @ApiModelProperty(value = "选中图片路径", name = "urly", example = "1fasdsfafasfd")
    @Column(name = "urly")
    private String urly;

    @ApiModelProperty(value = "0无效,1有效", name = "status", example = "1")
    @Column(name = "status")
    private Integer status;


}

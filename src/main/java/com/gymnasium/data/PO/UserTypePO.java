package com.gymnasium.data.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: UserTypePO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/17 15:42
 */

@Data
@Entity
@Table(name = "date_usertype", catalog = "gymnasium")
@ApiModel(value = "UserTypePO", description = "系统级-用户信息类型")
public class UserTypePO implements Serializable {

    @Id
    @Column(name = "typeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "FG_046587")
    private Integer typeid;

    @Column(name = "type")
    @ApiModelProperty(value = "0目的,1体型,2身材,3职业,4收入类型", name = "type", example = "0")
    private Integer type;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", name = "name", example = "苗条")
    private String name;

    @Column(name = "url")
    @ApiModelProperty(value = "图片路径", name = "url", example = "xxxx")
    private String url;

    @Column(name = "urly")
    @ApiModelProperty(value = "选中图片路径", name = "urly", example = "xxxxx")
    private String urly;

//    @Column(name = "purposeName")
//    @ApiModelProperty(value = "目的名称", name = "purpose", example = "FG_046587")
//    private int purposeName;
//
//
//    @Column(name = "bodyTypeName")
//    @ApiModelProperty(value = "体型名称", name = "bodyType", example = "FG_046587")
//    private String bodyTypeName;
//
//
//    @Column(name = "figure")
//    @ApiModelProperty(value = "身材名称", name = "figureName", example = "骨感,苗条,完美,微胖,肥胖")
//    private String figureName;
//
//
//    @Column(name = "jobName")
//    @ApiModelProperty(value = "职业名称", name = "jobName", example = "学生")
//    private int jobName;
//
//    @Column(name = "income")
//    @ApiModelProperty(value = "收入类型", name = "income", example = "学生")
//    private String incomeName;


}

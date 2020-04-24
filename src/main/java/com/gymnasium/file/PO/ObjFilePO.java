package com.gymnasium.file.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: ObjFilePO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/10 9:21
 */
@Data
@Entity
@Table(name = "file_objfile", catalog = "gymnasium")
@ApiModel(value = "ObjFilePO", description = "全局文件")
public class ObjFilePO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", name = "name", example = "xxx")
    private String name;

    @Column(name = "url")
    @ApiModelProperty(value = "url", name = "url", example = "1")
    private String url;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注", name = "remarks", example = "xxxx")
    private String remarks;
}

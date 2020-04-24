package com.gymnasium.module.PO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gymnasium.personnel.PO.Permission;
import com.gymnasium.personnel.PO.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 王志鹏
 * @title: ModulePO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 9:48
 */

@Data
@Entity
@Table(name = "sys_module", catalog = "gymnasium")
@ApiModel(value = "ModulePO", description = "动态列表页面")
public class ModulePO implements Serializable {

    @Id
    @Column(name = "mid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "mid", example = "1")
    private Integer mid;

    @Column(name = "fatherModuleId")
    @ApiModelProperty(value = "父id", name = "fatherModuleId", example = "1")
    private String fatherModuleId;

    @Column(name = "name")
    @ApiModelProperty(value = "名称", name = "name", example = "1")
    private String name;

    @Column(name = "icon")
    @ApiModelProperty(value = "图标路径", name = "icon", example = "xxxx")
    private String icon;

    @Column(name = "url")
    @ApiModelProperty(value = "url", name = "url", example = "1")
    private String url;

    @Column(name = "level")
    @ApiModelProperty(value = "列表等级", name = "level", example = "1")
    private String level;

    @Column(name = "sort")
    @ApiModelProperty(value = "排序", name = "sort", example = "1")
    private Integer sort;

    @OneToMany
    @JoinColumn(name = "mid",referencedColumnName = "mid",insertable = false,updatable = false)
    private List<Permission> permission;

}
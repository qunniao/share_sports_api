package com.gymnasium.module.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: ModulePermissionPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 11:23
 */

@Data
@Entity
@Table(name = "sys_module_role", catalog = "gymnasium")
@ApiModel(value = "ModulePermissionPO", description = "页面权限")
public class ModuleRolePO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "mid", example = "1")
    private Integer id;

    @Column(name = "mid")
    @ApiModelProperty(value = "modulId", name = "mid", example = "1")
    private Integer mid;

    @Column(name = "rid")
    @ApiModelProperty(value = "角色id", name = "rid", example = "1")
    private Integer rid;

}
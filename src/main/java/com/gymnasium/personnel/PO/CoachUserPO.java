package com.gymnasium.personnel.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: CoachUserPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/11 15:37
 */

@Data
@Entity
@Table(name = "personnel_coach_user", catalog = "gymnasium")
@ApiModel(value = "CoachUserPO", description = "健身房教练")
public class CoachUserPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "userId")
    @ApiModelProperty(value = "编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "gy_xxxx")
    private String gymShopId;

    @Column(name = "title")
    @ApiModelProperty(value = "职业说明", name = "title", example = "gy_xxxx")
    private String title;

    @Column(name = "jobYear")
    @ApiModelProperty(value = "从业年限", name = "jobYear", example = "gy_xxxx")
    private String jobYear;

    @Column(name = "goodat")
    @ApiModelProperty(value = "擅长", name = "goodat", example = "gy_xxxx")
    private String goodat;

    @Column(name = "name")
    @ApiModelProperty(value = "教练名称", name = "name", example = "教练名称")
    private String name;

    @Column(name = "resume")
    @ApiModelProperty(value = "履历", name = "resume", example = "履历")
    private String resume;

    @Column(name = "expertise")
    @ApiModelProperty(value = "专长", name = "expertise", example = "1")
    private String expertise;

    @Column(name = "status")
    @ApiModelProperty(value = "0无效,1有效", name = "status", example = "1")
    private Integer status;

    @Column(name = "url")
    @ApiModelProperty(value = "头像链接", name = "url", example = "1")
    private String url;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-08-01 11:11:01")
    private Timestamp createTime;
}

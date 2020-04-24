package com.gymnasium.personnel.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: UserAdditionPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/17 14:47
 */
@Data
@Entity
@Table(name = "user_addition", catalog = "gymnasium")
@ApiModel(value = "UserAdditionPO对象", description = "用户附加信息")
public class UserAdditionPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户附加信息", name = "id", example = "1")
    private Integer id;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "purpose")
    @ApiModelProperty(value = "目的", name = "purpose", example = "1")
    private Integer purpose;

    @Column(name = "bodyType")
    @ApiModelProperty(value = "体型", name = "bodyType", example = "1")
    private Integer bodyType;

    @Column(name = "figure")
    @ApiModelProperty(value = "身材", name = "figure", example = "1")
    private Integer figure;

    @Column(name = "age")
    @ApiModelProperty(value = "用户年龄", name = "age", example = "18")
    private Integer age;

    @Column(name = "job")
    @ApiModelProperty(value = "用户职业", name = "job")
    private Integer job;

    @Column(name = "income")
    @ApiModelProperty(value = "收入类型", name = "income")
    private Integer income;

    @Column(name = "crashName")
    @ApiModelProperty(value = "紧急联系人姓名", name = "crashName", example = "小王")
    private String crashName;

    @Column(name = "crashUserPhone")
    @ApiModelProperty(value = "紧急联系电话", name = "crashUserPhone", example = "1234567810")
    private String crashUserPhone;

    @Column(name = "height")
    @ApiModelProperty(value = "身高", name = "height", example = "175")
    private Integer height;

    @Column(name = "weight")
    @ApiModelProperty(value = "体重", name = "weight", example = "45")
    private Integer weight;

    @Column(name = "region")
    @ApiModelProperty(value = "区域", name = "region", example = "浙江省 xxx xxx")
    private String region;

    @Transient
    @ApiModelProperty(value = "用户真实姓名", name = "userName", example = "小王")
    private String userName;

    @Transient
    @ApiModelProperty(value = "身份证号", name = "identityNumber", example = "21464488xxx")
    private String identityNumber;

    @Transient
    @ApiModelProperty(value = "userNike", name = "userNike", example = "陈楠")
    private String userNike;

    @Transient
    @ApiModelProperty(value = "性别", name = "sex", example = "1")
    private Integer sex;

    @Column(name = "signature")
    @ApiModelProperty(value = "个性签名", name = "signature", example = "1")
    private String signature;

    @OneToOne(targetEntity = UserPO.class)
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false, unique = false)
    private UserPO userPO;

}
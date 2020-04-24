package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:14
 * @Description:
 */

@Data
@Entity
@Table(name = "service_personnel")
@ApiModel(value = "ServicePersonnel", description = "客服")
public class ServicePersonnelPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "manager_id")
    @ApiModelProperty(value = "后台用户id", name = "managerId", example = "1")
    private Integer managerId;

    @Column(name = "name")
    @ApiModelProperty(value = "姓名", name = "name", example = "1")
    private String name;

    @Column(name = "nick")
    @ApiModelProperty(value = "昵称", name = "nick", example = "1")
    private String nick;

    @Column(name = "work_number")
    @ApiModelProperty(value = "工号", name = "workNumber", example = "1")
    private String workNumber;

    @Column(name = "service_number")
    @ApiModelProperty(value = "服务人数", name = "serviceNumber", example = "1")
    private Integer serviceNumber;

    @Column(name = "cover")
    @ApiModelProperty(value = "头像", name = "cover", example = "1")
    private String cover;

    @Column(name = "jpush_name")
    @ApiModelProperty(value = "极光账号", name = "jpushName", example = "1")
    private String jpushName;

    @Column(name = "jpush_pwd")
    @ApiModelProperty(value = "极光密码", name = "jpushPwd", example = "1")
    private String jpushPwd;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;
}

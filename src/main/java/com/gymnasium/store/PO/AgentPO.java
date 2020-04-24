package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:13
 * @Description:
 */
@Data
@Entity
@Table(name = "agent")
@ApiModel(value = "AgentPO", description = "代理")
public class AgentPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "operation_id")
    @ApiModelProperty(value = "代理人id", name = "operationId", example = "1")
    private Integer operationId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "agent_code")
    @ApiModelProperty(value = "代理人编码", name = "agentCode", example = "1")
    private String agentCode;

    @Column(name = "level")
    @ApiModelProperty(value = "代理等级1.一级2.二级", name = "level", example = "1")
    private Integer level;

    @Column(name = "title")
    @ApiModelProperty(value = "机构名称", name = "title", example = "1")
    private String title;

    @Column(name = "address")
    @ApiModelProperty(value = "机构地址", name = "address", example = "1")
    private String address;

    @Column(name = "service_phone")
    @ApiModelProperty(value = "服务电话", name = "servicePhone", example = "113451164")
    private String servicePhone;

    @Column(name = "true_name")
    @ApiModelProperty(value = "真实姓名", name = "trueName", example = "张三")
    private String trueName;

    @Column(name = "id_card")
    @ApiModelProperty(value = "身份证", name = "idCard", example = "1")
    private String idCard;

    @Column(name = "phone")
    @ApiModelProperty(value = "联系电话", name = "phone", example = "13525346406")
    private String phone;

    @Column(name = "region")
    @ApiModelProperty(value = "所属区域", name = "region", example = "1")
    private String region;

    @Column(name = "username")
    @ApiModelProperty(value = "用户账号", name = "username", example = "1")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(value = "登录密码", name = "password", example = "1")
    private String password;

    @Column(name = "qr_code")
    @ApiModelProperty(value = "推广二维码", name = "qrCode", example = "1")
    private String qrCode;

    @Column(name = "audit_status")
    @ApiModelProperty(value = "审核状态", name = "auditStatus", example = "1")
    private Integer auditStatus;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;
}

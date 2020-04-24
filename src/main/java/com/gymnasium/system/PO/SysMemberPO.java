package com.gymnasium.system.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: SysMember
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/12 15:19
 */
@Data
@Entity
@Table(name = "sys_member", catalog = "gymnasium")
@ApiModel(value = "SysMemberPO", description = "系统会员配置")
public class SysMemberPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    @Column(name = "id")
    private Integer id;

    @Column(name = "level")
    @ApiModelProperty(value = "会员等级,0初始,1体验,2大众,3精英,4皇家", name = "level", example = "0")
    private Integer level;

    @Column(name = "price")
    @ApiModelProperty(value = "会员价格", name = "price", example = "99.000")
    private BigDecimal price;

    @Column(name = "name")
    @ApiModelProperty(value = "会员级别名称", name = "name", example = "黄金会员")
    private String name;

    @Column(name = "energy")
    @ApiModelProperty(value = "能量值", name = "energy", example = "20")
    private Double energy;

    @Column(name = "people_number")
    @ApiModelProperty(value = "使用人数", name = "peopleNumber", example = "2")
    private Integer peopleNumber;

    @Column(name = "average_number")
    @ApiModelProperty(value = "平均使用次数", name = "averageNumber", example = "20")
    private Double averageNumber;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "crateTime", example = "2019-08-08 11:11:11")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-08-08 11:11:11")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "content")
    @ApiModelProperty(value = "描述", name = "content", example = "描述")
    private String content;

    @Column(name = "cover")
    @ApiModelProperty(value = "封面图片", name = "cover", example = "描述")
    private String cover;

    @Column(name = "spare")
    @ApiModelProperty(value = "权限备用字段", name = "spare", example = "0.000")
    private String spare;

    @Column(name = "privilege_id")
    @ApiModelProperty(value = "多个特权券id", name = "privilegeId", example = "1,2,3")
    private String privilegeId;

    @Column(name = "gift_id")
    @ApiModelProperty(value = "多个礼包券id", name = "giftId", example = "1,2,3")
    private String giftId;
}
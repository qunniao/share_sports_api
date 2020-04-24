package com.gymnasium.record.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: CollectPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/20 13:50
 */
@Data
@Entity
@Table(name = "record_user", catalog = "gymnasium")
@ApiModel(value = "CollectPO", description = "收藏健身和教练")
public class CollectPO implements Serializable {

    @Id
    @Column(name = "cid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "cid", example = "1")
    private Integer cid;

    @Column(name = "userId")
    @ApiModelProperty(value = "本人用户编号", name = "userId", example = "gy_xxxx")
    private String userId;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "gy_xxxx")
    private String gymShopId;

    @Column(name = "coachId")
    @ApiModelProperty(value = "教练表主键", name = "coachId", example = "1")
    private Integer coachId;

    @Column(name = "type")
    @ApiModelProperty(value = "类型:1健身房,2教练", name = "coachId", example = "1")
    private Integer type;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "时间戳")
    private Timestamp createTime;

    @Transient
    @ApiModelProperty(value = "删除多个健身教练使用", name = "coachIdList", example = "1,2,3")
    private String coachIdList;
}

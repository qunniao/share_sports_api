package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GymCardLogPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/15 11:14
 */

@Data
@Entity
@Table(name = "gym_card_log" , catalog = "gymnasium")
public class GymCardLogPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "cardId")
    @ApiModelProperty(value = "卡片编号", name = "cardId", example = "1")
    private String cardId;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "健生房编号", name = "gymShopId", example = "1")
    private String gymShopId;

    @Column(name = "remarks")
    @ApiModelProperty(value = "操作备注", name = "remarks", example = "1")
    private String remarks;

    @Column(name = "createTime")
    @ApiModelProperty(value = "操作时间", name = "createTime", example = "1")
    private Timestamp createTime;

}

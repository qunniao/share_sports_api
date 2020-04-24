package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GymUserCardPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/15 11:49
 */
@Data
@Entity
@Table(name = "gym_user_card", catalog = "gymnasium")
@ApiModel(value = "GymUserCardPO", description = "人员健身卡")
public class GymUserCardPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    @ApiModelProperty(value = "人员编号", name = "level", example = "0")
    private Integer userId;

    @Column(name = "cardId")
    @ApiModelProperty(value = "卡号", name = "cardId", example = "0")
    private String cardId;

    @Column(name = "type")
    @ApiModelProperty(value = "1待激活,2已激活", name = "type", example = "0")
    private Integer type;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "0")
    private Timestamp createTime;
}

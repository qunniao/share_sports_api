package com.gymnasium.information.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 王志鹏
 * @title: GroupPraisePO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 13:48
 */
@Data
@Entity
@Table(name = "information_group_praise", catalog = "gymnasium")
@ApiModel(value = "GroupPraisePO", description = "点赞表")
public class GroupPraisePO implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "mid")
    @ApiModelProperty(value = "动态表主键", name = "mid", example = "1")
    private Integer mid;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "Rzl6c72Q190527")
    private String userId;
}

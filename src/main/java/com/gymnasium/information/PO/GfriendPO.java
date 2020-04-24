package com.gymnasium.information.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GfriendPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/23 10:40
 */

@Data
@Entity
@Table(name = "information_gfriend", catalog = "gymnasium")
@ApiModel(value = "GfriendPO好友记录", description = "好友记录")
public class GfriendPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "FG_046587")
    private Integer id;

    @ApiModelProperty(value = "master", name = "auid", example = "1")
    @Column(name = "auserId")
    private String auserId;

    @ApiModelProperty(value = "believer", name = "buid", example = "2")
    @Column(name = "buserId")
    private String buserId;

    @ApiModelProperty(value = "创建时间", name = "id", example = "FG_046587")
    @Column(name = "createTime")
    private Timestamp createTime;

}

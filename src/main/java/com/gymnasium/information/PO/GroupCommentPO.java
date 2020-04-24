package com.gymnasium.information.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: GroupCommentPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 13:31
 */
@Data
@Entity
@Table(name = "information_group_comment", catalog = "gymnasium")
@ApiModel(value = "GroupCommentPO", description = "评论表")
public class GroupCommentPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "mid")
    @ApiModelProperty(value = "动态表主键", name = "mid", example = "1")
    private Integer mid;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "ojmZOJsh190511")
    private String userId;

    @Column(name = "comment")
    @ApiModelProperty(value = "评论内容", name = "comment", example = "评论内容")
    private String comment;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-05-18 13:51:09")
    private Timestamp createTime;
}

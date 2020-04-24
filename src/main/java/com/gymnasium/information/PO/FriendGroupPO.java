package com.gymnasium.information.PO;

import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 王志鹏
 * @title: FriendGroupPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 12:02
 */
@Data
@Entity
@Table(name = "information_group", catalog = "gymnasium")
@ApiModel(value = "FriendGroupPO", description = "广场动态")
public class FriendGroupPO implements Serializable {

    @Id
    @Column(name = "mid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "mid", example = "1")
    private Integer mid;

    @Column(name = "userId")
    @ApiModelProperty(value = "用户编号", name = "userId", example = "1")
    private String userId;

    @Column(name = "content")
    @ApiModelProperty(value = "内容", name = "content", example = "1")
    private String content;

    @Column(name = "place")
    @ApiModelProperty(value = "地点", name = "place", example = "1")
    private String place;

    @Column(name = "longitude")
    @ApiModelProperty(value = "经度", name = "longitude", example = "1")
    private Double longitude;

    @Column(name = "latitude")
    @ApiModelProperty(value = "纬度", name = "latitude", example = "FG_046587")
    private Double latitude;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "id", example = "FG_046587")
    private Timestamp createTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid", referencedColumnName = "mid")
    private List<GroupPraisePO> groupPraisePOList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private UserPO userPO;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid", referencedColumnName = "mid", insertable = false, updatable = false)
    private List<GroupPictorePO> groupPictorePOList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mid", referencedColumnName = "mid", insertable = false, updatable = false)
    private List<GroupCommentPO> groupCommentPOList;

}

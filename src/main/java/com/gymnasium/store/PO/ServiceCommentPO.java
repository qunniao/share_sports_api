package com.gymnasium.store.PO;

import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:45
 * @Description:
 */

@Data
@Entity
@Table(name = "service_comment")
@ApiModel(value = "ServiceCommentPO", description = "评价客服")
public class ServiceCommentPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "service_id")
    @ApiModelProperty(value = "客服id", name = "serviceId", example = "1")
    private Integer serviceId;

    @Column(name = "level")
    @ApiModelProperty(value = "评分(1-5)", name = "level", example = "1")
    private Integer level;

    @Column(name = "content")
    @ApiModelProperty(value = "建议内容", name = "content", example = "1")
    private String content;

    @Column(name = "solved")
    @ApiModelProperty(value = "是否解决:0.没有解决1.已解决", name = "solved", example = "1")
    private Integer solved;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", referencedColumnName = "id")
    private List<UserPO> userPO;

}

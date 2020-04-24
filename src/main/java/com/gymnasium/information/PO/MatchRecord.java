package com.gymnasium.information.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 边书恒
 * @Title: MatchRecordDao
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/2 15:52
 */
@Data
@Entity
@Table(name = "match_record", catalog = "gymnasium")
@ApiModel(value = "搭伙匹配记录", description = "搭伙匹配记录")
public class MatchRecord implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "搭伙匹配记录主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id int类型", name = "userId", example = "43")
    private Integer userId;

    @Column(name = "page")
    @ApiModelProperty(value = "当前页码", name = "page", example = "1")
    private Integer page;

    @Column(name = "number")
    @ApiModelProperty(value = "剩余名额", name = "number", example = "15")
    private Integer number;

    @Column(name = "level")
    @ApiModelProperty(value = "会员等级", name = "level", example = "1")
    private Integer level;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;
}

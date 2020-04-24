package com.gymnasium.personnel.PO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/3 19:21
 * @Description:
 */
@Data
@Entity
@Table(name = "authentication")
public class Authentication implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "true_name")
    @ApiModelProperty(value = "姓名", name = "trueName", example = "张三")
    private String trueName;

    @Column(name = "id_card")
    @ApiModelProperty(value = "身份证号", name = "idCard", example = "45111245")
    private String idCard;

    @Column(name = "image")
    @ApiModelProperty(value = "手持照片", name = "image")
    private String image;

    @Column(name = "review_state")
    @ApiModelProperty(value = "审核状态：0.待审核1.审核通过2.拒绝", name = "reviewState;", example = "1")
    private Integer reviewState;

    @Column(name = "reject")
    @ApiModelProperty(value = "拒绝原因", name = "reject;", example = "1")
    private String reject;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status;", example = "1")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime;", example = "1")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "修改时间", name = "updateTime;", example = "1")
    private Date updateTime;
}

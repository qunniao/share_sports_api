package com.gymnasium.store.PO;

import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 边书恒
 * @Title: ServiceUser
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/19 14:43
 */
@ApiModel("客服用户表")
@Entity
@Data
@Table(name = "service_user")
@Accessors(chain = true)
public class ServiceUser implements Serializable {

    @Id
    @ApiModelProperty(value = "主键id", name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "客服id", name = "service_id")
    @Column(name = "service_id")
    private Integer serviceId;

    @ApiModelProperty(value = "用户id", name = "user_id")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty(value = "服务状态", name = "service_state")
    @Column(name = "service_state")
    private Integer serviceState;

    @ApiModelProperty(value = "创建时间", name = "createTime")
    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @OneToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ServicePersonnelPO servicePersonnelPO;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid", insertable = false, updatable = false)
    private UserPO userPO;
}

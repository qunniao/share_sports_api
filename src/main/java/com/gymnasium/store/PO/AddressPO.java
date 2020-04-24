package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/21 09:45
 * @Description:
 */
@Data
@Entity
@Table(name = "shopping_address")
@ApiModel(value = "AddressPO", description = "收货地址")
public class AddressPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "consignee")
    @ApiModelProperty(value = "收货人", name = "consignee", example = "1")
    private String consignee;

    @Column(name = "phone_number")
    @ApiModelProperty(value = "手机号", name = "phoneNumber", example = "1")
    private String phoneNumber;

    @Column(name = "area")
    @ApiModelProperty(value = "所在地区", name = "area", example = "1")
    private String area;

    @Column(name = "detailed_address")
    @ApiModelProperty(value = "详细地址", name = "detailedAddress", example = "1")
    private String detailedAddress;

    @Column(name = "is_default")
    @ApiModelProperty(value = "设置默认", name = "isDefault", example = "1")
    private Integer isDefault;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;
}

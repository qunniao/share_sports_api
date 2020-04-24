package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/17 19:14
 *
 */
@Entity
@Data
@Table(name = "product_type", catalog ="gymnasium")
@ApiModel(value = "ProductTypePO", description = "产品类型信息")
public class ProductTypePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "title")
    @ApiModelProperty(value = "分类名称", name = "title", example = "1")
    private String title;

    /**
     * 状态
     */
    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;


    @Column(name = "image")
    @ApiModelProperty(value = "图片", name = "image", example = "1")
    private String image;

    @Column(name = "settlement_type")
    @ApiModelProperty(value = "支付方式", name = "settlementType", example = "1")
    private Integer settlementType;

    /**
     * 创建时间
     */
    @Column(name = "create_Time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;



}
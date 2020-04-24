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
 * @createTime 2019/5/18 19:17
 *
 */
@Data
@Entity
@Table(name = "product_carousel")
@ApiModel(value = "ProductCarouselPO", description = "产品轮播图")
public class ProductCarouselPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品id", name = "productId", example = "1")
    private Integer productId;

    @Column(name = "name")
    @ApiModelProperty(value = "图片名称", name = "name", example = "1")
    private String name;

    @Column(name = "url")
    @ApiModelProperty(value = "图片路径", name = "url", example = "1")
    private String url;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", name = "status", example = "1")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;
}

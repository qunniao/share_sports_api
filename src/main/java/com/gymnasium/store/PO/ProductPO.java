package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/17 17:27
 */
@Data
@Entity
@Table(name = "product", catalog ="gymnasium")
@ApiModel(value = "ProductPO", description = "产品信息")
public class ProductPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "产品名称", name = "name", example = "1")
    private String name;

    @Column(name = "product_number")
    @ApiModelProperty(value = "产品编号", name = "productNumber", example = "1")
    private String productNumber;

    @Column(name = "product_type_id")
    @ApiModelProperty(value = "产品类型id", name = "productTypeId", example = "1")
    private Integer productTypeId;

    @Column(name = "settlement_type")
    @ApiModelProperty(value = "支付方式", name = "settlementType", example = "1")
    private Integer settlementType;

    @Column(name = "unit")
    @ApiModelProperty(value = "单位", name = "unit", example = "1")
    private Integer unit;

    @Column(name = "show_price")
    @ApiModelProperty(value = "展示价格", name = "showPrice", example = "1")
    private BigDecimal showPrice;

    @Column(name = "real_price")
    @ApiModelProperty(value = "实际价格", name = "realPrice", example = "1")
    private BigDecimal realPrice;

    @Column(name = "floor_price")
    @ApiModelProperty(value = "底价", name = "floorPrice", example = "1")
    private BigDecimal floorPrice;

    @Column(name = "intro")
    @ApiModelProperty(value = "简介", name = "intro", example = "1")
    private String intro;

    @Column(name = "image")
    @ApiModelProperty(value = "首图", name = "image", example = "1")
    private String image;

    @Column(name = "details")
    @ApiModelProperty(value = "详情介绍", name = "details", example = "1")
    private String details;

    @Column(name = "freight")
    @ApiModelProperty(value = "运费", name = "freight", example = "1")
    private BigDecimal freight;

    @Column(name = "putaway_status")
    @ApiModelProperty(value = "上架状态", name = "putawayStatus", example = "1")
    private Integer putawayStatus;

    @Column(name = "putaway_id")
    @ApiModelProperty(value = "上架人员", name = "putawayId", example = "1")
    private Integer putawayId;

    @Column(name = "inventory")
    @ApiModelProperty(value = "库存数量", name = "inventory", example = "1")
    private Integer inventory;

    @Column(name = "sales_volume")
    @ApiModelProperty(value = "销量", name = "salesVolume", example = "1")
    private Integer salesVolume;

    @Column(name = "recommend")
    @ApiModelProperty(value = "是否推荐: 0:否; 1:是", name = "recommend", example = "1")
    private Integer recommend;

    @Column(name = "style_name")
    @ApiModelProperty(value = "款式名称", name = "styleName", example = "1")
    private String styleName;

    @Column(name = "style")
    @ApiModelProperty(value = "款式", name = "style", example = "1")
    private String style;

    @Column(name = "param")
    @ApiModelProperty(value = "参数", name = "param", example = "1")
    private String param;

    @Column(name = "free_shipping")
    @ApiModelProperty(value = "是否免邮: 0:否; 1:是", name = "freeShipping", example = "1")
    private Integer freeShipping;

    @Column(name = "timely_delivery")
    @ApiModelProperty(value = "是否七天发货: 0:否; 1:是", name = "timelyDelivery", example = "1")
    private Integer timelyDelivery;

    @Column(name = "salable_product")
    @ApiModelProperty(value = "是否正品: 0:否; 1:是", name = "salableProduct", example = "1")
    private Integer salableProduct;

    @Column(name = "full_service")
    @ApiModelProperty(value = "是否全程服务", name = "fullService", example = "1")
    private Integer fullService;

    @Column(name = "page_view")
    @ApiModelProperty(value = "浏览量", name = "pageView", example = "1")
    private Integer pageView;

    @Column(name = "is_coupon")
    @ApiModelProperty(value = "是否可使用优惠券 : 0 否; 1 是", name = "isCoupon", example = "1")
    private Integer isCoupon;

    @Column(name = "is_agent")
    @ApiModelProperty(value = "是否代理: 0 否; 1 是", name = "isAgent", example = "1")
    private Integer isAgent;

    @Column(name = "is_channel")
    @ApiModelProperty(value = "是否渠道: 0 否; 1 是", name = "isChannel", example = "1")
    private Integer isChannel;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private List<ProductCarouselPO> productCarousel;
}
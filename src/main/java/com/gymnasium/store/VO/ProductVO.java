package com.gymnasium.store.VO;

import com.gymnasium.store.PO.ProductCarouselPO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/18 12:49
 *
 */
@Data
public class ProductVO implements Serializable {

    private Integer id;
    private String name;
    private String productNumber;
    private Integer productTypeId;
    private Integer settlementType;
    private Integer unit;
    private BigDecimal showPrice;
    private BigDecimal realPrice;
    private BigDecimal floorPrice;
    private String intro;
    private String image;
    private String details;
    private BigDecimal freight;
    private Integer putawayStatus;
    private Integer putawayId;
    private Integer inventory;
    private Integer salesVolume;
    private Integer recommend;
    private String styleName;
    private String style;
    private String param;
    private Integer freeShipping;
    private Integer timelyDelivery;
    private Integer salableProduct;
    private Integer fullService;
    private Integer pageView;
    private Integer isCoupon;
    private Integer isAgent;
    private Integer isChannel;
    private Date createTime;
    private Date updateTime;

    private List<ProductCarouselPO> productCarousel;

}

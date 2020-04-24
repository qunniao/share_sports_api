package com.gymnasium.store.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 19:35
 * @Description:
 */
@Data
public class ShopOrderVO{

    private Long id;

    private Integer userId;

    private Integer superior;

    private List<ProductInfoVO> productInfo;

    private String orderNumber;

    private String contactName;

    private String phone;

    private String address;

    private Integer addressId;

    private BigDecimal totalPrice;

    private BigDecimal payPrice;

    private BigDecimal discount;

    private BigDecimal freight;

    private Integer orderState;

    private String courierNumber;

    private String deliveryWay;

    private Integer settlementType;

    private Integer couponId;

    private Integer payWay;

    private String remark;

    private Date createTime;

    private Date payTime;

    private Date deliveryTime;

    private Date receivingTime;

    private Date lastTime;

    private Integer status;

}

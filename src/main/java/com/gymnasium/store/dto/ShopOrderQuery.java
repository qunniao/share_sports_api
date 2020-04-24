package com.gymnasium.store.dto;

import lombok.Data;

/**
 * @author tangchao
 */
@Data
public class ShopOrderQuery {

    private Integer pageNum;
    private Integer pageSize;
    private Integer settlementType;
    private Integer orderState;
    private String orderNumber;
    private String createTime;
    private String deliveryTime;
    private String keyword;
}

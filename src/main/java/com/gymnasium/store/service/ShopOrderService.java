package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ShopOrderPO;
import com.gymnasium.store.VO.ShopOrderVO;
import com.gymnasium.store.dto.ShopOrderQuery;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 19:15
 * @Description:
 */
public interface ShopOrderService {

    /**
     * 新增订单
     * @param shopOrderVO
     * @return
     */
    String insertShopOrder(ShopOrderVO shopOrderVO);

    /**
     * 更新订单
     * @param shopOrderVO
     * @return
     */
    Boolean updateShopOrder(ShopOrderVO shopOrderVO);

    /**
     * 发货接口
     * @param shopOrderVO
     * @return
     */
    Boolean shipments(ShopOrderVO shopOrderVO);

    /**
     * 收货接口
     * @param id
     * @param userId
     * @return
     */
    Boolean receiving(Long id, Integer userId);


    /**
     * 分页查询客户订单
     * @param role
     * @param userId
     * @param orderState
     * @param page
     * @return
     */
    Page<ShopOrderPO> pageCustomerOrder(Integer role,Integer userId, Integer orderState, Paging page);

    /**
     * 分页查询订单
     * @param page
     * @param shopOrderVO
     * @param year
     * @param month
     * @return
     */
    Page<ShopOrderPO> queryShopOrder(Paging page, ShopOrderVO shopOrderVO,Integer year, Integer month);


    Page<ShopOrderPO> queryOrder(ShopOrderQuery shopOrderQuery);

    /**
     * 根据订单id统计商品预计总佣金
     * @param orderId
     * @return
     */
    BigDecimal calculateTotalCommission(Long orderId);

    /**
     * 提交购物车
     * @param shopOrderVO
     * @param cartIds
     * @return
     */
    String submitCart(ShopOrderVO shopOrderVO, String cartIds);

    /**
     * 根据订单id查询订单详情
     * @param id
     * @return
     */
    ShopOrderPO queryShopOrderDetails(Long id);

    /**
     * 取消订单
     * @param id
     * @return
     */
    Boolean cancelShopOrder(Long id);

    /**
     * 实时查询快递
     * @param id
     * @return
     */
    String queryExpress(Long id);
}

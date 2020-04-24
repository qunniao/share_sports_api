package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ShopOrderPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 19:50
 * @Description:
 */
public interface ShopOrderDao extends JpaRepository<ShopOrderPO, Long>, JpaSpecificationExecutor<ShopOrderPO> {

    ShopOrderPO findByOrderNumber(String orderNumber);

    ShopOrderPO findShopOrderPOById(Long id);

    ShopOrderPO findByCourierNumber(String courierNumber);

    Integer countShopOrderPOByUserIdIn(List userId);

    Page<ShopOrderPO> findAllByUserIdAndOrderState(Integer userId, Integer orderState, Pageable pageable);

    ShopOrderPO findAllByStatus(String state);

    /**
     * 统计销售额
     * @param orderId
     * @return
     */
    @Query(value = "SELECT SUM(pay_price) FROM shop_order WHERE order_state = 4 AND id IN(:orderId)",
            nativeQuery = true)
    BigDecimal countOrderSales(List<Integer> orderId);

    @Query(value = "SELECT op.product_id FROM shop_order so INNER JOIN order_product op ON so.id = op.order_id \n" +
            "WHERE so.id = :orderId", nativeQuery = true)
    List<Integer> queryProductId(Long orderId);

    ShopOrderPO findByUserIdAndOrderNumber(Integer userId, String orderNumber);

}

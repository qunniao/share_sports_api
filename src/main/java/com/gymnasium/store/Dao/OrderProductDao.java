package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.OrderProductPO;
import com.gymnasium.store.VO.OrderProductVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/23 10:40
 * @Description:
 */
public interface OrderProductDao extends JpaRepository<OrderProductPO, Integer> {

    List<OrderProductPO> findByOrderId(Long orderId);

    @Query(value = "SELECT product_id FROM order_product WHERE order_id = :orderId", nativeQuery = true)
    List<Integer> queryProductId(Long orderId);

}

package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.CartPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/20 18:45
 * @Description:
 */
public interface CartDao extends JpaRepository<CartPO, Integer>, JpaSpecificationExecutor<CartPO> {

    List<CartPO> findAllByIdIn(long[] id);

    List<CartPO> findByIdIn(List id);

    CartPO findCartPOById(Long id);

    @Query(value = "SELECT COUNT(c.settlement_type ) AS amount,\n" +
            "sum(real_price * number ) AS totalPrice \n" +
            "FROM cart c LEFT JOIN product AS p ON c.product_id = p.id \n" +
            "WHERE user_id = :userId AND c.settlement_type = :settlementType", nativeQuery = true)
    Map<String, Object> countCart(Integer userId, Integer settlementType);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "delete FROM CartPO as c where id IN :id")
    Integer deleteCartPO(long[] id);

    CartPO findByUserIdAndProductIdAndStyleAndStatus(Integer userId, Integer productId, String style, Integer status);

    Integer deleteCartPOSByIdIn(List<Long> cartId);

    @Query(value = "SELECT * FROM cart WHERE user_id = ?1 AND settlement_type = ?2 AND IF(?3 != '', id IN (?3),1=1)",
            nativeQuery = true)
    List<CartPO> queryAll(Integer userId, Integer settlementType, long[] cartId);
}
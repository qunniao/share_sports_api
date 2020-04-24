package com.gymnasium.order.DAO;

import com.gymnasium.order.PO.CouponOperationPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 王志鹏
 * @title: CouponOperationDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 10:49
 */
public interface CouponOperationDao extends JpaRepository<CouponOperationPO, Integer>, JpaSpecificationExecutor<CouponOperationPO> {

    CouponOperationPO findById(int id);

    @Query(value = "SELECT * FROM order_coupon_operation  where NOW()<= expiration_time " +
            "AND verify_state = 2 AND id = :id",nativeQuery = true)
    CouponOperationPO queryCouPon(Integer id);
}
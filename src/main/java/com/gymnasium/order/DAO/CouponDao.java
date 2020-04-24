package com.gymnasium.order.DAO;

import com.gymnasium.order.PO.CouponPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author 王志鹏
 * @title: CouponDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 10:52
 */
public interface CouponDao extends JpaRepository<CouponPO, Integer>, JpaSpecificationExecutor<CouponPO> {

    CouponPO findById(int id);

    @Query(value = "SELECT * FROM order_coupon oc where NOW()-oc.create_time<= oc.validity*24*60*60 AND id = :id",
            nativeQuery = true)
    CouponPO queryCoupon(Integer id);

    /**
     * 根据会员等级查询需要发放的优惠券
     * @param memberLevel
     * @return
     */
    @Query(value = "SELECT * FROM order_coupon WHERE member_level= ?1 AND issue_way = 2 AND status=1", nativeQuery = true)
    List<CouponPO> queryIssueCoupon(Integer memberLevel);

    @Query(value = "SELECT oc.* FROM order_coupon_operation oco RIGHT JOIN order_coupon oc ON oc.id = oco" +
            ".coupon_id\n" +
            "WHERE user_id = :userId AND oco.status = 1 AND (:totalPrices > oc.price) " +
            "AND IF(:types = 2,oco.type = 1 OR oco.type = 2, oco.type = 3)",nativeQuery = true)
    Set<CouponPO> queryAvailableCouPon(String userId, BigDecimal totalPrices, Integer types);
}
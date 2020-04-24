package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.CashFlowPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/30 18:48
 * @Description:
 */
public interface CashFlowDao extends JpaRepository<CashFlowPO,Integer> {

    List<CashFlowPO> findByUserId(Integer userId);

    CashFlowPO findByOrderId(Long orderId);

    Integer countByUserIdAndSuperior(Integer userId, Integer superior);

    @Query(value = "SELECT SUM(agent_commission) FROM cash_flow WHERE user_id In (SELECT user_id FROM commission " +
            "WHERE second_level = :secondLevel And role = :role)",nativeQuery = true)
    BigDecimal sumAgentCommission(Integer secondLevel, Integer role);

    @Query(value = "SELECT SUM(client_commission) FROM cash_flow WHERE user_id In (SELECT user_id FROM commission " +
            "WHERE superior = :superior And role = :role)",nativeQuery = true)
    BigDecimal sumClientCommission(Integer superior, Integer role);

    /**
     *  统计总订单数， 统计总销售额， 统计总佣金
     * @param superior
     * @param userId
     * @return
     */
    @Query(value = "select COUNT(id) AS totalOrders, SUM(money) AS sales, SUM(client_commission) AS totalCommissions FROM \n" +
                "cash_flow where superior = :superior AND user_id = :userId",nativeQuery = true)
    Map<String, Object> sumSales(@Param("superior") Integer superior,@Param("userId") Integer userId);

    /**
     * 根据订单id统计代理佣金
     * @param orderId
     * @return
     */
    @Query(value = "SELECT SUM(agent_commission) FROM cash_flow WHERE order_id IN(:orderId);",nativeQuery = true)
    BigDecimal countAgentCommission(List<Integer> orderId);

    /**
     * 根据订单id统计客户佣金
     * @param orderId
     * @return
     */
    @Query(value = "SELECT SUM(client_commission) FROM cash_flow WHERE order_id IN(:orderId);",nativeQuery = true)
    BigDecimal countClientCommission(List<Integer> orderId);

    /**
     * 根据用户id集合 分别统计用户的订单数，销售额 总佣金
     * @param userId
     * @return
     */
    @Query(value = "SELECT SUM(cf.money)AS sales, SUM(cf.client_commission) AS totalCommission, " +
            "COUNT(cf.order_id) AS totalNumber, u.* FROM user u LEFT JOIN cash_flow cf ON u.uid = cf.user_id " +
            "WHERE uid IN(:userId) GROUP BY uid Limit :pageNum, :pagSize",
            nativeQuery = true)
    List<Map> countUserSales(List<Integer> userId, Integer pageNum, Integer pagSize);


    /**
     * 根据用户id集合 分别统计代理的订单数，销售额 总佣金
     * @param userId
     * @return
     */
    @Query(value = "SELECT SUM(cf.money)AS sales, SUM(cf.client_commission) AS totalCommission, " +
            "COUNT(cf.order_id) AS totalNumber, c.status as status, u.* FROM user u LEFT JOIN cash_flow cf ON u.uid = cf.superior " +
            "LEFT JOIN commission c ON c.user_id = u.uid where u.uid IN(:userId) GROUP BY uid, status Limit :pageNum, :pagSize",
            nativeQuery = true)
    List<Map> countAgentSales(List<Integer> userId, Integer pageNum, Integer pagSize);
}

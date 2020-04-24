package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.CommissionPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/29 10:00
 * @Description:
 */
public interface CommissionDao extends JpaRepository<CommissionPO,Integer>, JpaSpecificationExecutor<CommissionPO> {

    CommissionPO findByUserIdAndRole(Integer id, Integer role);

    List<CommissionPO> findAllByRoleAndUserIdIn(Integer role, List userId);

    List<CommissionPO> findAllByRoleAndSuperiorIn(Integer role, List userId);

    CommissionPO findByUserId(Integer userId);

    List<CommissionPO> findByUserIdIn(List<Integer> userId);

    List<CommissionPO> findBySecondLevel(Integer userId);

    List<CommissionPO> findAllBySuperior(Integer userId);

    /**
     * 查询客户团队
     * @param userId
     * @param role
     * @return
     */
    List<CommissionPO> findAllBySuperiorAndRole(Integer userId, Integer role);

    /**
     * 查询代理团队
     * @param userId
     * @param role
     * @param status
     * @return
     */
    List<CommissionPO> findAllBySuperiorAndRoleAndStatus(Integer userId, Integer role, Integer status);

    /**
     * 查询团队总人数
     * @param status
     * @param superior
     * @param secondLevel
     * @return
     */
    Integer countByStatusAndSuperiorOrSecondLevel(Integer status, Integer superior, Integer secondLevel);

    @Query(value = "SELECT role FROM commission WHERE user_id = :userId",nativeQuery = true)
    Integer queryRole(Integer userId);

    /**
     *  根据时间统计下级人数
     * @param userId
     * @param times
     * @return
     */
    @Query(value = "SELECT count(id) FROM commission WHERE superior = :userId  And create_time LIKE CONCAT(:times,'%')",nativeQuery = true)
    Integer countBySuperior(Integer userId, String times);

    @Query(value = "SELECT count(id) FROM commission WHERE superior = :userId  And create_time LIKE CONCAT(:times,'%') GROUP BY QUARTER(create_time)",nativeQuery = true)
    List<BigInteger> quarterBySuperior(Integer userId, String times);
    /**
     *  团队人数统计
     * @param role
     * @param superior
     * @param secondLevel
     * @return
     */
    @Query(value = "SELECT count(id) FROM commission WHERE role = :role And (superior = :superior OR second_level = :secondLevel) And create_time LIKE CONCAT (:times,'%')",
            nativeQuery = true)
    Integer countTeamSize(Integer role, Integer superior, Integer secondLevel, String times);

    @Query(value = "SELECT count(id) FROM commission WHERE role = :role And (superior = :superior OR second_level = :secondLevel) And create_time LIKE CONCAT (:times,'%') GROUP BY QUARTER(create_time)", nativeQuery = true)
    List<BigInteger> quarterTeamSize(Integer role, Integer superior, Integer secondLevel, String times);

    /**
     * 统计代理佣金
     * @param secondLevel
     * @param role
     * @return
     */
    @Query(value = "SELECT SUM(agent_commission) FROM cash_flow WHERE user_id In (SELECT user_id FROM commission " +
                   "WHERE second_level = :secondLevel And role = :role) And create_time LIKE CONCAT(:times,'%')",nativeQuery = true)
    BigDecimal countAgentCommission(Integer secondLevel, Integer role, String times);

    @Query(value = "SELECT SUM(agent_commission) FROM cash_flow WHERE user_id In (SELECT user_id FROM commission " +
            "WHERE second_level = :secondLevel And role = :role) And create_time LIKE CONCAT(:times,'%') GROUP BY QUARTER(create_time)",nativeQuery = true)
    List<BigDecimal> quarterAgentCommission(Integer secondLevel, Integer role, String times);

    /**
     * 统计用户佣金
     * @param superior
     * @param role
     * @return
     */
    @Query(value = "SELECT SUM(client_commission) FROM cash_flow WHERE user_id In (SELECT user_id FROM commission " +
            "WHERE superior = :superior And role = :role) And create_time LIKE CONCAT (:times,'%')",nativeQuery = true)
    BigDecimal countClientCommission(Integer superior, Integer role, String times);

    @Query(value = "SELECT SUM(client_commission) FROM cash_flow WHERE user_id In (SELECT user_id FROM commission " +
            "WHERE superior = :superior And role = :role) And create_time LIKE CONCAT (:times,'%') GROUP BY QUARTER(create_time)",nativeQuery = true)
    List<BigDecimal> quarterClientCommission(Integer superior, Integer role, String times);

    /**
     * 根据 身份和上级id查询用户id列表
     * @param role
     * @param superior
     * @return
     */
    @Query(value ="SELECT user_id FROM commission  WHERE role = :role And superior = :superior",nativeQuery = true)
    List<Integer> queryUser(Integer role, Integer superior);
    
    /**
     * 查询客户团队，查询 role 为 0 或者 1的
     * @param role
     * @param role1
     * @param superior
     * @return
     */
    @Query(value ="SELECT user_id FROM commission  WHERE superior = :superior AND (role = :role OR role = :role1)",
            nativeQuery = true)
    List<Integer> queryCustomerTeam(Integer superior, Integer role, Integer role1);

    @Query(value ="SELECT id FROM shop_order WHERE user_id IN (?1)",nativeQuery = true)
    List<Integer> queryOrder(List<Integer> userList);


    @Query(value ="SELECT user_id FROM commission WHERE role = :role And second_level = :secondLevel",nativeQuery =
            true)
    List<Integer> queryTeam(Integer role, Integer secondLevel);

    @Query(value ="SELECT user_id FROM commission WHERE second_level = :secondLevel AND (role = :role OR role = " +
            ":role1)",
            nativeQuery = true)
    List<Integer> queryAgentTeam(Integer secondLevel, Integer role, Integer role1);

    /**
     *  分配佣金
     * @param balance
     * @param profits
     * @param canCarry
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "UPDATE commission SET balance = balance + :balance, profits = profits + :profits, can_carry = can_carry + :canCarry WHERE user_id = :userId", nativeQuery = true)
    int updateCommission(@Param("balance") BigDecimal balance, @Param("profits") BigDecimal profits,@Param("canCarry") BigDecimal canCarry, @Param("userId") Integer userId);


    /**
     * 统计总人数，总佣金，和总销售额
     * @param userId
     * @param role
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT COUNT( DISTINCT c.id, c.id ) AS amount, SUM(profits)AS  totalCommission," +
            " SUM( cf.money ) AS sales FROM commission c INNER JOIN cash_flow cf ON c.user_id = cf.user_id \n" +
            "WHERE c.superior = :userId AND c.role = :role")
    Map<String, Integer> teamStatistics(Integer userId, Integer role);

}

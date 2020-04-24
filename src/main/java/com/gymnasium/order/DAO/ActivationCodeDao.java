package com.gymnasium.order.DAO;

import com.gymnasium.order.PO.ActivationCodePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 王志鹏
 * @title: ActivationCodeDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/16 16:33
 */
public interface ActivationCodeDao extends JpaRepository<ActivationCodePO, Integer> , JpaSpecificationExecutor<ActivationCodePO> {

    /**
     *  查询 转卡记录
     * @param activationCode
     * @return
     */
    ActivationCodePO queryByActivationCode(String activationCode);

    /**
     * 统计健身房转卡总数
     * @param gymShopId
     * @return
     */
    Integer countByGymShopId(String gymShopId);


    /**
     * 转卡次数
     * @param gymShopId
     * @param times
     * @return
     */
    @Query(value = "SELECT count(id) FROM order_activation_code WHERE if (?1!='',gymShopId = ?1,1=1) AND createTime " +
            "LIKE CONCAT(?2,'%')",
            nativeQuery = true)
    Integer countActivationCode(String gymShopId, String times);

    /**
     * 按季度统计转卡次数
     * @param gymShopId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT count(id) FROM order_activation_code\n" +
            "WHERE if (?1!='',gymShopId = ?1,1=1) createTime BETWEEN ?2 AND ?3 ", nativeQuery = true)
    Integer quarterActivationCode(String gymShopId, String startTime, String endTime);

    /**
     * 统计转卡人数
     * @param gymShopId
     * @param times
     * @return
     */
    @Query(value = "SELECT count(DISTINCT userId,userId) FROM order_activation_code WHERE gymShopId = ?1 AND " +
            "createTime LIKE CONCAT(?2,'%')", nativeQuery = true)
    Integer countGymTurnCard(String gymShopId, String times);

    /**
     * 按季度统计健身房转卡人数
     * @param gymShopId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT count(DISTINCT userId,userId) FROM order_activation_code\n" +
            "WHERE gymShopId = ?1 AND createTime BETWEEN ?2 AND ?3 ", nativeQuery = true)
    Integer quarterGymTurnCard(String gymShopId, String startTime, String endTime);

    // 统计总人数
    @Query(value = "SELECT count(DISTINCT userId,userId) FROM order_activation_code\n" +
            "WHERE gymShopId = ?1", nativeQuery = true)
    Integer countHeadcount(String gymShopId);

    /**
     * 根据 订单编号查询转卡订单
     * @param orderNumber
     * @return
     */
    ActivationCodePO findByOrderNumber(String orderNumber);
}

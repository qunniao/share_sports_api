package com.gymnasium.order.DAO;

import com.gymnasium.order.PO.ExpensesRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: ExpensesRecordDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/16 10:49
 */
public interface ExpensesRecordDao extends JpaRepository<ExpensesRecordPO, Integer>, JpaSpecificationExecutor<ExpensesRecordPO> {

    List<ExpensesRecordPO> queryByUserIdAndType(String userId, int type);

    /**
     * 累计能量值
     * @param userId
     * @param titleType
     * @return
     */
    @Query(value = "SELECT SUM(oue.energy) FROM order_user_expensesrecord oue " +
            "WHERE userId  = :userId AND titleType = :titleType ",nativeQuery = true)
    Double queryCumulativeEnergy(String userId, Integer titleType);


    Integer countByShopId(String shopId);

    /**
     * 查询用户的能量值
     * @param userId
     * @return
     */
    @Query(value = "SELECT energy FROM user u WHERE userId = :userId",nativeQuery = true)
    Double queryEnergy(String userId);

    @Query(value = "select SUM(energy) from order_user_expensesrecord oue \n" +
            "where userId = ?1 AND  if (?2!='',titleType = ?2,1=1) \n" +
            "AND operatingTime BETWEEN ?3 AND ?4",
    nativeQuery = true)
    Double statisticalEnergy(String userId,Integer titleType, String startTime, String endTime);


    @Query(value = "select SUM(energy) AS energys from order_user_expensesrecord oue \n" +
            "where userId = ?1 AND if (?2!='',titleType = ?2,1=1) \n" +
            "AND operatingTime LIKE CONCAT(?3,'%') ",
            nativeQuery = true)
    Double countEnergyByLike(String userId,Integer titleType, String times);


}
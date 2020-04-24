package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.PartnerRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: PartnerRecordDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/23 15:41
 */

public interface PartnerRecordDao extends JpaRepository<PartnerRecordPO, Integer>, JpaSpecificationExecutor<PartnerRecordPO> {

    List<PartnerRecordPO> queryByAuserIdAndBuserId(String auserId, String buserId);

    List<PartnerRecordPO> queryByAuserIdAndBuserIdOrderByIdDesc(String auserId, String buserId);

    List<PartnerRecordPO> queryByBuserId(String buserId);

    List<PartnerRecordPO> queryByPromiseTime(String promiseTime);

//    @Query(value = "SELECT * FROM infromation_partnerrecord WHERE IF(?1 != '', auserId LIKE CONCAT('%',?1,'%') OR " +
//            "buserId LIKE CONCAT('%',?1,'%'), 1=1) AND IF (?2 != '', gymShopId LIKE CONCAT('%',?2,'%'), 1=1)",
//            nativeQuery = true)
//    List<PartnerRecordPO> queryTest(String userId, String gymShopId);
//}

    List<PartnerRecordPO> findAllByGymShopIdAndAuserIdOrBuserId(String gymShopId, String aUserId, String bUserId);

    Integer countByGymShopId(String gymShopId);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE gymShopId = ?1 " +
            "AND createTime  BETWEEN ?2 AND ?3",
            nativeQuery = true)
    Integer quarterPartnerRecord(String gymShopId, String startTime, String endTime);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE gymShopId = ?1 " +
            "AND createTime like CONCAT(?2, '%') ", nativeQuery = true)
    Integer countPartnerRecord(String gymShopId, String times);

    Integer countAllByAuserIdOrBuserId(String aUserId, String bUserId);

    Integer countAllByTypeAndAuserIdOrBuserId(Integer type, String aUserId, String bUserId);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE (auserId = ?1 OR buserId = ?2) AND " +
            "createTime BETWEEN ?3 AND ?4", nativeQuery = true)
    Integer quarterUserTotalNumber(String aUserId, String bUserId, String startTime, String endTime);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE createTime LIKE CONCAT(?1,'%') AND " +
            "(auserId = ?2 OR buserId = ?3) ", nativeQuery = true)
    Integer countUserTotalNumber(String times, String aUserId, String bUserId);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE type = ?1 And (auserId = ?2 OR buserId = " +
            "?3) AND createTime BETWEEN ?4 AND ?5", nativeQuery = true)
    Integer quarterUserSuccessfulNumber(Integer type,String aUserId, String bUserId, String startTime, String endTime);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE type = ?1 And createTime LIKE CONCAT(?2," +
            "'%') AND (auserId = ?3 OR buserId = ?4) ", nativeQuery = true)
    Integer countUserSuccessfulNumber(Integer type,String times, String aUserId, String bUserId);

    @Query(value = "SELECT COUNT(id) FROM infromation_partnerrecord WHERE IF(?1 != '', promiseTime = ?1,1=1) " +
            "AND type = ?2 AND createTime BETWEEN ?3 AND ?4", nativeQuery = true)
    Integer quarterSuccessfulNumber(String promiseTime, Integer type, String startTime, String endTime);
}
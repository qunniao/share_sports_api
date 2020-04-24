package com.gymnasium.store.Dao;
import com.gymnasium.store.PO.AgentPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:38
 * @Description:
 */
public interface AgentDao extends JpaRepository<AgentPO, Integer>, JpaSpecificationExecutor<AgentPO> {

    AgentPO findAgentPOById(Integer id);

    AgentPO findByOperationId(Integer operationId);

    AgentPO findByUserId(Integer userId);

    @Query(value = "SELECT COUNT(id) FROM agent\n" +
            "WHERE status = ?1 AND IF(?2 !='', region LIKE CONCAT('%',?2,'%'), 1=1) AND level = ?3\n" +
            "AND create_time BETWEEN ?4 AND ?5", nativeQuery = true)
    Integer quarterAgentNumber(Integer status, String region, Integer level, String startTime, String endTime);


    @Query(value = "SELECT COUNT(id) FROM agent " +
            "WHERE status = ?1 AND IF(?2 !='', region LIKE CONCAT('%',?2,'%'),1=1) AND level = ?3 " +
            "AND create_time CONCAT(?4,'%')", nativeQuery = true)
    Integer countAgentNumber(Integer status, String region, Integer level, String times);


}

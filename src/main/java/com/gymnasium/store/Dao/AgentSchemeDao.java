package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.AgentSchemePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:12
 * @Description:
 */
public interface AgentSchemeDao extends JpaRepository<AgentSchemePO, Integer>, JpaSpecificationExecutor<AgentSchemePO> {

    AgentSchemePO findByProductId(Integer productId);

    AgentSchemePO findByUserId(Integer userId);

    List<AgentSchemePO> findByTypeOrderByMemberLevelAsc(Integer type);

    AgentSchemePO findByTypeAndMemberLevel(Integer type,Integer memberLevel);

    AgentSchemePO findAgentSchemePOById(Integer id);


    /**
     * 根据方案类型和产品名称模糊查询代理方案
     * @param name
     * @param type
     * @return
     */
    @Query(value = "SELECT a.* FROM agent_scheme a INNER JOIN product p ON a.product_id = p.id " +
            "WHERE p.name LIKE CONCAT ('%',?1,'%') AND IF(?2 != '', a.type = ?2, 1=1)",nativeQuery = true)
    List<AgentSchemePO> queryAgentScheme(String name, Integer type);

}

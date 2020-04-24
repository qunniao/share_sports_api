package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.PO.ChannelSchemePO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:11
 * @Description:
 */
public interface ChannelSchemeDao extends JpaRepository<ChannelSchemePO, Integer>,
        JpaSpecificationExecutor<ChannelSchemePO> {

    ChannelSchemePO findByUserIdAndLevelAndType(Integer userId, Integer level, Integer type);

    ChannelSchemePO findByProductId(Integer productId);

    ChannelSchemePO findByUserId(Integer userId);

    ChannelSchemePO findByUserIdAndProductId(Integer userId, Integer productId);

    ChannelSchemePO findByUserIdAndProductIdAndOperatorId(Integer userId, Integer productId, Integer operatorId);

    ChannelSchemePO findByChannelCode(String channelCode);

    ChannelSchemePO findChannelSchemePOById(Integer id);

    ChannelSchemePO findByProductIdAndLevel(Integer productId, Integer level);

    ChannelSchemePO findByOperatorIdAndProductId(Integer operatorId, Integer productId);

    Page<ChannelSchemePO> findAllByLevel(Integer level, Pageable pageable);

    Page<ChannelSchemePO> findAllByUserIdAndLevel(Integer userId, Integer level, Pageable pageable);

    ChannelSchemePO findByProductIdAndLevelAndOperatorId(Integer productId, Integer level, Integer operatorId);

    Page<ChannelSchemePO> findByLevelAndType(Integer level, Integer type, Pageable pageable);

    Page<ChannelSchemePO> findAllByUserIdAndLevelAndType(Integer UserId, Integer level, Integer type, Pageable pageable);

    /**
     * 根据产品名称和方案类型查询渠道方案
     * @param name
     * @param type
     * @return
     */
    @Query(value = "SELECT c.* FROM channel_scheme c INNER JOIN product p ON c.product_id = p.id " +
            "WHERE p.name LIKE CONCAT ('%',?1,'%') AND IF(?2 != '', c.type = ?2, 1=1) AND level = ?3 " +
            "AND IF(?4 != '', operator_id = ?4, 1=1)",nativeQuery = true)
    List<ChannelSchemePO> queryChannelScheme(String name, Integer type, Integer level, Integer operatorId);

    @Query(value = "select * from  channel_scheme WHERE type = ?1 AND member_level = ?2 AND " +
            "level = ?3 AND IF (?4 != '', userId = ?4, 1=1)" ,nativeQuery = true)
    ChannelSchemePO queryMemberScheme(Integer Type, Integer memberLevel, Integer level, Integer userId);

    ChannelSchemePO findByUserIdAndMemberLevelAndLevelAndType(Integer userId, Integer memberLevel, Integer level,Integer type);

    ChannelSchemePO findByMemberLevelAndLevelAndType(Integer memberLevel, Integer level, Integer type);

    List<ChannelSchemePO> findAllByTypeAndLevelOrderByMemberLevelAsc(Integer type, Integer level);

    List<ChannelSchemePO> findAllByUserIdAndTypeAndLevelOrderByMemberLevelAsc(Integer userId, Integer type, Integer level);
}

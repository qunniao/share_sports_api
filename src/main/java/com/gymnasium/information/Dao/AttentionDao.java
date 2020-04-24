package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.AttentionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AttentionDao extends JpaRepository<AttentionPo, Integer>, JpaSpecificationExecutor<AttentionPo> {

    AttentionPo findByUserIdAndFollowUserId(String userId, String followUserId);

    void deleteByUserIdAndFollowUserId(String userId, String followUserId);

    List<AttentionPo> findByUserId(String userId);

    Integer countByUserId(String userId);
}

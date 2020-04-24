package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.MatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 边书恒
 * @Title: MatchRecordDao
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/2 15:58
 */
public interface MatchRecordDao extends JpaRepository<MatchRecord, Integer> {

    MatchRecord findAllByUserId(Integer userId);
}

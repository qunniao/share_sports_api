package com.gymnasium.card.Dao;

import com.gymnasium.card.PO.ActivationRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/28 14:26
 * @Description:
 */
public interface ActivationRecordDao extends JpaRepository<ActivationRecordPO, Integer> {

    ActivationRecordPO findByCardNum(String cardNum);

    @Query(value = "SELECT * FROM card_record_activation WHERE type = 1 AND useUserId = :useUserId ORDER BY operTime DESC LIMIT 1", nativeQuery = true)
    ActivationRecordPO queryNewestRecord(String useUserId);

}

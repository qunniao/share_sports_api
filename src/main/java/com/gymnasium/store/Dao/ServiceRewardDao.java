package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ServiceRewardPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 16:37
 * @Description:
 */
public interface ServiceRewardDao extends JpaRepository<ServiceRewardPO, Integer> {

    Integer countByServiceId(Integer serviceId);
}

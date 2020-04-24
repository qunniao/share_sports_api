package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ChannelPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:39
 * @Description:
 */
public interface ChannelDao  extends JpaRepository<ChannelPO, Integer>, JpaSpecificationExecutor<ChannelPO> {

    ChannelPO findChannelPOById(Integer id);

    ChannelPO findByOperationId(Integer id);

    ChannelPO findByUserId(Integer userId);





}

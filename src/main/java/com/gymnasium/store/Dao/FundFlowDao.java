package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.FundFlowPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/31 14:41
 * @Description:
 */
public interface FundFlowDao extends JpaRepository<FundFlowPO, Integer>, JpaSpecificationExecutor<FundFlowPO> {

    Page<FundFlowPO> findByUserIdAndType(Pageable pageable, Integer userId, Integer type);

}

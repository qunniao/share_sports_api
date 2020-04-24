package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ServiceCommentPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 16:36
 * @Description:
 */
public interface ServiceCommentDao extends JpaRepository<ServiceCommentPO, Integer> {

    @Query(value = "SELECT AVG(level) FROM service_comment sc WHERE sc.service_id = :serviceId", nativeQuery = true)
    Integer queryLevel(Integer serviceId);

    List<ServiceCommentPO> findAllByServiceId(Integer serviceId);

    /**
     * 查询客服历史评论总数
     * @param serviceId
     * @param status
     * @return
     */
    Integer countByServiceIdAndStatus(Integer serviceId, Integer status);
}

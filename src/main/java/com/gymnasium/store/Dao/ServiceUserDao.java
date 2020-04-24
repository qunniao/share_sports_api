package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 边书恒
 * @Title: ServiceUserDao
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/19 14:51
 */
public interface ServiceUserDao extends JpaRepository<ServiceUser, Integer> {

    ServiceUser findByUserId(Integer userId);
}

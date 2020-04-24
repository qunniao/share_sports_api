package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/3 19:36
 * @Description:
 */
public interface AuthenticationDao extends JpaRepository<Authentication,Integer>, JpaSpecificationExecutor<Authentication> {

    Authentication findByUserId(Integer userId);
}

package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.FriendGroupPO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 王志鹏
 * @title: FriendGroupPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 14:24
 */
public interface FriendGroupDao extends JpaRepository<FriendGroupPO, Integer>, JpaSpecificationExecutor<FriendGroupPO> {


    List<FriendGroupPO> findFirst10ByContentLike(String content, Pageable pageable);

    List<FriendGroupPO> findByUserId(String userId);

}

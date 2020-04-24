package com.gymnasium.stadium.Dao;

import com.gymnasium.stadium.PO.GymSubjectPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/4/6 12:25
 **/
public interface GymSubjectDao extends JpaRepository<GymSubjectPO, Integer> {
    
    List<GymSubjectPO> queryByGid(int gid);

    Integer deleteGymSubjectPOById(Integer id);
}

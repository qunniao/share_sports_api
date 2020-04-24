package com.gymnasium.personnel.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.file.VO.GymImagesVO;
import com.gymnasium.personnel.PO.CoachUserPO;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CoachUserService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/11 17:37
 */
public interface CoachUserService {

    List<CoachUserPO> queryCoachUserByGymShopId(String gymShopId);

    CoachUserPO queryCoachUserByUserId(String userId);

    CoachUserPO addCoachUser(CoachUserPO coachUserPO);

    //更新添或添加头像
    boolean updateCoachUserAvatar(GymImagesVO gymImagesVO);


    Result deleteCoachUserById(Integer coachUserId);

    Result updateCoachUserById(CoachUserPO coachUserPO);

}

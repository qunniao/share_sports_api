package com.gymnasium.personnel.Service.ServiceImpl;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.DateUtil;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.RandomUtil;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.file.VO.GymImagesVO;
import com.gymnasium.personnel.Dao.CoachUserDao;
import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.personnel.Service.CoachUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: CoachUserServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/11 17:37
 */

@Service
public class CoachUserServiceImpl implements CoachUserService {

    @Autowired
    private CoachUserDao coachUserDao;

    @Override
    public List<CoachUserPO> queryCoachUserByGymShopId(String gymShopId) {

        return coachUserDao.queryByGymShopId(gymShopId);
    }

    @Override
    public CoachUserPO queryCoachUserByUserId(String userId) {

        return coachUserDao.queryByUserId(userId);
    }

    @Override
    public CoachUserPO addCoachUser(CoachUserPO coachUserPO) {


        coachUserPO.setUserId("JL_" + RandomUtil.getShortUuid() + DateUtil.current());
        coachUserPO.setStatus(SysConstant.STATUS_Enable_INT.getConstant());
        coachUserPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));

        return coachUserDao.save(coachUserPO);
    }

    @Override
    public boolean updateCoachUserAvatar(GymImagesVO gymImagesVO) {

        CoachUserPO coachUserPO = coachUserDao.queryByUserId(gymImagesVO.getUserId());

        if (ObjectUtils.anyNotNull(coachUserPO)) {
            throw new SCException(10010, "教练不存在");
        }

        coachUserPO.setUrl(gymImagesVO.getUrl());

        coachUserDao.save(coachUserPO);

        return true;
    }

    @Override
    @Transactional
    public Result deleteCoachUserById(Integer coachUserId) {

        coachUserDao.updateStatusById(coachUserId);

        return ResultUtil.success();
    }


    @Override
    public Result updateCoachUserById(CoachUserPO coachUserPO) {
        CoachUserPO query = coachUserDao.queryById(coachUserPO.getId());
        if (coachUserPO.getUserId() != null) query.setUserId(coachUserPO.getUserId());
        if (coachUserPO.getGymShopId() != null) query.setGymShopId(coachUserPO.getGymShopId());
        if (coachUserPO.getTitle() != null) query.setTitle(coachUserPO.getTitle());
        if (coachUserPO.getJobYear() != null) query.setJobYear(coachUserPO.getJobYear());
        if (coachUserPO.getGoodat() != null) query.setGoodat(coachUserPO.getGoodat());
        if (coachUserPO.getName() != null) query.setName(coachUserPO.getName());
        if (coachUserPO.getResume() != null) query.setResume(coachUserPO.getResume());
        if (coachUserPO.getExpertise() != null) query.setExpertise(coachUserPO.getExpertise());
        if (coachUserPO.getUrl() != null) query.setUrl(coachUserPO.getUrl());
        coachUserDao.save(query);
        return ResultUtil.success();
    }
}

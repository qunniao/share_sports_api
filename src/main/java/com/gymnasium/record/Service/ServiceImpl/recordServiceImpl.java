package com.gymnasium.record.Service.ServiceImpl;

import com.gymnasium.Util.DateUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.personnel.Dao.CoachUserDao;
import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.record.Dao.CollectDao;
import com.gymnasium.record.PO.CollectPO;
import com.gymnasium.record.Service.RecordService;
import com.gymnasium.record.VO.UserCollectVO;
import com.gymnasium.stadium.Dao.GymShopDao;
import com.gymnasium.stadium.PO.GymShopPO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: recordServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/20 14:32
 */
@Service
public class recordServiceImpl implements RecordService {

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private GymShopDao gymShopDao;

    @Autowired
    private CoachUserDao coachUserDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCollectPO(CollectPO collectPO) {

        System.err.println(collectPO);

        int type = collectPO.getType();
        String uerId = collectPO.getUserId();
        if (type == 1) {
            CollectPO collectPO1 = collectDao.queryByUserIdAndGymShopId(uerId, collectPO.getGymShopId());
            if (collectPO1 != null) {
                throw new SCException(123123, "已经收藏过本健身房");
            }
        } else {
            CollectPO collectPO2 = collectDao.queryByUserIdAndCoachId(uerId, collectPO.getCoachId());
            if (collectPO2 != null) {
                throw new SCException(123123, "已经收藏过本教练");
            }
        }

        collectPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        collectDao.save(collectPO);
        return true;
    }

    @Override
    public UserCollectVO queryCollectAll(CollectPO collectPO) {

        List<CollectPO> list = collectDao.queryByUserIdAndType(collectPO.getUserId(), 1);//jiansf
        List<CollectPO> list2 = collectDao.queryByUserIdAndType(collectPO.getUserId(), 2);//jiaolian

        UserCollectVO userCollectVO = new UserCollectVO();

        List<GymShopPO> shopPOList = new ArrayList<>();
        List<CoachUserPO> coachUserPOList = new ArrayList<>();

        for (CollectPO collectPO1 : list) {
            GymShopPO gymShopPO = gymShopDao.findByGymShopId(collectPO1.getGymShopId());

            if (ObjectUtils.anyNotNull(gymShopPO)){
                shopPOList.add(gymShopPO);
            }
        }

        for (CollectPO collectPO1 : list2) {
            CoachUserPO coachUserPO = coachUserDao.queryById(collectPO1.getCoachId());

            if (ObjectUtils.anyNotNull(coachUserPO)){
                coachUserPOList.add(coachUserPO);
            }
        }

        userCollectVO.setUserId(collectPO.getUserId());
        userCollectVO.setCoachUserPOList(coachUserPOList);
        userCollectVO.setShopPOList(shopPOList);

        System.err.println(userCollectVO);

        return userCollectVO;
    }

}

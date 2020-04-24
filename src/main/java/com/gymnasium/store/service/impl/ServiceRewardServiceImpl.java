package com.gymnasium.store.service.impl;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.store.Dao.ServiceRewardDao;
import com.gymnasium.store.PO.ServiceCommentPO;
import com.gymnasium.store.PO.ServiceRewardPO;
import com.gymnasium.store.VO.ServiceCommentVO;
import com.gymnasium.store.VO.ServiceRewardVO;
import com.gymnasium.store.service.ServiceRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 16:45
 * @Description:
 */
@Service
public class ServiceRewardServiceImpl implements ServiceRewardService {

    @Autowired
    private ServiceRewardDao serviceRewardDao;

    @Override
    public Boolean insertServiceReward(ServiceRewardVO serviceRewardVO) {

        ServiceRewardPO serviceRewardPO = new ServiceRewardPO();

        BeanUtil.copyProperties(serviceRewardVO, serviceRewardPO);

        serviceRewardPO.setCreateTime(new Date());
        serviceRewardPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        serviceRewardDao.save(serviceRewardPO);

        return null;
    }

    @Override
    public Integer countByServiceId(Integer serviceId) {

        return serviceRewardDao.countByServiceId(serviceId);
    }
}

package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.store.Dao.ServicePersonnelDao;
import com.gymnasium.store.Dao.ServiceUserDao;
import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.PO.ServiceUser;
import com.gymnasium.store.service.ServiceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 边书恒
 * @Title: ServiceUserServiceImpl
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/19 14:54
 */
@Service
public class ServiceUserServiceImpl implements ServiceUserService {

    @Autowired
    private ServiceUserDao serviceUserDao;

    @Autowired
    private ServicePersonnelDao servicePersonnelDao;

    @Override
    public Boolean addServiceUser(Integer userId) {

        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.queryMinNumber();

        if (ObjectUtil.isNull(servicePersonnelPO)){
            return false;
        }

        // 客服id
        Integer serviceId = servicePersonnelPO.getId();

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setServiceId(serviceId)
                   .setUserId(userId);

        Integer serviceNumber = servicePersonnelPO.getServiceNumber();

        servicePersonnelPO.setServiceNumber(serviceNumber + 1);


        servicePersonnelDao.save(servicePersonnelPO);

        return ObjectUtil.isNotNull(serviceUserDao.save(serviceUser));
    }

    @Override
    public ServiceUser queryServiceUser(Integer userId) {

        return serviceUserDao.findByUserId(userId);
    }
}

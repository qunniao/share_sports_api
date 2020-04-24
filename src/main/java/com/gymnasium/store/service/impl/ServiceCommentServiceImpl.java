package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.SCException;
import com.gymnasium.store.Dao.ServiceCommentDao;
import com.gymnasium.store.Dao.ServicePersonnelDao;
import com.gymnasium.store.PO.ServiceCommentPO;
import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.VO.ServiceCommentVO;
import com.gymnasium.store.service.ServiceCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 16:39
 * @Description:
 */

@Service
public class ServiceCommentServiceImpl implements ServiceCommentService {

    @Autowired
    private ServiceCommentDao serviceCommentDao;

    @Autowired
    private ServicePersonnelDao servicePersonnelDao;

    @Override
    public Boolean insertServiceComment(ServiceCommentVO serviceCommentVO) {

        ServiceCommentPO serviceCommentPO = new ServiceCommentPO();

        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.findServicePersonnelPOById(serviceCommentVO.getServiceId());

        if (ObjectUtil.isNull(serviceCommentPO)){
            throw new SCException(ResultEnum.SERVICE_IS_NULL);
        }

        BeanUtil.copyProperties(serviceCommentVO, serviceCommentPO);

        serviceCommentPO.setCreateTime(new Date());
        serviceCommentPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        serviceCommentDao.save(serviceCommentPO);

        return true;
    }

    @Override
    public Integer queryLevelAvg(Integer serviceId) {

        return serviceCommentDao.queryLevel(serviceId);
    }

    @Override
    public List<ServiceCommentPO> findAllByServiceId(Integer serviceId) {

        return serviceCommentDao.findAllByServiceId(serviceId);
    }
}

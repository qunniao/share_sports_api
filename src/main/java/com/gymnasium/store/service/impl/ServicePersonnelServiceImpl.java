package com.gymnasium.store.service.impl;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.JgUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.SnowFlakeIdGenerator;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.login.Service.UserManageService;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserManagePO;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.VO.UserManageVO;
import com.gymnasium.store.Dao.ServicePersonnelDao;
import com.gymnasium.store.Dao.ServiceUserDao;
import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.PO.ServiceUser;
import com.gymnasium.store.VO.ServicePersonnelVO;
import com.gymnasium.store.service.ServicePersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:24
 * @Description:
 */
@Service
public class ServicePersonnelServiceImpl implements ServicePersonnelService {


    @Autowired
    private ServicePersonnelDao servicePersonnelDao;

    @Autowired
    private ServiceUserDao serviceUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserManageService userManageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertServicePersonnel(MultipartFile file, ServicePersonnelVO servicePersonnelVO) {

        ServicePersonnelPO servicePersonnelPO = new ServicePersonnelPO();

        if (file != null) {
            String url = FileUtils.uploadImage(file);
            servicePersonnelVO.setCover(url);
        }

        BeanUtil.copyPropertie(servicePersonnelVO,servicePersonnelPO);

        String workNumber = "S" + SnowFlakeIdGenerator.nextId();

        servicePersonnelPO.setCreateTime(new Date());

        servicePersonnelPO.setWorkNumber(workNumber);
        servicePersonnelPO.setStatus(1);

        String nameAndPassword = "Service_" + (System.currentTimeMillis());

        JgUtil.testCreateGroup(nameAndPassword, nameAndPassword);

        servicePersonnelPO.setJpushName(nameAndPassword);
        servicePersonnelPO.setJpushPwd(nameAndPassword);
        servicePersonnelPO.setServiceNumber(0);

        String username = servicePersonnelVO.getUsername();
        String password = servicePersonnelVO.getPassword();

        UserManageVO userManageVO = new UserManageVO(username, "客服人员", password, 7);

        userManageService.addUserManage(userManageVO);

        UserManagePO userManagePO = userManageService.queryUserByUserId(username);

        Integer uid = userManagePO.getUid();

        servicePersonnelPO.setManagerId(uid);

        return ObjectUtil.isNotNull(servicePersonnelDao.save(servicePersonnelPO));

    }

    @Override
    public Boolean updateServiceNumber(Integer serviceId, String jPushId, Integer type) {

        UserPO userPO = userDao.findByJiguangUsername(jPushId);

        if (ObjectUtil.isNull(userPO)){
            throw new SCException(ResultEnum.NULL_USER);
        }

        ServiceUser serviceUser = serviceUserDao.findByUserId(userPO.getUid());

        if (ObjectUtil.isNull(serviceUser)){
            throw new SCException(4455, "当前用户没有客服服务");
        }

        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.getOne(serviceId);

        if (ObjectUtil.isNull(servicePersonnelPO)){
            throw new SCException(400785, "客服不存在");
        }

        Integer serviceNumber = servicePersonnelPO.getServiceNumber();

        //开始服务
        if (type == 1){
            if (serviceUser.getServiceState() != 1){
                serviceUser.setServiceState(1);
            }
            serviceNumber += 1;
        }else {
            if (serviceUser.getServiceState() == 1){
                serviceUser.setServiceState(0);
            }

            if (serviceNumber <= 0){
                throw new SCException(456451, "当前没有服务，无法结束");
            }
            serviceNumber -= 1;
        }

        servicePersonnelPO.setServiceNumber(serviceNumber);
        servicePersonnelPO.setUpdateTime(new Date());

        return ObjectUtil.isNotNull(servicePersonnelDao.save(servicePersonnelPO));
    }

    @Override
    public ServicePersonnelPO queryServicePersonnel() {

        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.findByServiceNumber();

        if (ObjectUtil.isNull(servicePersonnelPO)){
            throw new SCException(ResultEnum.SERVICE_IS_BUSY);
        }

        return servicePersonnelPO;
    }

    @Override
    public ServicePersonnelPO queryCustomerService(String workNumber) {

        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.findByWorkNumber(workNumber);

        if (ObjectUtil.isNull(servicePersonnelPO)){
            throw new SCException(407819, "客服不存在");
        }

        return servicePersonnelPO;

    }
}

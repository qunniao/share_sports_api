package com.gymnasium.data.Service.ServiceImpl;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.JwtUtil;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.data.Dao.BuildingDao;
import com.gymnasium.data.Dao.SubjectDao;
import com.gymnasium.data.Dao.UserTypeDao;
import com.gymnasium.data.PO.BuildingPO;
import com.gymnasium.data.PO.SubjectPO;
import com.gymnasium.data.PO.UserTypePO;
import com.gymnasium.data.Service.DataService;
import com.gymnasium.stadium.Service.GymShopService;
import com.gymnasium.stadium.VO.GymShopVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 王志鹏
 * @title: DataServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 17:20
 */

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private UserTypeDao userTypeDao;

    @Autowired
    private GymShopService gymShopService;

    @Override
    public List<UserTypePO> findAll() {

        return userTypeDao.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSubject(SubjectPO subjectPO) {
        subjectPO.setStatus(SysConstant.STATUS_Enable_INT.getConstant());
        subjectDao.save(subjectPO);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addBuilding(BuildingPO buildingPO) {
        buildingPO.setStatus(SysConstant.STATUS_Enable_INT.getConstant());
        buildingDao.save(buildingPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserType(UserTypePO userTypePO) {


        if (userTypeDao.countByNameAndType(userTypePO.getName(), userTypePO.getType()) != 0) {
            throw new SCException(ResultEnum.NAME_ERROR);
        }
        userTypeDao.save(userTypePO);

        return true;
    }

    @Override
    public GymShopVO decryptQRCode(String sgin) {

        String gymShopId = JwtUtil.verify(sgin);

        if (gymShopId == null) {
            throw new SCException(ResultEnum.GYM_QRCode_ERROR);
        }
        GymShopVO gymShopVO = gymShopService.queryByGymShopId(gymShopId);
        if (gymShopVO == null) {
            throw new SCException(ResultEnum.GYM_NULL_ERROR);
        }

        return gymShopVO;
    }

    @Override
    public Result updateDataBuilding(BuildingPO buildingPO) {
        BuildingPO byBid = buildingDao.findByBid(buildingPO.getBid());
        if (buildingPO.getName() != null) byBid.setName(buildingPO.getName());
        if (buildingPO.getUrl() != null) byBid.setUrl(buildingPO.getUrl());
        if (buildingPO.getUrly() != null) byBid.setUrly(buildingPO.getUrly());
        buildingDao.save(byBid);
        return ResultUtil.success();
    }

    @Override
    public Result updateDataSubject(SubjectPO subjectPO) {
        SubjectPO one = subjectDao.getOne(subjectPO.getSid());
        if (subjectPO.getName() != null) {
            one.setName(subjectPO.getName());
        }
        if (subjectPO.getUrl() != null) {
            one.setUrl(subjectPO.getUrl());
        }
        if (subjectPO.getUrly() != null) {
            one.setUrly(subjectPO.getUrly());
        }

        subjectDao.save(one);
        return ResultUtil.success();
    }
}

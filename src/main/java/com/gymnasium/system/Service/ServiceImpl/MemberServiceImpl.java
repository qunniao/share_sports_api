package com.gymnasium.system.Service.ServiceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.card.Dao.ActivationRecordDao;
import com.gymnasium.store.Dao.AgentSchemeDao;
import com.gymnasium.store.Dao.ChannelSchemeDao;
import com.gymnasium.store.Dao.CommissionDao;
import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.system.Dao.SysMemberDao;
import com.gymnasium.system.Dao.SysMenberCityDao;
import com.gymnasium.system.PO.SysMemberPO;
import com.gymnasium.system.PO.SysMenberCityPO;
import com.gymnasium.system.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 王志鹏
 * @title: MemberServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 17:13
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private SysMenberCityDao sysMenberCityDao;

    @Autowired
    private SysMemberDao sysMemberDao;

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private AgentSchemeDao agentSchemeDao;

    @Autowired
    private ChannelSchemeDao channelSchemeDao;



    @Override
    public Boolean addSysMenberCity(SysMenberCityPO sysMenberCityPO) {

        sysMenberCityDao.save(sysMenberCityPO);
        return true;
    }

    @Override
    public SysMenberCityPO queryByCityNameLikeAndMid(String cityName, int mid) {

        cityName = "%"+cityName+"%";
        return sysMenberCityDao.queryByCityNameLikeAndMid(cityName, mid);
    }

    @Override
    public List<SysMemberPO> querySysMemberAll() {

        return sysMemberDao.findAll();
    }

    @Override
    public List<SysMenberCityPO> querySysMenberCityAll() {

        return sysMenberCityDao.findAll();
    }

    @Override
    public Boolean updateSysMenberCity(SysMenberCityPO sysMenberCityPO) {
        SysMenberCityPO sysMenberCityPOs = sysMenberCityDao.getOne(sysMenberCityPO.getId());

        if (sysMenberCityPO == null) {
            throw new SCException(ResultEnum.MenberCityPO_IS_NULL);
        }

        sysMenberCityPOs.setOnePrice(sysMenberCityPO.getOnePrice());
        sysMenberCityPOs.setTwoPrice(sysMenberCityPO.getTwoPrice());
        sysMenberCityPOs.setCityName(sysMenberCityPO.getCityName());
        sysMenberCityDao.save(sysMenberCityPOs);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertMember(MultipartFile file, SysMemberPO sysMemberPO) {

        if (ObjectUtil.isNull(sysMemberPO)){
            throw new SCException(ResultEnum.NO_DATA_EXIST, "sysMember");
        }

        if (ObjectUtil.isNotEmpty(file)){

            String image = FileUtils.uploadImage(file);
            sysMemberPO.setCover(image);
        }

        SysMemberPO save = sysMemberDao.save(sysMemberPO);

        return ObjectUtil.isNotEmpty(save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateMember(MultipartFile file, SysMemberPO sysMemberPO) {

        SysMemberPO sysMemberPO1 = sysMemberDao.findSysMemberPOById(sysMemberPO.getId());

        if (ObjectUtil.isNull(sysMemberPO1)){
            throw new SCException(ResultEnum.NO_DATA_EXIST, "sysMember");
        }


        if (ObjectUtil.isNotEmpty(file)){

            String image = FileUtils.uploadImage(file);
            sysMemberPO.setCover(image);
        }

        BeanUtil.copyPropertie(sysMemberPO, sysMemberPO1);

        SysMemberPO save = sysMemberDao.save(sysMemberPO1);

        return ObjectUtil.isNotEmpty(save);
    }

    @Override
    public Boolean deleteMember(Integer id) {

        Integer integer = sysMemberDao.deleteSysMemberPOById(id);

        return integer > 0;
    }

    @Override
    public List<SysMemberPO> querySysMember(Integer userId) {

        CommissionPO commissionPO = commissionDao.findByUserId(userId);

        if (ObjectUtil.isEmpty(commissionPO)){

            throw new SCException(40080, "用户佣金数据不存在");
        }

        // 查询二级佣金数据
        CommissionPO superiorPO = commissionDao.findByUserId(commissionPO.getSuperior());

        Sort sort = new Sort(Sort.Direction.ASC,"level");

        List<SysMemberPO> sysMemberList = sysMemberDao.findAll(sort);

        if (ObjectUtil.isEmpty(superiorPO)){
            return sysMemberList;
        }

        Integer role = superiorPO.getRole();

        if (role == 1 || role == 0){

            List<AgentSchemePO> agentSchemePOList = agentSchemeDao.findByTypeOrderByMemberLevelAsc(1);

            if (CollectionUtil.isEmpty(agentSchemePOList)){
                return sysMemberList;
            }

            for (int i = 0; i < agentSchemePOList.size(); i++) {

                AgentSchemePO agentSchemePO = agentSchemePOList.get(i);

                BigDecimal agentPrice = agentSchemePO.getAgentPrice();

                sysMemberList.get(i).setPrice(agentPrice);
            }
        }else if (role == 2 || role == 3){

            List<ChannelSchemePO> channelSchemePOS;

            if (role == 2){

                // 查询一级渠道
                channelSchemePOS = channelSchemeDao.findAllByTypeAndLevelOrderByMemberLevelAsc(1, 1);
            } else {

                // 查询二级渠道
                channelSchemePOS = channelSchemeDao.findAllByUserIdAndTypeAndLevelOrderByMemberLevelAsc(superiorPO.getSuperior(), 1,2);
            }

            if (CollectionUtil.isEmpty(channelSchemePOS)){
                return sysMemberList;
            }

            for (int i = 0; i < channelSchemePOS.size(); i++) {

                ChannelSchemePO channelSchemePO = channelSchemePOS.get(i);

                BigDecimal channelPrice = channelSchemePO.getChannelPrice();

                sysMemberList.get(i).setPrice(channelPrice);
            }
        }

        return sysMemberList;
    }
}

package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.Service.CodelogService;
import com.gymnasium.login.Service.UserManageService;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.VO.UserManageVO;
import com.gymnasium.store.Dao.AgentDao;
import com.gymnasium.store.Dao.CashFlowDao;
import com.gymnasium.store.Dao.ChannelDao;
import com.gymnasium.store.Dao.CommissionDao;
import com.gymnasium.store.PO.AgentPO;
import com.gymnasium.store.PO.ChannelPO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.VO.ChannelVO;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:51
 * @Description:
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private CommissionUtil commissionUtil;

    @Autowired
    private CodelogService codelogService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private CashFlowDao cashFlowDao;

    @Override
    public Boolean insertChannel(ChannelVO channelVO, String superiorId, String code) {

        Integer userId = channelVO.getUserId();

        if (StrUtil.isEmpty(channelVO.getPhone()) || StrUtil.isEmpty(code)){
            throw new SCException(40100, "手机号或验证码为空");
        }

        // 效验验证码是否正确
        if (!codelogService.validCode(channelVO.getPhone(), code)) {
            throw new SCException(ResultEnum.CODE_TIME_OUT);
        }

        ChannelPO channelPO1 = channelDao.findByUserId(userId);

        if (channelPO1 != null){
            throw new SCException(452,"渠道人已存在");
        }

        UserPO superiorPO = userDao.findByUserId(superiorId);

        if (ObjectUtil.isNull(superiorPO)){
            throw new SCException(453,"上级不存在" );
        }

        AgentPO agentPO = agentDao.findByUserId(userId);

        if (ObjectUtil.isNotNull(agentPO)){
            throw new SCException(455, "当前用户已经是代理");
        }

        ChannelPO channelPO = new ChannelPO();
        BeanUtil.copyPropertie(channelVO,channelPO);

        String agentCode = "C" + RandomUtil.randomString(7);

        channelPO.setCreateTime(new Date());
        channelPO.setChannelCode(agentCode);

        channelPO.setStatus(SysConstant.STATUS_SHOW.getConstant());
        channelPO.setOperationId(superiorPO.getUid());
        channelPO.setAuditStatus(1);

        ChannelPO save = channelDao.save(channelPO);

        if (save != null){

            CommissionPO commission = commissionDao.findByUserId(save.getOperationId());

            CommissionPO commissionPO =new CommissionPO();

            if (commission != null) {
                commissionPO.setSecondLevel(commission.getSuperior());
            }

            commissionUtil.addCommission(save.getUserId(), superiorPO.getUid(),channelVO.getLevel() + 1);
        }


        Integer level = save.getLevel();
        Integer role;
        String username;
        if (level == 1) {
            role = 5;
            username = "一级渠道" + userId;
        } else {
            role = 6;
            username = "二级渠道" + userId;
        }

        String userIdStr;
        String password;

        if (StrUtil.isBlank(channelVO.getUsername()) || StrUtil.isBlank(channelVO.getPassword())){
            userIdStr = "oyoc" + userId;
            password = "oyoc123456";
        }else {
            userIdStr = channelVO.getUsername();
            password = channelVO.getPassword();
        }

        UserManageVO userManageVO = new UserManageVO(userIdStr,username, password, role);

        return userManageService.addUserManage(userManageVO);
    }

    @Override
    public Page<ChannelPO> pageAllChannel(Paging page, ChannelVO channelVO) {

        Pageable pageable = PageRequest.of(page.getPageNum()-1, page.getPageSize());

        Page<ChannelPO> channelPO = channelDao.findAll(new Specification<ChannelPO>(){

            @Override
            public Predicate toPredicate(Root<ChannelPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class),1));

                if (GeneralUtils.notEmpty(channelVO.getTitle())){

                    list.add(criteriaBuilder.like(root.get("title"), "%" + channelVO.getTitle() + "%"));
                }
                if (GeneralUtils.notEmpty(channelVO.getTrueName())){

                    list.add(criteriaBuilder.like(root.get("trueName"), "%" + channelVO.getTrueName() + "%"));
                }

                if (GeneralUtils.notEmpty(channelVO.getPhone())){

                    list.add(criteriaBuilder.like(root.get("phone"),  channelVO.getPhone() + "%"));
                }

                if (GeneralUtils.notEmpty(channelVO.getLevel())){

                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), channelVO.getLevel()));
                }
                if (!StringUtils.isEmpty(channelVO.getOperationId())) {
                    list.add(criteriaBuilder.equal(root.get("operationId").as(Integer.class),
                            channelVO.getOperationId()));
                }

                if (!StringUtils.isEmpty(channelVO.getAuditStatus())){

                    list.add(criteriaBuilder.equal(root.get("auditStatus").as(Integer.class), channelVO.getAuditStatus()));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);

        return channelPO;
    }

    @Override
    public Boolean updateChannel(ChannelVO channelVO) {

        ChannelPO channel = channelDao.findChannelPOById(channelVO.getId());

        if (channel == null) {

            throw new SCException(451,"渠道人不存在");
        }

        BeanUtil.copyProperties(channelVO,channel);

        channel.setUpdateTime(new Date());

        channelDao.save(channel);

        return true;
    }

    @Override
    public Boolean auditChannel(Integer id, Integer auditStatus) {

        ChannelPO channelPO = channelDao.findChannelPOById(id);

        channelPO.setAuditStatus(auditStatus);
        channelPO.setUpdateTime(new Date());

        channelDao.save(channelPO);

        return true;
    }

    @Override
    public Boolean deleteChannel(Integer id) {

        ChannelPO channelPO = channelDao.findChannelPOById(id);

        if (channelPO == null) {
            throw new SCException(451,"渠道人不存在");
        }

        channelPO.setStatus(0);
        channelDao.save(channelPO);

        return true;
    }

    @Override
    public List<Map> queryChannelTeam(CommissionVO commissionVO, Paging page) {

        Integer status = commissionVO.getStatus();
        Integer userId = commissionVO.getUserId();

        List<CommissionPO> commissionPOList = new ArrayList<>();

        // 查询所有客户
        if (ObjectUtil.isNotNull(status)) {

            commissionPOList = commissionDao.findAllBySuperiorAndRoleAndStatus(userId, 3, status);
        }else {
            commissionPOList = commissionDao.findAllBySuperiorAndRole(userId, 3);
        }


        if (GeneralUtils.isEmpty(commissionPOList)) {
            return null;
        }

        // 客户id 集合
        List<Integer> userList = new ArrayList<>();


        // 遍历所有用户取出所有id
        for (CommissionPO commissionPO : commissionPOList) {

            userList.add(commissionPO.getUserId());
        }

        // todo: 渠道团队
        List<Map> maps = cashFlowDao.countAgentSales(userList, page.getPageNum() - 1, page.getPageSize());

        return maps;
    }

    @Override
    public Integer queryChannelLevel(Integer userId) {

        ChannelPO channelPO = channelDao.findByUserId(userId);

        if(ObjectUtil.isEmpty(channelPO)){
            throw new SCException(102000,"渠道人不存在");
        }

        return channelPO.getLevel();
    }
}

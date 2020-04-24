package com.gymnasium.store.service.impl;

import cn.hutool.core.collection.CollectionUtil;
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
import com.gymnasium.store.Dao.*;
import com.gymnasium.store.PO.*;
import com.gymnasium.store.VO.AgentVO;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.AgentService;
import com.gymnasium.store.service.ChannelSchemeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:46
 * @Description:
 */
@Service
@Component
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private CommissionUtil commissionUtil;

    @Autowired
    private CashFlowDao cashFlowDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodelogService codelogService;

    @Autowired
    private UserManageService userManageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertAgent(AgentVO agentVO, String superiorId, String code) {

        Integer userId = agentVO.getUserId();

        if (StrUtil.isEmpty(agentVO.getPhone()) || StrUtil.isEmpty(code)) {
            throw new SCException(40100, "手机号或验证码为空" );
        }

        // 效验验证码是否正确
        if (!codelogService.validCode(agentVO.getPhone(), code)) {
            throw new SCException(ResultEnum.CODE_TIME_OUT);
        }

        AgentPO agentPO1 = agentDao.findByUserId(userId);

        if (agentPO1 != null) {
            throw new SCException(452, "代理人已存在" );
        }

        ChannelPO channelPO = channelDao.findByUserId(userId);

        if (ObjectUtil.isNotNull(channelPO)) {
            throw new SCException(455, "当前用户已经是渠道" );
        }

        UserPO superiorPO = userDao.findByUserId(superiorId);

        if (ObjectUtil.isNull(superiorPO)) {
            throw new SCException(453, "上级不存在" );
        }

        AgentPO agentPO = new AgentPO();

        BeanUtil.copyProperties(agentVO, agentPO);

        String agentCode = "A" + RandomUtil.randomString(7);

        agentPO.setAgentCode(agentCode);
        agentPO.setCreateTime(new Date());
        agentPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        agentPO.setAuditStatus(1);
        agentPO.setOperationId(superiorPO.getUid());

        AgentPO save = agentDao.save(agentPO);

        if (save != null) {

            commissionUtil.addCommission(save.getUserId(), superiorPO.getUid(), 1);
        }

        Integer level = save.getLevel();
        Integer role;
        String username;
        if (level == 1) {
            role = 3;
            username = "一级代理" + userId;
        } else {
            role = 4;
            username = "二级代理" + userId;
        }

        String userIdStr;
        String password;

        if (StrUtil.isBlank(agentVO.getUsername()) || StrUtil.isBlank(agentVO.getPassword())){
            userIdStr = "oyoc" + userId;
            password = "oyoc123456";
        }else {
            userIdStr = agentVO.getUsername();
            password = agentVO.getPassword();
        }

        UserManageVO userManageVO = new UserManageVO(userIdStr, username, password, role);

        return userManageService.addUserManage(userManageVO);
    }

    @Override
    public Page<AgentPO> pageAllAgent(Paging page, AgentVO agentVO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<AgentPO> pageAgent = agentDao.findAll(new Specification<AgentPO>() {

            @Override
            public Predicate toPredicate(Root<AgentPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (GeneralUtils.notEmpty(agentVO.getLevel())) {

                    list.add(criteriaBuilder.equal(root.get("level" ).as(Integer.class), agentVO.getLevel()));
                }
                if (GeneralUtils.notEmpty(agentVO.getTrueName())) {

                    list.add(criteriaBuilder.like(root.get("trueName" ), "%" + agentVO.getTrueName() + "%" ));
                }
                if (GeneralUtils.notEmpty(agentVO.getTitle())) {

                    list.add(criteriaBuilder.like(root.get("title" ), "%" + agentVO.getTitle() + "%" ));
                }
                if (GeneralUtils.notEmpty(agentVO.getPhone())) {

                    list.add(criteriaBuilder.like(root.get("phone" ), agentVO.getPhone() + "%" ));
                }

                if (!StringUtils.isEmpty(agentVO.getOperationId())) {
                    list.add(criteriaBuilder.equal(root.get("operationId" ).as(Integer.class),
                            agentVO.getOperationId()));
                }
                if (GeneralUtils.notEmpty(agentVO.getAuditStatus())) {

                    list.add(criteriaBuilder.equal(root.get("auditStatus" ).as(Integer.class), agentVO.getAuditStatus()));
                }

                list.add(criteriaBuilder.equal(root.get("status" ).as(Integer.class), SysConstant.STATUS_SHOW.getConstant()));

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return pageAgent;
    }

    @Override
    public Boolean updateAgent(AgentVO agentVO) {

        AgentPO agent = agentDao.findAgentPOById(agentVO.getId());

        if (agent == null) {

            throw new SCException(451, "代理人不存在" );
        }

        BeanUtil.copyProperties(agentVO, agent);

        agent.setUpdateTime(new Date());

        agentDao.save(agent);

        return true;
    }

    @Override
    public Boolean auditAgent(Integer id, Integer status) {

        AgentPO agentPO = agentDao.findAgentPOById(id);

        agentPO.setAuditStatus(status);
        agentPO.setUpdateTime(new Date());

        agentDao.save(agentPO);

        return true;
    }

    @Override
    public Boolean deleteAgent(Integer id) {

        AgentPO agentPO = agentDao.findAgentPOById(id);

        if (agentPO == null) {
            throw new SCException(451, "代理人不存在" );
        }

        agentPO.setStatus(0);
        agentDao.save(agentPO);

        return true;
    }

    @Override
    public List<Map> queryClientTeam(Integer userId, Paging page) {

        // 查询所有客户
        List<CommissionPO> commissionPOList = commissionDao.findAllBySuperiorAndRole(userId, 0);

        if (GeneralUtils.isEmpty(commissionPOList)) {
            return null;
        }

        // 客户id 集合
        List<Integer> userList = new ArrayList<>();


        // 遍历所有用户取出所有id
        for (CommissionPO commissionPO : commissionPOList) {

            userList.add(commissionPO.getUserId());
        }

        // 分页查出所有的用户
        List<Map> maps = cashFlowDao.countUserSales(userList, page.getPageNum() - 1, page.getPageSize());

        return maps;
    }

    @Override
    public List<Map> queryAgentTeam(Integer userId, Integer status, Paging page) {

        List<CommissionPO> commissionPOList = new ArrayList<>();

        // 查询所有客户
        if (ObjectUtil.isNotNull(status)) {

            commissionPOList = commissionDao.findAllBySuperiorAndRoleAndStatus(userId, 1, status);
        }else {
            commissionPOList = commissionDao.findAllBySuperiorAndRole(userId, 1);
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

        // 分页查出所有的用户
        List<Map> maps = cashFlowDao.countAgentSales(userList, page.getPageNum() - 1, page.getPageSize());

        return maps;
    }

    @Override
    public Map<String, Object> queryDetails(Integer userId) {

        Map<String, Object> map = new HashMap<>(16);

        CommissionPO commissionPO = commissionDao.findByUserId(userId);

        if (ObjectUtil.isNull(commissionPO)) {
            throw new SCException(40011, "用户佣金数据不存在" );
        }

        UserPO userPO = userDao.findByUid(userId);

        //判断用户是否存在
        if (ObjectUtil.isNull(userPO)) {
            throw new SCException(ResultEnum.USER_REPEAT);
        }

        map.put("name", userPO.getUserName());
        map.put("nikeName", userPO.getUserNike());
        map.put("avatarUrl", userPO.getAvatarUrl());

        // 用户身份
        Integer role = commissionPO.getRole();

        if (role == 1) {

            AgentPO agentPO = agentDao.findByUserId(userId);

            // 代理编码
            if (ObjectUtils.anyNotNull(agentPO)) {
                map.put("code", agentPO.getAgentCode());
            }
        }

        if (role == 2 || role == 3) {

            ChannelPO channelPO = channelDao.findByUserId(userId);
            // 渠道编码
            if (ObjectUtil.isNotNull(channelPO)) {
                map.put("code", channelPO.getChannelCode());
            }
        }

        // 代理发展人
        UserPO userPO1 = userDao.findByUid(commissionPO.getSuperior());

        if (ObjectUtil.isNull(userPO1)) {

            map.put("developer_name", null);
        } else {
            map.put("developer_name", userPO1.getUserNike());
        }

        // 已提现佣金
        map.put("cashed", commissionPO.getCashed());
        // 可提现佣金
        map.put("canCarry", commissionPO.getCanCarry());

        Integer teamSize = commissionDao.countByStatusAndSuperiorOrSecondLevel(1, userId, userId);

        map.put("teamSize", teamSize);

        return map;
    }

    @Override
    public CommissionVO queryUserInfo(Integer userId) {

        AgentPO agentPO = agentDao.findByUserId(userId);

        if (ObjectUtil.isEmpty(agentPO)) {
            throw new SCException(40010, "代理不存在" );
        }

        CommissionVO commissionVO = new CommissionVO();

        CommissionPO commissionPO = commissionDao.findByUserId(userId);

        if (ObjectUtil.isEmpty(commissionPO)) {
            throw new SCException(40011, "佣金数据不存在" );
        }

        Integer role = commissionPO.getRole();

        BigDecimal clientCommission = commissionUtil.getClientCommission(userId);

        // 代理佣金
        if (role == 1) {

            BigDecimal agentCommission = commissionUtil.sumCommission(userId, role);
            commissionVO.setAgentCommission(agentCommission);
        }
        //二级渠道佣金
        if (role == 2) {
            BigDecimal channelCommission = commissionUtil.sumCommission(userId, role);

            commissionVO.setChannelCommission(channelCommission);
        }

        if (ObjectUtil.isNotEmpty(commissionPO)) {

            BeanUtil.copyProperties(commissionPO, commissionVO);
        }

        commissionVO.setClientCommission(clientCommission);

        return commissionVO;
    }

    @Override
    public Integer countOrderNumber(Integer userId) {

        List<Integer> teamUserId = getTeamUserId(userId);

        // 统计所有订单
        Integer orderNumber = shopOrderDao.countShopOrderPOByUserIdIn(teamUserId);

        return orderNumber;
    }

    @Override
    public Integer countTeamSize(Integer userId) {

        List<Integer> teamUserId = getTeamUserId(userId);

        return teamUserId.size();
    }

    @Override
    public Map<String, Object> countAgentTeam(Integer userId) {

        return countUserTeamInfo(userId, 1);
    }

    @Override
    public Map<String, Object> countClientTeam(Integer userId) {


        return countUserTeamInfo(userId, 0);
    }

    private Page<UserPO> getUser(List<Integer> userList, Paging page) {

        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<UserPO> userPOList = userDao.findAll(new Specification<UserPO>() {
            @Override
            public Predicate toPredicate(Root<UserPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                Expression<String> exp = root.get("uid" );

                list.add(exp.in(userList));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return userPOList;
    }

    private List<Integer> getTeamUserId(Integer userId) {

        // 查询用户身份
        Integer role = commissionDao.queryRole(userId);

        // 查询客户
        List<Integer> clientList = commissionDao.queryCustomerTeam(userId, 0, 1);

        System.out.println("clientList: " + clientList);

        if (ObjectUtil.isEmpty(role)) {
            throw new SCException(400701, "用户不存在或身份为空" );
        }

        if (role == 1) {

            // 查询代理团队 用户身份为 0 or 1 的
            List<Integer> list = commissionDao.queryAgentTeam(userId, 0, 1);

            System.err.println("list: " + list);

            for (Integer userIds : list) {

                clientList.add(userIds);
            }
        }
        // 如果是二级渠道，
        if (role == 2) {
            // 查询二级团队id
            List<Integer> channelList = commissionDao.queryTeam(3, userId);

            System.err.println("channelList: " + channelList);

            for (Integer userIds : channelList) {

                clientList.add(userIds);
            }
        }

        System.err.println("clientList: " + clientList);

        return clientList;
    }

    private Map<String, Object> countUserTeamInfo(Integer userId, Integer role) {

        Map<String, Object> map = new HashMap<>(16);

        // 根据 superior = userId 和 role = 1 查询代理团队
        List<Integer> list = commissionDao.queryUser(role, userId);

        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        // 根据 superior = userId 和 role = 1 查询代理团队 查询代理订单
        List<Integer> orderList = commissionDao.queryOrder(list);

        // 从佣金流水表根据订单id 统计总佣金

        BigDecimal totalCommission = new BigDecimal("0" );

        if (CollectionUtil.isNotEmpty(orderList)) {

            if (role == 1) {
                totalCommission = cashFlowDao.countAgentCommission(orderList);
            }

            if (role == 2) {
                totalCommission = cashFlowDao.countClientCommission(orderList);
            }
        }

        // 根据订单id集合，订单状态为 4 统计总销售额

        BigDecimal sales = new BigDecimal("0" );

        if (CollectionUtil.isNotEmpty(orderList)) {
            sales = shopOrderDao.countOrderSales(orderList);
        }

        map.put("teamSize", list.size());
        map.put("totalCommission", totalCommission);
        map.put("sales", sales);

        return map;
    }
}

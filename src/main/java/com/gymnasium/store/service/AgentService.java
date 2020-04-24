package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.PO.AgentPO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.VO.AgentVO;
import com.gymnasium.store.VO.CommissionVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:43
 * @Description:
 */
public interface AgentService {

    /**
     * 添加代理
     *
     * @param agentVO
     * @param superiorId
     * @param code
     * @return
     */
    Boolean insertAgent(AgentVO agentVO, String superiorId, String code);

    /**
     * 分页查询全部代理
     *
     * @param page
     * @param agentVO
     * @return
     */
    Page<AgentPO> pageAllAgent(Paging page, AgentVO agentVO);

    /**
     * 更新代理
     *
     * @param agentVO
     * @return
     */
    Boolean updateAgent(AgentVO agentVO);

    /**
     * 编辑代理
     *
     * @param id
     * @param auditStatus
     * @return
     */
    Boolean auditAgent(Integer id, Integer auditStatus);

    /**
     * 根据id 删除代理
     *
     * @param id
     * @return
     */
    Boolean deleteAgent(Integer id);

    /**
     * 查询客户团队用户信息， 销售额，订单数， 总佣金
     *
     * @param userId
     * @param page
     * @return
     */
    List<Map> queryClientTeam(Integer userId, Paging page);

    /**
     * 查询代理团队
     *
     * @param userId
     * @param status
     * @param page
     * @return
     */
    List<Map> queryAgentTeam(Integer userId, Integer status, Paging page);

    /**
     * 查询代理人详情
     *
     * @param userId
     * @return
     */
    Map<String, Object> queryDetails(Integer userId);

    /**
     * 用户佣金管理
     *
     * @param userId
     * @return
     */
    CommissionVO queryUserInfo(Integer userId);

    /**
     * 统计订单总数接口
     *
     * @param userId
     * @return
     */
    Integer countOrderNumber(Integer userId);

    /**
     * 统计我的团队总人数
     *
     * @param userId
     * @return
     */
    Integer countTeamSize(Integer userId);

    /**
     * 统计代理团队
     *
     * @param userId
     * @return
     */
    Map<String, Object> countAgentTeam(Integer userId);

    /**
     * 统计客户团队
     *
     * @param userId
     * @return
     */
    Map<String, Object> countClientTeam(Integer userId);
}

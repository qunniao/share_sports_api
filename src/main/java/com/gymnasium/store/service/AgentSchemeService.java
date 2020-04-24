package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.VO.AgentSchemeVO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:13
 * @Description:
 */
public interface AgentSchemeService{

    /**
     * 编辑代理方案
     * @param agentSchemeVO
     * @return
     */
    AgentSchemeVO editAgentScheme(AgentSchemeVO agentSchemeVO);

    /**
     * 后台编辑方案
     * @param agentSchemeVO
     * @return
     */
    Boolean editScheme(AgentSchemeVO agentSchemeVO);

    /**
     * 更新代理方案
     * @param agentSchemeVO
     * @return
     */
    Boolean updateAgentScheme(AgentSchemeVO agentSchemeVO);

    /**
     * 分页查询代理方案
     * @param page
     * @param agentSchemeVO
     * @return
     */
    Page<AgentSchemePO> pageAgentScheme(Paging page, AgentSchemeVO agentSchemeVO);

    /**
     * 查询代理方案
     * @param name
     * @param type
     * @return
     */
    List<AgentSchemePO> queryAgentScheme(String name, Integer type);


    /**
     * 根据产品id查询代理方案
     *
     * @param productId 产品id
     * @return AgentSchemeVO
     */
    AgentSchemeVO queryByProductId(Integer productId);
}

package com.gymnasium.store.service.impl;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.AgentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/3 12:47
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AgentServiceImplTest {

    @Autowired
    private AgentService agentService;

    @Test
    public void queryClientTeam() {

        Paging paging = new Paging();
        paging.setPageNum(1);
        paging.setPageSize(10);

        agentService.queryClientTeam(1, paging);
    }

    @Test
    public void queryAgentTeam() {
    }

    @Test
    public void queryUserInfo() {

        CommissionVO commissionVO = agentService.queryUserInfo(1);
        System.out.println(commissionVO);

    }
}
package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.AgentSchemePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.swing.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 边书恒
 * @Title: AgentSchemeDaoTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/722
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AgentSchemeDaoTest {

    @Autowired
    private AgentSchemeDao agentSchemeDao;

    @Test
    public void queryAgentScheme() {

        List<AgentSchemePO> agentSchemePOS = agentSchemeDao.queryAgentScheme(" 新希望", 2);

        Assert.notEmpty(agentSchemePOS,"agentSchemePOS为空");

        System.out.println(agentSchemePOS);
    }
}
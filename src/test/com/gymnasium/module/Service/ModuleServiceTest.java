package com.gymnasium.module.Service;

import com.gymnasium.module.Dao.ModuleDao;
import com.gymnasium.module.PO.ModulePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 边书恒
 * @Title: ModuleServiceTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/13 9:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleServiceTest {

    @Autowired
    private ModuleDao moduleDao;

    @Test
    public void queryAll() {

        List<ModulePO> moduleList = moduleDao.findAll();

        System.out.print(moduleList);
    }
}
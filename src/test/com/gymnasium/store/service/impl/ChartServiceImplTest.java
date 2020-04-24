package com.gymnasium.store.service.impl;

import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.store.service.ChartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/14 16:19
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartServiceImplTest {

    @Autowired
    private ChartService chartService;

    @Test
    public void pagePartnerRecord() {

        Paging paging = new Paging();

        Page<PartnerRecordPO> partnerRecordPOS = chartService.pagePartnerRecord("sh_001", "bbb", paging);

        System.out.println(partnerRecordPOS);

        System.out.println(partnerRecordPOS.getContent());

        System.out.println(partnerRecordPOS.getTotalElements());

    }
}
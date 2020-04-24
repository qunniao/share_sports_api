package com.gymnasium.record.Service;

import com.gymnasium.record.PO.CollectPO;
import com.gymnasium.record.VO.UserCollectVO;


/**
 * @author 王志鹏
 * @title: RecordService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/20 14:16
 */

public interface RecordService {

    boolean addCollectPO(CollectPO collectPO);

    UserCollectVO queryCollectAll(CollectPO collectPO);
}

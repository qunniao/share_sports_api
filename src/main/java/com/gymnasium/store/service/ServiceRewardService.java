package com.gymnasium.store.service;

import com.gymnasium.store.VO.ServiceCommentVO;
import com.gymnasium.store.VO.ServiceRewardVO;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:46
 * @Description:
 */
public interface ServiceRewardService {

  Boolean insertServiceReward(ServiceRewardVO serviceRewardVO);

  Integer countByServiceId(Integer id);
}

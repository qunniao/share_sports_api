package com.gymnasium.store.service;

import com.gymnasium.store.PO.ServiceCommentPO;
import com.gymnasium.store.VO.ServiceCommentVO;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:46
 * @Description:
 */
public interface ServiceCommentService {

  Boolean  insertServiceComment(ServiceCommentVO serviceCommentVO);

  Integer queryLevelAvg(Integer serviceId);

  List<ServiceCommentPO> findAllByServiceId(Integer serviceId);

}

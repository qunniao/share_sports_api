package com.gymnasium.store.service;

import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.VO.ServicePersonnelVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 14:20
 * @Description:
 */
public interface ServicePersonnelService {

 Boolean insertServicePersonnel(MultipartFile file, ServicePersonnelVO servicePersonnelVO);

 /**
  * 更新 客服服务人数
  * @param serviceId
  * @param type
  * @return
  */
 Boolean updateServiceNumber(Integer serviceId, String jPushId, Integer type);

 ServicePersonnelPO queryServicePersonnel();

 ServicePersonnelPO queryCustomerService(String workNumber);

}

package com.gymnasium.store.service;

import com.gymnasium.store.PO.ServiceUser;

/**
 * @author 边书恒
 * @Title: ServiceUserService
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/19 14:52
 */
public interface ServiceUserService {

    Boolean addServiceUser(Integer userId);

    ServiceUser queryServiceUser(Integer userId);
}

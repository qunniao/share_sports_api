package com.gymnasium.personnel.Service;

import com.gymnasium.personnel.PO.RolePermission;
import com.gymnasium.personnel.VO.RolePermissionVO;

/**
 * @author 边书恒
 * @Title: RolePermissionService
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 16:25
 */
public interface RolePermissionService {

    /**
     * 添加角色和权限
     *
     * @param rolePermissionVO
     * @return boolean
     */
    Boolean addRolePermission(RolePermissionVO rolePermissionVO);

    /**
     * 删除角色和权限
     *
     * @param id
     * @return boolean
     */
    Boolean delRolePermission(Integer id);
}

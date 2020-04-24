package com.gymnasium.personnel.Service;

import com.gymnasium.personnel.PO.Permission;
import com.gymnasium.personnel.VO.PermissionVO;

import java.util.List;

/**
 * @author 边书恒
 * @Title: PermissionService
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 15:27
 */
public interface PermissionService {

    /**
     * 添加权限
     *
     * @param permissionVO
     * @return boolean
     */
    Boolean addPermission(PermissionVO permissionVO);

    /**
     * 更新权限
     *
     * @param permissionVO
     * @return boolean
     */
    Boolean updatePermission(PermissionVO permissionVO);

    /**
     * 查询权限
     *
     * @param permissionVO
     * @return boolean
     */
    List<Permission> selectPermission(PermissionVO permissionVO);

    /**
     * 删除权限
     *
     * @param id
     * @return boolean
     */
    Boolean delPermission(Integer id);
}

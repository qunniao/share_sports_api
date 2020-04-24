package com.gymnasium.personnel.Service.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.personnel.Dao.RolePermissionDao;
import com.gymnasium.personnel.PO.RolePermission;
import com.gymnasium.personnel.Service.RolePermissionService;
import com.gymnasium.personnel.VO.RolePermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 边书恒
 * @Title: RolePermissionServiceImpl
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 16:27
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Boolean addRolePermission(RolePermissionVO rolePermissionVO) {

        RolePermission rolePermission = new RolePermission();

        BeanUtil.copyProperties(rolePermissionVO, rolePermission);

        return(ObjectUtil.isNotNull(rolePermissionDao.save(rolePermission)));
    }

    @Override
    public Boolean delRolePermission(Integer id) {

        boolean exists = rolePermissionDao.existsById(id);

        if (!exists){
            throw new SCException(7451, "权限数据不存在");
        }

        rolePermissionDao.deleteById(id);

        return true;
    }
}

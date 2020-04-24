package com.gymnasium.personnel.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.personnel.Dao.PermissionDao;
import com.gymnasium.personnel.PO.Permission;
import com.gymnasium.personnel.Service.PermissionService;
import com.gymnasium.personnel.VO.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 边书恒
 * @Title: PermissionServiceImpl
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 15:53
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Boolean addPermission(PermissionVO permissionVO) {

        Permission permission = new Permission();

        BeanUtil.copyProperties(permissionVO, permission);

        return(ObjectUtil.isNotNull(permissionDao.save(permission)));
    }

    @Override
    public Boolean updatePermission(PermissionVO permissionVO) {

        Permission permission1 = permissionDao.getOne(permissionVO.getPid());

        if (ObjectUtil.isNull(permission1)){
            throw new SCException(48516,"权限数据不存在");
        }

        BeanUtil.copyProperties(permissionVO, permission1);

        return ObjectUtil.isNotNull( permissionDao.save(permission1));
    }

    @Override
    public List<Permission> selectPermission(PermissionVO permissionVO) {

        List<Permission> permissionList = permissionDao.findAll((Specification<Permission>) (root, query, criteriaBuilder) -> {

            List<Predicate> list = new LinkedList<>();

            if (ObjectUtil.isNotNull(permissionVO.getMid())) {
                list.add(criteriaBuilder.equal(root.get("mid").as(Integer.class), permissionVO.getMid()));
            }

            if (ObjectUtil.isNotNull(permissionVO.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + permissionVO.getName() + "%"));
            }

            Predicate[] predicates = new Predicate[list.size()];

            return criteriaBuilder.and(list.toArray(predicates));
        });

        return permissionList;
    }

    @Override
    public Boolean delPermission(Integer id) {

        boolean exists = permissionDao.existsById(id);

        if (!exists){
            throw new SCException(7451, "权限数据不存在");
        }

        permissionDao.deleteById(id);

        return true;
    }
}

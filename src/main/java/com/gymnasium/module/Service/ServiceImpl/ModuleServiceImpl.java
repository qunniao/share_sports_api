package com.gymnasium.module.Service.ServiceImpl;

import com.google.common.base.Joiner;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.personnel.Dao.RoleDao;
import com.gymnasium.personnel.Dao.UserManageDao;
import com.gymnasium.module.Dao.ModuleDao;
import com.gymnasium.module.Dao.ModuleRoleDao;
import com.gymnasium.module.PO.ModulePO;
import com.gymnasium.module.PO.ModuleRolePO;
import com.gymnasium.module.Service.ModuleService;
import com.gymnasium.personnel.PO.Role;
import com.gymnasium.personnel.PO.UserManagePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王志鹏
 * @title: moduleServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 10:04
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private UserManageDao userManageDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModuleRoleDao moduleRoleDao;

    @Override
    public List<ModulePO> findAll() {

        return moduleDao.queryAllBySrot();
    }

    @Override
    public List<ModulePO> findMoudelByUsreId(String userId) {

        UserManagePO userManage = userManageDao.queryByUserId(userId);

        List<Integer> roleIdList = new ArrayList<>();

        for (Role role : userManage.getRoleList()) {

            roleIdList.add(role.getRid());
        }
        String roleIdsStr = Joiner.on(",").join(roleIdList);

        List<ModulePO> modulePOS = moduleDao.queryModulePOByRid(roleIdsStr);

        return modulePOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMouleService(ModulePO modulePO) {
        moduleDao.save(modulePO);
        return false;
    }

    /**
     * 三张表:sys_module sys_module_role role
     *
     * @param rid
     * @param mid
     * @return
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateModuleByRid(Integer rid, List<Integer> mid) {
        //判断rid是否存在
        Role allByRid = roleDao.findAllByRid(rid);
        if (allByRid != null) {
            boolean flag = true;
            //判断每一个mid是否存在
            for (Integer s : mid) {
                ModulePO byMid = moduleDao.findByMid(s);
                if (byMid == null) flag = false;
            }
            if (flag) {
                moduleRoleDao.deleteAllByRid(rid);
                for (Integer s : mid) {
                    ModuleRolePO role = new ModuleRolePO();
                    role.setRid(rid);
                    role.setMid(s);
                    moduleRoleDao.save(role);
                }
                return ResultUtil.success();
            }
        }

        return ResultUtil.error(444, "数据库没有这个数据");
    }

    @Override
    public Result addModule(ModulePO modulePO) {

        moduleDao.save(modulePO);
        return ResultUtil.success();
    }

    @Override
    public Result updateModule(ModulePO modulePO) {

        moduleDao.save(modulePO);
        return ResultUtil.success();
    }

    @Override
    public Result deleteModule(int mid) {
        moduleDao.deleteById(mid);
        return ResultUtil.success();
    }

    @Override
    public List<ModulePO> queryAll() {

        return moduleDao.findAll();
    }
}

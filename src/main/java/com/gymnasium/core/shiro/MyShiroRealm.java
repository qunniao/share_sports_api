package com.gymnasium.core.shiro;

import com.gymnasium.login.Service.UserManageService;
import com.gymnasium.personnel.PO.Permission;
import com.gymnasium.personnel.PO.Role;
import com.gymnasium.personnel.PO.UserManagePO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserManageService userManageService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String userId = (String) principals.getPrimaryPrincipal();

        UserManagePO user = userManageService.queryUserByUserId(userId);

        for (Role role : user.getRoleList()) {
            authorizationInfo.addRole(role.getRoleName());
            for (Permission p : role.getPermissionList()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userId = (String) token.getPrincipal();
        //实际项目中，这里可以根据实际情况做缓存，如果不做，ShirogetAuthenticationInfo自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserManagePO user = userManageService.queryUserByUserId(userId);

        if (user == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserId(), //用户名
                user.getPassWord(), //密码
                ByteSource.Util.bytes(user.getSalt()),
                this.getName()  //realm name
        );

        return authenticationInfo;
    }

}
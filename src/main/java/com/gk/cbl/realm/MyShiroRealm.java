package com.gk.cbl.realm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.service.Org_userService;
import com.gk.cbl.service.Org_user_roleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    Org_userService userService;

    @Autowired
    Org_user_roleService userRoleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        Org_user user = userService.selectOne(new EntityWrapper<Org_user>().eq("userName", username));
        //System.out.println(user);
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user,user.getUserPassword(),"UserRealm");
        return authcInfo;

    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Org_user user = (Org_user) principals.getPrimaryPrincipal();
        String username = user.getUserName();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //authorizationInfo.
        authorizationInfo.setRoles(userRoleService.findByUsername(username));
        //System.out.println(userService.findPermissions(username));
        return authorizationInfo;
    }
}

package com.qf.myrealm;

import com.qf.bean.Users;
import com.qf.service.UsersService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 2019/6/25
 * Administrator
 * springTest
 * 面向对象面向君  不负代码不负卿
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    private UsersService usersService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证。。。。。。。如果认证成功，则自动跳转页面");
        //1.得到用户的姓名
        String username = (String)authenticationToken.getPrincipal();
        //2.调用service层方法进行查询
        Users user = usersService.login(username);
        if(user==null)
            return   null;
        //3.判断密码
        // char[] pass=(char[]) authenticationToken.getCredentials();
        //SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(username,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),"realma");
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),"realma");

        return info;
    }
}

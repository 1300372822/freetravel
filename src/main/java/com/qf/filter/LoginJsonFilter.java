package com.qf.filter;

import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description:
 * @Createtime: 2019-07-30 14:15
 * 面向对象面向君，不负代码不负卿
 */
public class LoginJsonFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return super.onAccessDenied(request, response);
    }
}

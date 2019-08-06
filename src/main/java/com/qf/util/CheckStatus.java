package com.qf.util;

import com.qf.bean.DataView;
import com.qf.bean.Users;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:判断增删改后状态
 * @Createtime: 2019-07-31 16:05
 * 面向对象面向君，不负代码不负卿
 */
public class CheckStatus {
    public static void check(int k, Users user, HttpSession session, DataView dataView){
        if (k>0){
            session.setAttribute("user",user);
            dataView.setCode(0);
            dataView.setMsg("成功");
            List list = new ArrayList();
            list.add(user);
            dataView.setData(list);
        }else {
            dataView.setCode(1);
            dataView.setMsg("失败");
        }
    }
}

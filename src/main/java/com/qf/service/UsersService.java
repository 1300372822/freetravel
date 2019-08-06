package com.qf.service;

import com.qf.bean.Users;

import java.util.Map;

public interface UsersService {

    //登录
    Users login(String username);

    //更新头像
    int updateIcon(Users users);

    //查询我的货架
    Map findItemList(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    //主键查询
    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}

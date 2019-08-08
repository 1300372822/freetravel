package com.qf.service;

import com.qf.bean.Users;

import java.util.Map;

public interface UsersService {

    //通过手机号查询是否已经存在该用户(存在返回id，不存在返回0)
    int isUserExistByPhone(String phone);

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

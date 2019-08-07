package com.qf.dao;

import com.qf.bean.Users;

import java.util.List;

public interface UsersMapper {

    //通过手机号查询是否已经存在该用户
    int isUserExistByPhone(String phone);

    //登录
    Users login(String username);

    //通过商品id查询发布或转发人
    Users selectByIid(Integer iid);

    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}
package com.qf.service.impl;

import com.qf.bean.Users;
import com.qf.dao.ItemMapper;
import com.qf.dao.UsersMapper;
import com.qf.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:个人信息
 * @Createtime: 2019-07-30 09:39
 * 面向对象面向君，不负代码不负卿
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private ItemMapper itemMapper;

    //登录
    @Override
    public Users login(String username) {
        return usersMapper.login(username);
    }

    //更新头像
    @Override
    @Transactional
    public int updateIcon(Users users) {
        return usersMapper.updateByPrimaryKeySelective(users);
    }

    //我的货架
    @Override
    public Map findItemList(Integer id) {
        Map map = new HashMap();
        //查询发布数
        int publishcount = itemMapper.findPublishCount(id);
        //查询转发数
        int forwardCount = itemMapper.findForwardCount(id);
        //查询已售数
        int saleCount = itemMapper.findSaleCount(id);
        map.put("publishcount", publishcount);
        map.put("forwardCount", forwardCount);
        map.put("saleCount", saleCount);
        return map;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Users record) {
        return 0;
    }

    @Override
    public int insertSelective(Users record) {
        return 0;
    }

    //主键查询
    @Override
    public Users selectByPrimaryKey(Integer id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Users record) {
        return usersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Users record) {
        return 0;
    }
}

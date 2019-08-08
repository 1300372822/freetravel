package com.qf.service;

import com.github.pagehelper.PageInfo;
import com.qf.bean.Images;
import com.qf.bean.Item;

import java.util.List;

public interface ItemService {

    //查询热门
    PageInfo<Item> findhot(int pageindex, int pagesize);
    //查询国内
    PageInfo<Item> finddomestic(int pageindex, int pagesize);
    //查询周边
    PageInfo<Item> findnearby(String currentlocation,int pageindex, int pagesize);

    //点赞前三的商品
    List<Images> top3();

    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}
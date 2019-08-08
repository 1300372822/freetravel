package com.qf.dao;

import com.qf.bean.Item;

import java.util.List;
import java.util.Map;

public interface ItemMapper {

    //查询个人发布数
    int findPublishCount(Integer id);

    //查询个人转发数
    int findForwardCount(Integer id);

    //查询个人售出数
    int findSaleCount(Integer id);

    //查询热门
    List<Item> findhot();

    //查询国内
    List<Item> finddomestic();

    //查询国内
    List<Item> findnearby(Map map);

    //点赞前三的商品
    List<Integer> top3();

    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}
package com.qf.dao;

import com.qf.bean.Images;

import java.util.List;

public interface ImagesMapper {
    //通过商品id查询商品照片
    List<Images> selectByIid(Integer iid);

    int deleteByPrimaryKey(Integer id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}
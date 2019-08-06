package com.qf.dao;

import com.qf.bean.Labels;

import java.util.List;

public interface LabelsMapper {
    //通过商品id查询标签
    List<Labels> selectByIid(Integer iid);

    int deleteByPrimaryKey(Integer id);

    int insert(Labels record);

    int insertSelective(Labels record);

    Labels selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Labels record);

    int updateByPrimaryKey(Labels record);
}
package com.qf.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.bean.Images;
import com.qf.bean.Item;
import com.qf.bean.Labels;
import com.qf.bean.Users;
import com.qf.dao.ImagesMapper;
import com.qf.dao.ItemMapper;
import com.qf.dao.LabelsMapper;
import com.qf.dao.UsersMapper;
import com.qf.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Createtime: 2019-08-05 14:13
 * 面向对象面向君，不负代码不负卿
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemMapper itemMapper;
    @Resource
    private ImagesMapper imagesMapper;
    @Resource
    private LabelsMapper labelsMapper;
    @Resource
    private UsersMapper usersMapper;

    //热门查询
    @Override
    public PageInfo<Item> findhot(int pageindex, int pagesize) {
        PageHelper.startPage(pageindex,pagesize);

        List<Item> hotList = itemMapper.findhot();
        for (Item item : hotList) {
            Integer iid = item.getId();
            //查询商品照片
            List<Images> imagesList = imagesMapper.selectByIid(iid);
            item.setImagesList(imagesList);
            //查询商品标签
            List<Labels> labelsList = labelsMapper.selectByIid(iid);
            item.setLabelsList(labelsList);
            //查询发布者
            Users users = usersMapper.selectByIid(iid);
            item.setUsers(users);
        }
        PageInfo<Item> pageInfo = new PageInfo<>(hotList);
        return pageInfo;
    }

    //国内查询
    @Override
    public PageInfo<Item> finddomestic(int pageindex, int pagesize) {
        PageHelper.startPage(pageindex,pagesize);
        List<Item> domesticList = itemMapper.finddomestic();
        for (Item item : domesticList) {
            Integer iid = item.getId();
            //查询商品照片
            List<Images> imagesList = imagesMapper.selectByIid(iid);
            item.setImagesList(imagesList);
            //查询商品标签
            List<Labels> labelsList = labelsMapper.selectByIid(iid);
            item.setLabelsList(labelsList);
            //查询发布者
            Users users = usersMapper.selectByIid(iid);
            item.setUsers(users);
        }
        PageInfo<Item> pageInfo = new PageInfo<>(domesticList);
        return pageInfo;
    }
    //周边查询
    @Override
    public PageInfo<Item> findnearby(String currentlocation,int pageindex, int pagesize) {
        PageHelper.startPage(pageindex,pagesize);
        Map map = new HashMap();
        map.put("address", currentlocation);
        List<Item> domesticList = itemMapper.findnearby(map);
        for (Item item : domesticList) {
            Integer iid = item.getId();
            //查询商品照片
            List<Images> imagesList = imagesMapper.selectByIid(iid);
            item.setImagesList(imagesList);
            //查询商品标签
            List<Labels> labelsList = labelsMapper.selectByIid(iid);
            item.setLabelsList(labelsList);
            //查询发布者
            Users users = usersMapper.selectByIid(iid);
            item.setUsers(users);
        }
        PageInfo<Item> pageInfo = new PageInfo<>(domesticList);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Item record) {
        return 0;
    }

    @Override
    public int insertSelective(Item record) {
        return 0;
    }

    @Override
    public Item selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Item record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Item record) {
        return 0;
    }
}

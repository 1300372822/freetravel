package com.qf.web;

import com.github.pagehelper.PageInfo;
import com.qf.bean.DataView;
import com.qf.bean.Images;
import com.qf.bean.Item;
import com.qf.service.ItemService;
import com.qf.util.DataUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:
 * @Createtime: 2019-08-05 14:12
 * 面向对象面向君，不负代码不负卿
 */
@RestController
public class ItemController {
    @Resource
    private ItemService itemService;

    //出发吧首页
    @RequestMapping(method = RequestMethod.POST, value = "/togo")
    public DataView<Images> toGo(){
        List<Images> images = itemService.top3();
        DataView<Images> dataView = new DataView<>();
        dataView.setCode(0);
        dataView.setMsg("成功");
        dataView.setData(images);
        return dataView;
    }
    //查询热门
    @RequestMapping(method = RequestMethod.POST, value = "/findhot")
    public DataView<Item> findHot( String place,int index){
        DataView<Item> dataView = new DataView();
        PageInfo<Item> pageInfo = itemService.findhot(index, DataUtil.PAGESIZE);
        dataView.setCode(0);
        dataView.setMsg("成功");
        dataView.setData(pageInfo.getList());
        return  dataView;
    }
    //查询国内
    @RequestMapping(method = RequestMethod.POST, value = "/finddomestic")
    public DataView<Item> findDomestic(String place, int index){
        DataView<Item> dataView = new DataView();
        PageInfo<Item> pageInfo = itemService.finddomestic(index, DataUtil.PAGESIZE);
        dataView.setCode(0);
        dataView.setMsg("成功");
        dataView.setData(pageInfo.getList());
        return  dataView;
    }

    //查询国内
    @RequestMapping(method = RequestMethod.POST, value = "/findnearby")
    public DataView<Item> findNearby(String place, int index, HttpSession session){
        DataView<Item> dataView = new DataView();
        String currentlocation = (String) session.getAttribute("currentlocation");
        PageInfo<Item> pageInfo = itemService.findnearby(currentlocation, index, DataUtil.PAGESIZE);
        dataView.setCode(0);
        dataView.setMsg("成功");
        dataView.setData(pageInfo.getList());
        return  dataView;
    }
}

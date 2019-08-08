package com.qf.web;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qf.bean.DataView;
import com.qf.bean.Item;
import com.qf.bean.Users;
import com.qf.service.UsersService;
import com.qf.util.RandomSalt;
import com.sun.net.httpserver.HttpsServer;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.User;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Description:我页面
 * @Createtime: 2019-07-30 09:49
 * 面向对象面向君，不负代码不负卿
 */
@RestController
public class UsersController {

    private HttpServletRequest request;
    private DataView dataView;
    @Resource
    private UsersService usersService;

    //验证码
    @RequestMapping(method = RequestMethod.POST,value ="/checkcode")
    public Map CheckCode( String checkphone,HttpSession session){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAInjUm9kCq5R2E", "T9LgGR0KY1MZlYhArmRgsREQnVkCwt");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", checkphone);
        request.putQueryParameter("SignName", "蜂鸟自由行");
        request.putQueryParameter("TemplateCode", "SMS_172007796");
        //生成的6位随机验证码
        int identifyingcode = (int)((Math.random()*9+1)*100000);
        session.setAttribute("identifyingcode", identifyingcode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+identifyingcode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("data", identifyingcode);
        return map;
    }

    //注册
    @RequestMapping(method = RequestMethod.POST,value = "/reg")
    public DataView logon( String phone,String password,int identifyingcode,HttpSession session){
        int identifyingcode1 = (int) session.getAttribute("identifyingcode");
        dataView = new DataView();
        if (usersService.isUserExistByPhone(phone)>0){
            dataView.setCode(1);
            dataView.setMsg("手机号已存在，请更换手机号重新注册！");
            return dataView;
        }else {
            //验证码正确
            if (identifyingcode==identifyingcode1){
                //设置用户默认信息
                Users user = new Users();
                user.setUsername("小蜂鸟#"+new Date() + (int)((Math.random()*9+1)*1000));
                user.setPhone(phone);
                user.setIcon("usericon/default.jpg");
                user.setRegdate(new Date());
                String salt = new RandomSalt().pwdRandom();
                user.setSalt(salt);
                Md5Hash md5password = new Md5Hash(password,salt,1);
                user.setPassword(md5password.toString());
                int k = usersService.insertSelective(user);
                if (k>0){
                    session.setAttribute("user", user);
                    dataView.setCode(0);
                    dataView.setMsg("成功");
                }else {
                    dataView.setCode(3);
                    dataView.setMsg("创建新用户失败");
                }
                return dataView;
            }else {
                dataView.setCode(2);
                dataView.setMsg("验证码不正确");
                return dataView;
            }

        }
    }

    //找回密码
    @RequestMapping(method = RequestMethod.POST,value = "/findbackpwd")
    public DataView findbackpwd( String checkphone, String newpwd, int identifyingcode,HttpSession session){
        int userid = usersService.isUserExistByPhone(checkphone);
        Users users = usersService.selectByPrimaryKey(userid);
        int identifyingcode1 = (int) session.getAttribute("identifyingcode");
        if (identifyingcode1==identifyingcode){
            String newsalt = new RandomSalt().pwdRandom();
            Md5Hash newpassword = new Md5Hash(newpwd,newsalt,1);
            users.setPassword(newpassword.toString());
            int k = usersService.updateByPrimaryKeySelective(users);
            dataView = new DataView();
            if (k>0){
                session.setAttribute("user",users );
                dataView.setCode(0);
                dataView.setMsg("成功");
            }else{
                dataView.setCode(1);
                dataView.setMsg("找回密码失败");
            }
        }else{
            dataView.setCode(2);
            dataView.setMsg("验证码错误！");
        }
        return dataView;
    }

    //登录失败
    @RequestMapping(method = RequestMethod.POST,value ="/tologin")
    public DataView toLogin(@RequestParam(defaultValue = "1") int count) throws Exception{
        //得到错误信息
        String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("shiroLoginFailure = " + shiroLoginFailure);
        dataView = new DataView<>();
        if (shiroLoginFailure!=null){
            count++;
            System.out.println(count);
            String msg = "";
            //判断错误次数
            if (count>=3){
                msg = "今日密码错误已达上限！账户已被锁定，请点击 找回密码";
            }else if (count<3){
                msg = "密码错误";
            }
            dataView.setCode(1);
            dataView.setMsg(msg);

            Map map = new HashMap();
            map.put("count", count);
            map.put("users", new ArrayList<Users>());
            List list = new ArrayList();
            list.add(map);
            dataView.setData(list);
            /*if (shiroLoginFailure.equals(UnknownAccountException.class.getName())){
                throw new Exception("用户名有误");
            }else if(shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())) {
                throw new Exception("密码有误");
            }else{
                throw new Exception("其他异常");
            }*/
        }
        return dataView;
    }

    //我主页(登陆成功后)
    @RequestMapping(method = RequestMethod.POST,value = "/userinfo")
    public DataView<Users> findUserInfo(HttpSession session){
        //保存session信息
        Users user = (Users) session.getAttribute("user");
        dataView = new DataView<>();
        dataView.setCode(0);
        dataView.setMsg("成功");
        List<Users> userList = new ArrayList<>();
        userList.add(user);
        dataView.setData(userList);
        return dataView;
    }

    //修改昵称
    @RequestMapping(method = RequestMethod.POST,value = "/updateusername")
    public DataView<Users> updateUsername(Users users,HttpSession session){
        Users user = (Users) session.getAttribute("user");
        user.setUsername(users.getUsername());
        user.setSex(users.getSex());
        user.setBirthday(users.getBirthday());
        user.setContact(users.getContact());
        int k = usersService.updateByPrimaryKeySelective(user);
        dataView = new DataView<>();
        if (k>0){
            session.setAttribute("user",user);
            dataView.setCode(0);
            dataView.setMsg("成功");
            List list = new ArrayList();
            list.add(user);
            dataView.setData(list);
        }else {
            dataView.setCode(1);
            dataView.setMsg("失败");
        }
        return dataView;
    }

    //修改头像
    @RequestMapping(method = RequestMethod.POST,value = "/updateicon")
    public DataView updateIcon(MultipartFile file,HttpServletRequest request,HttpSession session){
        try {
            String realPath = request.getRealPath("/usericon");
            Users user = (Users) session.getAttribute("user");
            file.transferTo(new File(realPath + "/" + user.getId() + "-" + file.getOriginalFilename()));
            user.setIcon(realPath + "/" + user.getId() + "-" + file.getOriginalFilename());
            int k = usersService.updateByPrimaryKeySelective(user);
            dataView = new DataView<>();
            if (k>0){
                session.setAttribute("user",user );
                dataView.setCode(0);
                dataView.setMsg("成功");
                List list = new ArrayList();
                list.add(user);
                dataView.setData(list);
            }else {
                dataView.setCode(1);
                dataView.setMsg("失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataView;
    }

    //我的货架
    @RequestMapping(method = RequestMethod.POST,value = "/finditemlist")
    public Map findItemList(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        Map itemMap = usersService.findItemList(user.getId());
        Integer publishcount = (Integer) itemMap.get("publishcount");
        Integer forwardCount = (Integer) itemMap.get("forwardCount");
        Integer saleCount = (Integer) itemMap.get("saleCount");
        dataView = new DataView<>();
        dataView.setCode(0);
        dataView.setMsg("成功");
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        Map dateMap = new HashMap();
        dateMap.put("publishcount",publishcount );
        dateMap.put("forwardCount",forwardCount );
        dateMap.put("saleCount",saleCount );
        map.put("date", dateMap);
        return map;
    }

    //修改密码
    @RequestMapping(value = "/updatepassword")
    public DataView updatePassword( String oldpwd,  String newpwd,HttpSession session){
        dataView = new DataView();
        Users user = (Users) session.getAttribute("user");
        Md5Hash oldpassword = new Md5Hash(oldpwd,user.getSalt(),1);
        //System.out.println(oldpassword);
        //判断输入的原始密码是否正确
        if (!(oldpassword.toString().equals(user.getPassword()))){
            dataView.setCode(1);
            dataView.setMsg("输入的当前密码错误！");
            return dataView;
        }
        String newsalt = new RandomSalt().pwdRandom();
        Md5Hash newpassword = new Md5Hash(newpwd,newsalt,1);
        user.setPassword(newpassword.toString());
        user.setSalt(newsalt);
        int k = usersService.updateByPrimaryKeySelective(user);
        if (k>0){
            session.setAttribute("user", user);
            dataView.setCode(0);
            dataView.setMsg("成功");
            return dataView;
        }else {
            dataView.setCode(2);
            dataView.setMsg("修改密码失败！");
            return dataView;
        }
    }
}

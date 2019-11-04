package com.ldu.service.impl;

import com.github.pagehelper.PageHelper;
import com.ldu.dao.UserMapper;
import com.ldu.pojo.User;
import com.ldu.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

//登录
    public User getUserByPhone(String phone) {
        User user  = userMapper.getUserByPhone(phone);
        return  user;
    }

    //注册
    public void addPUser(User user) {
        userMapper.addPUser(user);
    }


    public void updateUserName(User user) {
        userMapper.updateUserName(user);
    }

    @Override
    public int updateGoodsNum(Integer id, Integer goodsNum) {
        return userMapper.updateGoodsNum(id,goodsNum);
    }

    public User selectByPrimaryKey(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public int getUserNum() {
        List<User> users = userMapper.getUserList();
        return users.size();
    }

    @Override
    public List<User> getPageUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);//分页核心代码
        List<User> list =userMapper.getUserList();
        return list;
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void deleteUserById(String ids) {
        this.userMapper.deleteByPrimaryKey(Integer.parseInt(ids));
    }

    @Override
    public List<User> getPageUserByUser(String phone, String username, String qq, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list =userMapper.getUserListByUser(phone,username,qq);
        return list;
    }


}
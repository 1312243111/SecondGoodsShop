package com.ldu.dao;

import org.apache.ibatis.annotations.Param;

import com.ldu.pojo.User;

import java.util.List;

public interface UserMapper {


   User getUserByPhone(String phone);//通过手机号查询用户

   void addPUser(User user);//注册

   void updateUserName(User user);

   int updateGoodsNum(@Param("id") Integer id, @Param("goodsNum") Integer goodsNum);//更改用户的商品数量



    User selectByPrimaryKey(Integer id);

   public List<User> getUserList();//获取所有用户

    User getUserById(int id);

   int  deleteByPrimaryKey(int id);


    List<User> getUserListByUser(@Param("phone") String phone,@Param("username") String username,@Param("qq") String qq);
}
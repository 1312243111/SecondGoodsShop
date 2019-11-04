package com.ldu.service;

import java.util.List;

import com.ldu.pojo.User;

public interface UserService {
	//登录
	public User getUserByPhone(String phone);

//注册
	void addPUser(User user);

    void updateUserName(User user);

	int updateGoodsNum(Integer id, Integer goodsNum);

	User selectByPrimaryKey(Integer id);

   public int getUserNum();

	public List<User> getPageUser(int pageNum, int pageSize);

	public User getUserById(int id);

	public void deleteUserById(String idArr);

	public List<User> getPageUserByUser(String phone, String username, String qq, int pageNum, int pageSize);
}
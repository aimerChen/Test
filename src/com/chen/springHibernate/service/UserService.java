package com.chen.springHibernate.service;

import java.util.List;
import java.util.Map;

import com.chen.springHibernate.bean.User;

public interface UserService {
	public boolean register(User user);
	public User findUserByName(String name);
	public List<User> findUsersByLikeName(String name);
	public List<User> findAllUsers();
	public boolean deleteUserById(int userId);
	public int updateUser(User user);
	/**
	 * 
	 * @param map:roleId和userId
	 * @return
	 */
	public int addUserRole(Map<String,Integer> map);//删除用户的一个角色
	/**
	 * 
	 * @param map:roleId和userId
	 * @return
	 */
	public int deleteUserRole(Map<String,Integer> map);//给用户添加一个角色
	

	/**
	 * 删除所有角色
	 * @param userId
	 * @return
	 */
	public int deleteAllRolesOfUser(int userId);
}

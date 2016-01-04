package com.chen.springHibernate.dao;

import java.util.List;
import java.util.Map;

import com.chen.springHibernate.bean.User;

public interface UserDao {
	public int create(User user);
	public User findUserByName(String userName);
	/**
	 * 根据输入的字符搜索
	 * @param name
	 * @return
	 */
	public List<User> findUsersByLikeName(String name);
	public int updateUser(User user);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public int deleteUserById(int userId);
	/**
	 * 删除用户对应的所有role记录
	 * @param userId
	 * @return
	 */
	public int deleteAllRolesOfUser(int userId);
	
	/**
	 * 删除一个角色
	 * @param map
	 * @return
	 */
	public int deleteRole(Map<String,Integer> map);
	
	/**
	 * 添加一个角色
	 * @param map
	 * @return
	 */
	public int addRole(Map<String,Integer> map);
}

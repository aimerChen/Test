package com.chen.springHibernate.dao;

import java.util.List;

import com.chen.springHibernate.bean.User;

public interface UserDao {
	public int create(User user);
	public User findUserByName(User user);
	public List<User> findUsersByName(String name);
	public int updateUser(User user);
	public int deleteUserById(int userId);
	public int addRolesForUser(int userId,int[] rolesId);
}

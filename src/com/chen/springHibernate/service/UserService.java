package com.chen.springHibernate.service;

import java.util.List;

import com.chen.springHibernate.bean.User;

public interface UserService {
	public boolean register(User user);
	public User findUserByName(String name);
	public List<User> findUsersByLikeName(String name);
	public List<User> findAllUsers();
	public boolean delete(User user);
	public int addRolesForUser(int userId,int[] rolesId);
	public int deleteRolesForUser(int userId,int[] rolesId);
}

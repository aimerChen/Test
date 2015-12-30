package com.chen.springHibernate.dao;

import java.util.List;

import com.chen.springHibernate.bean.User;

public interface UserDao {
	public boolean create(User user);
	public User findUserByName(User user);
	public List<User> findUsersByName(String name);
}

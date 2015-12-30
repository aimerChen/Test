package com.chen.springHibernate.service;

import java.util.List;

import com.chen.springHibernate.bean.User;

public interface UserService {
	public boolean register(User user);
	public List<User> findUsersByName(String name);
}

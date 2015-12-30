package com.chen.springHibernate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.User;
import com.chen.springHibernate.dao.UserDao;
import com.chen.springHibernate.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao mUserDao;

	@Override
	public boolean register(User user) {
		User re=mUserDao.findUserByName(user);
		if(re!=null){
			return false;
		}else{
			return mUserDao.create(user);
		}
	}

	@Override
	public List<User> findUsersByName(String name) {
		List<User> list= mUserDao.findUsersByName(name);
		return list;
	}

}

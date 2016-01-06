package com.chen.springHibernate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.User;
import com.chen.springHibernate.dao.UserDao;
import com.chen.springHibernate.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao mUserDao;

	@Override
	public boolean register(User user) {
		String name=user.getName();
		User re = mUserDao.findUserByName(name);
		boolean result = false;
		if (re == null && mUserDao.create(user) == 1) {
			//user不存在并且创建成功
			result=true;
		}
		return result;
	}
	
	@Override
	public User findUserByName(String name) {
		if (name != null) {
			User user= mUserDao.findUserByName(name);
			if(user!=null){
				user.setRoles(mUserDao.findUserRolesById(user.getId()));
			}
			return user;
		} else {
			return null;
		}
	}
	
	@Override
	public List<User> findUsersByLikeName(String name) {
		if (name != null) {
			return mUserDao.findUsersByLikeName(name);
		} else {
			return null;
		}
	}
	
	/**
	 * 返回添加role的个数
	 */
	@Override
	public int addUserRole(Map<String,Integer> map) {
		return mUserDao.addRole(map);
	}

	/**
	 * 返回删除role的个数
	 */
	@Override
	public int deleteUserRole(Map<String, Integer> map) {
		return mUserDao.deleteRole(map);
	}

	@Override
	public boolean deleteUserById(int userId){
		return (mUserDao.deleteUserById(userId)==1)&&(mUserDao.deleteAllRolesOfUser(userId)>=0);
	}

	@Override
	public List<User> findAllUsers() {
		return mUserDao.findAllUsers();
	}

	@Override
	public int updateUser(User user) {
		return mUserDao.updateUser(user);
	}

	@Override
	public int deleteAllRolesOfUser(int userId) {
		return mUserDao.deleteAllRolesOfUser(userId);
	}
}

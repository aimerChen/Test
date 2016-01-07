package com.chen.springHibernate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.bean.User;
import com.chen.springHibernate.dao.UserDao;
import com.chen.springHibernate.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean create(User user) {
		String name=user.getName();
		User re = userDao.findUserByName(name);
		boolean result = false;
		if (re == null && userDao.create(user) == 1) {
			//user不存在并且创建成功
			result=true;
		}
		return result;
	}
	
	@Override
	public User findUserByName(String name) {
		if (name != null) {
			User user= userDao.findUserByName(name);
			if(user!=null){
				user.setRoles(userDao.findRoles(user.getId()));
			}
			return user;
		}
		return null;
	}
	
	@Override
	public List<User> findUsersByLikeName(String name) {
		if (name != null) {
			List<User> uList= userDao.findUsersByLikeName(name);
			if(uList!=null){
				for(User user:uList){
					user.setRoles(userDao.findRoles(user.getId()));
				}
			}
			return uList;
		} else {
			return null;
		}
	}
	
	@Override
	public List<User> findAllUsers() {
		List<User> uList= userDao.findAllUsers();
		if(uList!=null){
			for(User user:uList){
				user.setRoles(userDao.findRoles(user.getId()));
			}
		}
		return uList;
	}
	
	@Override
	public int update(User user) {
		int result=0;
		//改名字
		if(!StringUtils.isEmpty(user.getName())){
			result=userDao.update(user);
		}
		if(user.getRoles()!=null&&user.getRoles().size()>0){
			//删除所有的role
			userDao.deleteRoles(user.getId());
			List<Role> rList=user.getRoles();
			Map<String,Integer> map=new HashMap<>();
			map.put("userId", user.getId());
			for(Role role:rList){
				map.put("roleId", role.getId());
				//逐个添加role
				userDao.addRole(map);
			}
		}
		return result;
	}
	
	@Override
	public boolean delete(int userId){
		return (userDao.delete(userId)==1)&&(userDao.deleteRoles(userId)>=0);
	}
}

package com.chen.springHibernate.service.impl;

import java.util.HashMap;
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

//	Map<String,Object> pm=new HashMap<String,Object>();
//	pm.put("userId", user.getId());
//	if(user.getRoles()!=null){
//		List<Role> roles=user.getRoles();
//		for(Role role:roles){
//			pm.put("roleId", rolesId[i]);
//			if(mSqlSessionTemplate.delete("removeRoelsForUser",pm)==1){
//				result++;
//			}
//		}
//	}
	
	@Override
	public User findUserByName(String name) {
		if (name != null) {
			return mUserDao.findUserByName(name);
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
	public int addRolesForUser(int userId, int[] rolesId) {
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("userId", userId);
		int result=0;
		for(int i=0;i<rolesId.length;i++){
			map.put("roleId", rolesId[i]);
			if(mUserDao.addRole(map)==1){
				result++;
			}
		}
		return result;
	}

	/**
	 * 返回删除role的个数
	 */
	@Override
	public int deleteRolesForUser(int userId,int[] rolesId) {
		Map<String,Integer> pm=new HashMap<String,Integer>();
		pm.put("userId", userId);
		int result=0;
		for(int i=0;i<rolesId.length;i++){
			pm.put("roleId", rolesId[i]);
			if(mUserDao.deleteRole(pm)==1){
				result++;
			}
		}
		return result;
	}

	@Override
	public boolean delete(User user) {
		return (mUserDao.deleteUserById(user.getId())==1)&&(mUserDao.deleteAllRolesOfUser(user.getId())>=0);
	}

	@Override
	public List<User> findAllUsers() {
		return mUserDao.findAllUsers();
	}

}

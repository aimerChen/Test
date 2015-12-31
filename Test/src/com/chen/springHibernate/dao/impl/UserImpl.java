package com.chen.springHibernate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.chen.springHibernate.bean.User;
import com.chen.springHibernate.dao.UserDao;

@Repository
public class UserImpl implements UserDao{

	@Autowired
	private SqlSessionTemplate mSqlSessionTemplate;
	
	@Override
	public int create(User user) {
		return mSqlSessionTemplate.insert("saveUser",user);
	}

	@Cacheable(value="test-cache",key="#user.getName()")
	@CacheEvict(value="test-cache",key="#user.getName()")//evict:回收
	@Override
	public User findUserByName(User user) {
		return mSqlSessionTemplate.selectOne("queryUserByName",user);
	}

	@Cacheable(value="test-cache",key="#name")
	@CacheEvict(value="test-cache",key="#name")//evict:回收
	@Override
	public List<User> findUsersByName(String name) {
		System.out.println("走数据库List");
		return mSqlSessionTemplate.selectList("queryUsersByName",name);
	}

	@Override
	public int updateUser(User user) {
		return mSqlSessionTemplate.update("updateUser",user);
	}

	@Override
	public int deleteUserById(int id) {
		return mSqlSessionTemplate.update("deleteUserById",id);
	}

	@Override
	public int addRolesForUser(int userId, int[] rolesId) {
		Map<String,Object> pm=new HashMap<String,Object>();
		pm.put("userId", userId);
		int result=0;
		for(int i=0;i<rolesId.length;i++){
			pm.put("roleId", rolesId[i]);
			if(mSqlSessionTemplate.insert("addRoelsForUser",pm)==1){
				result++;
			}
		}
		return result;
	}
}

package com.chen.springHibernate.dao.impl;

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

	@Cacheable(value="test-cache",key="#userName")
	@CacheEvict(value="test-cache",key="#userName")//evict:回收
	@Override
	public User findUserByName(String userName) {
		return mSqlSessionTemplate.selectOne("queryUserByName",userName);
	}

	@Cacheable(value="test-cache",key="#name")
	@CacheEvict(value="test-cache",key="#name")//evict:回收
	@Override
	public List<User> findUsersByLikeName(String name) {
		System.out.println("走数据库List");
		return mSqlSessionTemplate.selectList("queryUsersByLikeName",name);
	}

	@Override
	public int updateUser(User user) {
		return mSqlSessionTemplate.update("updateUser",user);
	}

	@Override
	public int deleteUserById(int id) {
		return mSqlSessionTemplate.update("deleteUserById",id);
	}

	/**
	 * map中的key名字：roleId，userId
	 */
	@Override
	public int deleteRole(Map<String, Integer> map) {
		return mSqlSessionTemplate.delete("deleteRoleOfUser",map);
	}

	/**
	 * map中的key名字：roleId，userId
	 */
	@Override
	public int addRole(Map<String, Integer> map) {
		return mSqlSessionTemplate.delete("addRoleForUser",map);
	}

	@Override
	public int deleteAllRolesOfUser(int userId) {
		return mSqlSessionTemplate.delete("deleteAllRolesOfUser",userId);
	}

	@Override
	public List<User> findAllUsers() {
		return mSqlSessionTemplate.selectList("queryAllUsers");
	}
}

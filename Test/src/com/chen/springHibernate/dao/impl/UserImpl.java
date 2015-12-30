package com.chen.springHibernate.dao.impl;

import java.util.List;

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
	public boolean create(User user) {
		int result=mSqlSessionTemplate.insert("saveUser",user);
		return result>0?true:false;
	}

	@Cacheable(value="test-cache",key="#user.getName()")
	@CacheEvict(value="test-cache",key="#user.getName()")//evict:回收
	@Override
	public User findUserByName(User user) {
		System.out.println("走数据库User");
		User dbuser=mSqlSessionTemplate.selectOne("queryUserByName",user);
		if(dbuser!=null){
			System.out.println("object re:"+dbuser.toString());
		}else{
			System.out.println("dbuser不存在");
		}
		return dbuser;
	}

	@Cacheable(value="test-cache",key="#name")
	@CacheEvict(value="test-cache",key="#name")//evict:回收
	@Override
	public List<User> findUsersByName(String name) {
		System.out.println("走数据库List");
		List<User> list= mSqlSessionTemplate.selectList("queryUsersByName",name);
		System.out.println("object:"+list.toString());
		return list;
	}
}

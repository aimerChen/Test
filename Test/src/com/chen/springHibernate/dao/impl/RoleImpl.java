package com.chen.springHibernate.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.dao.RoleDao;

/**  
* @author Chen Benwen 
* @E-mail:  
* @version V 1.0.0
* @创建时间：Dec 30, 2015 2:34:35 PM  
* @类说明 :
*/
@Repository
public class RoleImpl  implements RoleDao{

	@Autowired
	private SqlSessionTemplate mSqlSessionTemplate;
	
	@Override
	public int create(Role role) {
		return mSqlSessionTemplate.insert("saveRole",role);
	}

	@Override
	public Role findRoleByName(String roleName) {
		return mSqlSessionTemplate.selectOne("queryRoleByName",roleName);
	}

	@Override
	public int update(Role role) {
		return mSqlSessionTemplate.update("updateRole",role);
	}

	@Override
	public List<Role> queryRolesByUserId(int userId) {
		return mSqlSessionTemplate.selectList("queryRolesByUserId",userId);
	}

}

package com.chen.springHibernate.dao.impl;

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
	public boolean create(Role role) {
		int result=mSqlSessionTemplate.insert("saveRole",role);
		return result>0?true:false;
	}

	@Override
	public Role findRoleByName(String roleName) {
		Role role=mSqlSessionTemplate.selectOne("queryRoleByName",roleName);
		return role;
	}

}

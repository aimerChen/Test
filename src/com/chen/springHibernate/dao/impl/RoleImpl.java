package com.chen.springHibernate.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chen.springHibernate.bean.Path;
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
	
//	@Override
//	public int create(List<Role> roleList) {
//		int result=0;
//		SqlSession session=mSqlSessionTemplate.getSqlSessionFactory().openSession();
//		if(session!=null){
//			try{
//				result=session.insert("saveRoles", roleList);
//				Map<String,Object> map=new HashMap<String,Object>();
//				for(Role role:roleList){
//					map.put("roleid", role.getId());
//					map.put("paths", role.getPath());
//					session.insert("savePathsForRole", map);
//				}
//				session.commit();
//			}finally{
//				session.close();
//			}
//		}
//		return result;
//	}
//	
	@Override
	public int register(Role role) {
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
	public List<Role> queryAllRoles() {
		return mSqlSessionTemplate.selectList("queryAllRoles");
	}


	@Override
	public List<Path> queryPathsByRoleId(int roleId) {
		return mSqlSessionTemplate.selectList("queryPathsByRoleId", roleId);
	}

	@Override
	public int deletePathsByRoleId(int roleId) {
		return mSqlSessionTemplate.delete("deletePathsByRoleId", roleId);
	}

	@Override
	public int registerPathForRole(Map<String,Integer> map) {
		return mSqlSessionTemplate.insert("savePathForRole", map);
	}

	@Override
	public Role findRoleById(int roleId) {
		return mSqlSessionTemplate.selectOne("queryRoleByRoleId", roleId);
	}

	@Override
	public int deleteRole(int roleId) {
		return mSqlSessionTemplate.delete("deleteRole", roleId);
	}

	@Override
	public Path findRolePathById(Map<String,Integer> map) {
		return mSqlSessionTemplate.selectOne("queryRolePathById", map);
	}
}

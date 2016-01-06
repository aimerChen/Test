package com.chen.springHibernate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.dao.RoleDao;
import com.chen.springHibernate.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public boolean register(Role role) {
		Role dbRole=roleDao.findRoleByName(role.getName());
		if(dbRole!=null){
			//角色存在，则更新角色对应的path
			return false;
		}else{//创建角色
			if(roleDao.register(role)==1){//role 创建成功
				return true;
			}else{
				//角色创建不成功
				return false;
			}
		}
	}
	

	@Override
	public int addRolePath(Map<String, Integer> map) {
		return roleDao.registerPathForRole(map);
	}

	@Override
	public Role findRoleByName(String roleName) {
		Role role= roleDao.findRoleByName(roleName);
		if(role!=null){
			role.setPath(roleDao.queryPathsByRoleId(role.getId()));
		}
		return role;
	}

	@Override
	public List<Role> findAllRoles() {
		List<Role> roleList=roleDao.queryAllRoles();
		if(roleList!=null){
			for(Role role:roleList){
				role.setPath(roleDao.queryPathsByRoleId(role.getId()));
			}
		}
		return roleList;
	}

	@Override
	public Role findRoleById(int roleId) {
		Role role= roleDao.findRoleById(roleId);
		if(role!=null){
			role.setPath(roleDao.queryPathsByRoleId(roleId));
		}
		return role;
	}

	@Override
	public int deleteRoleById(int roleId) {
		int result=0;
		if((result=roleDao.deleteRole(roleId))==1){
			roleDao.deletePathsByRoleId(roleId);
		}
		return result;
	}

	@Override
	public Path findRolePathById(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return roleDao.findRolePathById(map);
	}

	@Override
	public int update(Role role) {
		int result=0;
		if(role!=null){
			result=roleDao.update(role);
		}
		return result;
	}

	@Override
	public int deleteRolePathsById(int roleId) {
		return roleDao.deletePathsByRoleId(roleId);
	}


}

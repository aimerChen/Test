package com.chen.springHibernate.service.impl;

import java.util.HashMap;
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
		int id=-1;
		if(dbRole!=null){
			//角色存在，则更新角色对应的path
			id=dbRole.getId();
		}else{//创建角色和path
			if(roleDao.register(role)==1){//role 创建成功
				id=role.getId();
			}else{
				//角色创建不成功
				return false;
			}
		}
		//role在数据库存在了
		if(id>=0&&role.getPath()!=null){
			Map<String,Integer> pm=new HashMap<String,Integer>();
			pm.put("roleid", role.getId());
			for(Path path:role.getPath()){
				pm.put("pathid", path.getId());
				roleDao.registerPathForRole(pm);
			}
		}
		return true;
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
	public List<Role> queryAllRoles() {
		return roleDao.queryAllRoles();
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
	public boolean deleteRole(int roleId) {
		return (roleDao.deleteRole(roleId)==1)&&(roleDao.deletePathsByRoleId(roleId)>=0);
	}

}

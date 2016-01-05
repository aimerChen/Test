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
	public boolean register(String roleName,int[] pathIdList) {
		Role dbRole=roleDao.findRoleByName(roleName);
		int id=-1;
		Role role=new Role();
		if(dbRole!=null){
			//角色存在，则更新角色对应的path
//			id=dbRole.getId();
			return false;
		}else{//创建角色和path
			role.setName(roleName);
			if(roleDao.register(role)==1){//role 创建成功
				id=role.getId();
			}else{
				//角色创建不成功
				return false;
			}
		}
		//role在数据库存在了,然后插入对应的path权限
		if(id>=0&&pathIdList.length>0){
			Map<String,Integer> pm=new HashMap<String,Integer>();
			pm.put("roleid", role.getId());
			for(int i=0;i<pathIdList.length;i++){
				pm.put("pathid", pathIdList[i]);
				if(roleDao.findRolePathById(pm)==null){
					//该角色对应的path没有插入到数据库，现在插入
					addRolePaths(pm);
				}
			}
		}
		return true;
	}
	

	@Override
	public int addRolePaths(Map<String, Integer> map) {
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
		// TODO Auto-generated method stub
		return 0;
	}


}

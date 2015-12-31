package com.chen.springHibernate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.dao.PathDao;
import com.chen.springHibernate.dao.RoleDao;
import com.chen.springHibernate.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PathDao pathDao;
	
	@Override
	public boolean register(Role role) {
		Role dbRole=roleDao.findRoleByName(role.getName());
		int id=-1;
		if(dbRole!=null){
			//角色存在，则更新角色对应的path
			id=dbRole.getId();
		}else{//创建角色和path
			if(roleDao.create(role)==1){//role 创建成功
				id=role.getId();
			}else{
				return false;
			}
		}
		//role在数据库存在了
		if(id>=0){
			for(Path path:role.getPath()){
				path.setRole_id(id);
				pathDao.create(path);
			}
		}
		return true;
	}

	@Override
	public Role findRoleByName(String roleName) {
		return roleDao.findRoleByName(roleName);
	}

	@Override
	public List<Role> queryRolesByUserId(int userId) {
		if(userId<0){
			return null;
		}
		return roleDao.queryRolesByUserId(userId);
	}

}

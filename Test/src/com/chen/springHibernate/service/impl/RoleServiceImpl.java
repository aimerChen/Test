package com.chen.springHibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.dao.RoleDao;
import com.chen.springHibernate.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	@Override
	public boolean register(String roleName) {
		Role re=roleDao.findRoleByName(roleName);
		if(re!=null){
			return false;
		}else{
			return roleDao.create(roleName);
		}
	}

	@Override
	public Role findRoleByName(String roleName) {
		return roleDao.findRoleByName(roleName);
	}

}

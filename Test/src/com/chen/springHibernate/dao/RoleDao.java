package com.chen.springHibernate.dao;

import com.chen.springHibernate.bean.Role;

public interface RoleDao {
	public boolean create(Role role);
	public Role findRoleByName(String roleName);
}
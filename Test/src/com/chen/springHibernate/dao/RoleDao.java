package com.chen.springHibernate.dao;

import java.util.List;

import com.chen.springHibernate.bean.Role;

public interface RoleDao {
	public int create(Role role);
	public int update(Role role);
	public Role findRoleByName(String roleName);
	public List<Role> queryRolesByUserId(int userId);
}

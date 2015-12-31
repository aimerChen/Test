package com.chen.springHibernate.service;

import java.util.List;

import com.chen.springHibernate.bean.Role;

public interface RoleService {
	public boolean register(Role role);
	public Role findRoleByName(String roleName);
	public List<Role> queryRolesByUserId(int userId);
}

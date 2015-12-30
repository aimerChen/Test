package com.chen.springHibernate.service;

import com.chen.springHibernate.bean.Role;

public interface RoleService {
	public boolean register(Role role);
	public Role findRoleByName(String roleName);
}

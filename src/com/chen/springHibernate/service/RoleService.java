package com.chen.springHibernate.service;

import java.util.List;
import java.util.Map;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;

public interface RoleService {
	public boolean register(Role role);
	public Role findRoleByName(String roleName);
	public Role findRoleById(int roleId);
	public List<Role> findAllRoles();
	public int deleteRoleById(int roleId);
	public int update(Role role);
	public Path findRolePathById(Map<String,Integer> map);
	public int deleteRolePathsById(int roleId);
	public int addRolePath(Map<String,Integer> map);
}

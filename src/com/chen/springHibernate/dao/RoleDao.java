package com.chen.springHibernate.dao;

import java.util.List;
import java.util.Map;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;

public interface RoleDao {
	public int register(Role role);
	public int registerPathForRole(Map<String,Integer> map);
	public int update(Role role);
	public Role findRoleByName(String roleName);
	public Role findRoleById(int roleId);
	public List<Role> queryAllRoles();
	public List<Path> queryPathsByRoleId(int roleId);
	public int deletePathsByRoleId(int roleId);
	public int deleteRole(int roleId);
}

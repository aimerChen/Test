package com.chen.springHibernate.dao;

import java.util.List;
import java.util.Map;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;

public interface RoleDao {
	public int register(Role role);
	/**
	 * 
	 * @param map:两个key：(roleid，pathid)
	 * @return
	 */
	public int registerPathForRole(Map<String,Integer> map);
	public int update(Role role);
	public Role findRoleByName(String roleName);
	public Role findRoleById(int roleId);
	public List<Role> queryAllRoles();
	public List<Path> queryPathsByRoleId(int roleId);
	/**
	 * 在rolepath表中的记录：表示该role是否已经有这个path的权限了；有，则不写入；否则，插入到数据库
	 * @param map:两个key：pathid和roleid
	 * @return
	 */
	public Path findRolePathById(Map<String,Integer> map);
	public int deletePathsByRoleId(int roleId);
	public int deleteRole(int roleId);
}

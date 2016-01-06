package com.chen.springHibernate.dao;

import java.util.List;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;

public interface PathDao {
	public int register(Path path);
	/**
	 * 获取path对应的role
	 * @param pathId
	 * @return
	 */
	public List<Role> findRolesofPathById(int pathId);
	public Path findPathByName(String pathName);
	public List<Path> findPathsByLikeName(String pathName);
	public List<Path> findAllPaths();
	public int update(Path path);
	public int deletePathById(int id);
	/**
	 * 删除rolepath表中path对应的所有role 
	 * @param id
	 * @return
	 */
	public int deletePathRolesById(int id);
}

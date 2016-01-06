package com.chen.springHibernate.service;

import java.util.List;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;

public interface PathService {
	public boolean register(Path path);
	public List<Path> findAllPaths();
	/**
	 * 获取path对应的role
	 * @param pathId
	 * @return
	 */
	public List<Role> findRolesofPathById(int pathId);
	
	public List<Path> findPathsByName(String pathName);
	public List<Path> findPathsByLikeName(String pathName);
	public int update(Path path);
	public int deleteById(int id);
}

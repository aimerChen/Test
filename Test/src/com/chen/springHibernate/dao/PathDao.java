package com.chen.springHibernate.dao;

import java.util.List;

import com.chen.springHibernate.bean.Path;

public interface PathDao {
	public int create(Path path);
	public int update(Path path);
	public List<Path> queryPathsByName(String pathName);
	public List<Path> queryPathsByLikeName(String pathName);
	public List<Path> queryPathsByRoleId(int roleId);
	public int deletePathsByRoleId(int roleId);
	public int delete(Path path);
}

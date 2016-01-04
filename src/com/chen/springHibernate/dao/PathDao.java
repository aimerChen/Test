package com.chen.springHibernate.dao;

import java.util.List;

import com.chen.springHibernate.bean.Path;

public interface PathDao {
	public int register(Path path);
	public Path findPathByName(String pathName);
	public List<Path> findPathsByLikeName(String pathName);
	public List<Path> findAllPaths();
	public int update(Path path);
	public int delete(Path path);
	public int deleteById(int id);
}

package com.chen.springHibernate.service;

import java.util.List;

import com.chen.springHibernate.bean.Path;

public interface PathService {
	public boolean register(Path path);
	public List<Path> findAllPaths();
	public List<Path> findPathsByName(String pathName);
	public List<Path> findPathsByLikeName(String pathName);
	public int update(Path path);
	public int delete(Path path);
}

package com.chen.springHibernate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.dao.PathDao;
import com.chen.springHibernate.service.PathService;

@Service
@Transactional
public class PathServiceImpl implements PathService{

	@Autowired
	private PathDao pathDao;

	@Override
	public boolean register(Path path) {
		Path dbPath=pathDao.findPathByName(path.getName());
		if(dbPath!=null){
			//角色存在，则更新角色对应的path
			return false;
		}else{//创建角色和path
			return pathDao.register(path)==1;//role 创建成功
		}
	}

	@Override
	public List<Path> findPathsByName(String pathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Path> findPathsByLikeName(String pathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Path path) {
		// TODO Auto-generated method stub
		return pathDao.update(path);
	}

	@Override
	public List<Path> findAllPaths() {
		// TODO Auto-generated method stub
		return pathDao.findAllPaths();
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		int result=0;
		if((result=pathDao.deletePathById(id))==1){
			pathDao.deletePathRolesById(id);
		}
		return result;
	}

	@Override
	public List<Role> findRolesofPathById(int pathId) {
		return pathDao.findRolesofPathById(pathId);
	}
}

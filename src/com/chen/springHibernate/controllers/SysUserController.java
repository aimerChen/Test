package com.chen.springHibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.bean.User;
import com.chen.springHibernate.service.PathService;
import com.chen.springHibernate.service.RoleService;
import com.chen.springHibernate.service.UserService;
import com.chen.springHibernate.util.Utils;
import com.google.gson.Gson;

@Controller
@Scope("prototype")
@RequestMapping(value = "/sysUser")
public class SysUserController {

	@Autowired
	private RoleService mRoleService;
	
	@Autowired
	private UserService mUserService;
	
	@Autowired
	private PathService mPathService;
	
	@RequestMapping(value="createRole", method = RequestMethod.GET)
	public String showRole(){
		return "/sysUser/register_role";
	}

	/**
	 * 
	 * @param name
	 * @param path
	 * @return
	 * <p>-1:表示没接收到某些字段
	 * <p>0:表示已经占用
	 * <p>1:创建成功
	 * <p>2：系统预留角色，不能被创建
	 */
	@RequestMapping(value="createRole", method = RequestMethod.POST)
	@ResponseBody
	public int createRole(@RequestParam String name){
		System.out.println("role name:"+name);
		//1.先判断路径不为空
		if(name!=null){
			if(name.equalsIgnoreCase(Utils.DEFAULT_SYSUSER)){
				return 2;
			}
			Role role=new Role();
			role.setName(name);
			//创建角色：如果角色已经存在，则不再创建；
			if(mRoleService.create(role)){
				return 1;
			}else{
				return 0;
			}
		}
		return -1;
	}

	/**
	 * 检查角色是否能被床架
	 * @return
	 * <p>1:能被创建
	 * <p>0:不能被创建
	 */
	@RequestMapping(value="checkRole", method = RequestMethod.POST)
	@ResponseBody
	public int checkRole(){
		return 1;
	}
	
	
	/**
	 * 返回所有的角色
	 * @return
	 */
	@RequestMapping(value="showAllRoles", method = RequestMethod.POST)
	@ResponseBody
	public String showAllRoles(){
		return new Gson().toJson(mRoleService.findAllRoles());
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteRoleById", method = RequestMethod.POST)
	@ResponseBody
	public int deleteRoleById(@RequestParam int id){
		return mRoleService.delete(id);
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping(value="updateRoleById", method = RequestMethod.POST)
	@ResponseBody
	public int updateRoleById(@ModelAttribute Role role){
		return mRoleService.update(role);
	}

	/**
	 * 返回path jsp页面
	 * @param name
	 * @return
	 */
	@RequestMapping(value="createPath", method = RequestMethod.GET)
	public String showPath(){
		return "/sysUser/register_path";
	}
	
	/**
	 * 
	 * @param name
	 * @return <p>返回添加path的个数
	 */
	@RequestMapping(value="createPath", method = RequestMethod.POST)
	@ResponseBody
	public int createPath(@RequestParam String pathStr){
		int result=0;
		if(pathStr!=null){
			String[] pathArr=pathStr.split(";");
			Path path=null;
			for(int i=0;i<pathArr.length;i++){
				path=new Path();
				path.setName(pathArr[i]);
				if(mPathService.create(path)){
					result++;
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param name
	 * @return <p>返回添加path的个数
	 */
	@RequestMapping(value="showAllPaths", method = RequestMethod.POST)
	@ResponseBody
	public String showAllPaths(){
		return new Gson().toJson(mPathService.findAllPaths());
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletePathById", method = RequestMethod.POST)
	@ResponseBody
	public int deletePathById(@RequestParam int id){
		return mPathService.delete(id);
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping(value="updatePathById", method = RequestMethod.POST)
	@ResponseBody
	public int updatePathById(@ModelAttribute Path path){
		return mPathService.update(path);
	}
	
	
	/**
	 * 返回所有的用户
	 * @return
	 */
	@RequestMapping(value="createUser", method = RequestMethod.GET)
	public String showUser(){
		return "/sysUser/register_user";
	}
	
	/**
	 * 返回所有的用户
	 * @return
	 */
	@RequestMapping(value="findAllUsers", method = RequestMethod.POST)
	@ResponseBody
	public String findAllUsers(){
		return new Gson().toJson(mUserService.findAllUsers());
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteUserById", method = RequestMethod.POST)
	@ResponseBody
	public int deleteUserById(@RequestParam int id){
		return mUserService.delete(id)?1:0;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping(value="updateUserById", method = RequestMethod.POST)
	@ResponseBody
	public int updateUserById(@ModelAttribute User user){
		return mUserService.update(user);
	}

}

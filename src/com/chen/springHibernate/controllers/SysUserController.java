package com.chen.springHibernate.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chen.springHibernate.bean.Path;
import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.service.PathService;
import com.chen.springHibernate.service.RoleService;
import com.chen.springHibernate.service.UserService;
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
	public String showRole(String name){
		return "/sysUser/register_role";
	}
	
	@RequestMapping(value="createPath", method = RequestMethod.GET)
	public String showPath(String name){
		return "/sysUser/register_path";
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * <p>-1:表示没接收到某些字段
	 * <p>0:表示没有占用
	 * <p>1:表示已经占用
	 */
	@RequestMapping(value="checkRole", method = RequestMethod.POST)
	@ResponseBody
	public int checkRole(@RequestParam String name){
		if(name!=null){
			return mRoleService.findRoleByName(name)!=null?1:0;
		}
		return -1;
	}
	
	/**
	 * 
	 * @param name
	 * @param path
	 * @return
	 * <p>-1:表示没接收到某些字段
	 * <p>0:表示已经占用
	 * <p>1:创建成功
	 */
	@RequestMapping(value="createRole", method = RequestMethod.POST)
	@ResponseBody
	public int createRole(@RequestParam String name,@RequestParam String path){
		System.out.println("role name:"+name+",path:"+path);
		//1.先判断路径不为空
		String[] pathArray =null;
		if(path!=null&&name!=null){
			pathArray=path.split(";");
			List<Path> pathList =new ArrayList<Path>();
			Path pathObject=null;
			for(int i=0;i<pathArray.length;i++){
				pathObject=new Path();
				pathObject.setName(pathArray[i]);
				pathList.add(pathObject);
			}
			Role role=new Role();
			role.setName(name);
			role.setPath(pathList);
			//2角色存不存在：存在，则更新对应的path；否则，先创建角色，再创建path
			return mRoleService.register(role)==true?1:0;
		}
		return -1;
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
				if(mPathService.register(path)){
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
	 * @param userId
	 * @param roleIdStr
	 * @return
	 */
	@RequestMapping(value="addRolesForUser", method = RequestMethod.POST)
	@ResponseBody
	public int addRolesForUser(@RequestParam int userId,@RequestParam String roleIdStr){
		if(roleIdStr!=null&&userId>=0){
			String[] ints=roleIdStr.split(",");
			int[] rolesId = new int[ints.length];
			for(int i=0;i<ints.length;i++){
				rolesId[i]=Integer.parseInt(ints[i]);
			}
			if(userId>=0){
				mUserService.addRolesForUser(userId, rolesId);
			}
			return 1;
		}
		return 0;
	}
}
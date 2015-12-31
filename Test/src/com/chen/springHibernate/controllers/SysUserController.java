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
import com.chen.springHibernate.service.RoleService;
import com.chen.springHibernate.service.UserService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/sysUser")
public class SysUserController {

	@Autowired
	private RoleService mRoleService;
	
	@Autowired
	private UserService mUserService;
	
	@RequestMapping(value="home", method = RequestMethod.GET)
	public String home(){
		return "/sysUser/home";
	}
	
	@RequestMapping(value="createRole", method = RequestMethod.GET)
	public String showRole(String name){
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
	 * @param userId
	 * @param roleIdStr
	 * @return
	 */
	@RequestMapping(value="checkRole", method = RequestMethod.POST)
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

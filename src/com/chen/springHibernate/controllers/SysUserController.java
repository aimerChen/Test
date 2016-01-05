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
	public int createRole(@RequestParam String name,@RequestParam String pathIdList){
		System.out.println("role name:"+name+",pathIdList:"+pathIdList);
		//1.先判断路径不为空
		if(pathIdList!=null&&pathIdList.length()>0&&name!=null){
			String[] pathIdStr=pathIdList.split(",");
			int strLength=pathIdStr.length;
			int[] pathArray =new int[strLength];
			for(int i=0;i<strLength;i++){
				if(pathIdStr[i].length()>0){
					pathArray[i]=Integer.parseInt(pathIdStr[i]);
				}
			}
			//2角色存不存在：存在，则更新对应的path；否则，先创建角色，再创建path
			return mRoleService.register(name,pathArray)==true?1:0;
		}
		return -1;
	}

	/**
	 * 返回所有的角色
	 * @return
	 */
	@RequestMapping(value="findAllRoles", method = RequestMethod.POST)
	@ResponseBody
	public String findAllRoles(){
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
		return mRoleService.deleteRoleById(id);
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping(value="updateRoleById", method = RequestMethod.POST)
	@ResponseBody
	public int updateRoleById(@RequestParam int id,@RequestParam String name,@RequestParam String pathListId){
		Role role=new Role();
		role.setId(id);
		role.setName(name);
		String[] strArr=pathListId.split(",");
		int result= mRoleService.update(role);
		if(result==1){
			mRoleService.deleteRolePathsById(id);
			for(int i=0;i<strArr.length;i++){
				
				mRoleService.addRolePaths(map);
			}
		}
		return result;
	}
	
	
	/**
	 * 返回path jsp页面
	 * @param name
	 * @return
	 */
	@RequestMapping(value="createPath", method = RequestMethod.GET)
	public String showPath(String name){
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
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletePathById", method = RequestMethod.POST)
	@ResponseBody
	public int deletePathById(@RequestParam int id){
		return mPathService.deleteById(id);
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
	@RequestMapping(value="findAllUsers", method = RequestMethod.POST)
	@ResponseBody
	public String findAllUsers(){
		return new Gson().toJson(mUserService.findAllUsers());
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

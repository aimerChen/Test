package com.chen.springHibernate.controllers;

import java.util.HashMap;
import java.util.Map;

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
	 */
	@RequestMapping(value="createRole", method = RequestMethod.POST)
	@ResponseBody
	public int createRole(@RequestParam String name,@RequestParam String pathIdList){
		System.out.println("role name:"+name+",pathIdList:"+pathIdList);
		//1.先判断路径不为空
		if(name!=null){
			Role role=new Role();
			role.setName(name);
			//创建角色：如果角色已经存在，则不再创建；
			if(mRoleService.register(role)){
				String[] pathIdStr=pathIdList.split(",");
				int strLength=pathIdStr.length;
				Map<String,Integer> map=new HashMap<String,Integer>();
				map.put("roleid", role.getId());
				for(int i=0;i<strLength;i++){
					if(pathIdStr[i].length()>0){
						int pathid=Integer.parseInt(pathIdStr[i]);
						//为角色创建path
						map.put("pathid", pathid);
						mRoleService.addRolePath(map);
					}
				}
				return 1;
			}else{
				return 0;
			}
		}
		return -1;
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
			Map<String,Integer> map=new HashMap<String,Integer>();
			map.put("roleid", id);
			int pathId=0;
			mRoleService.deleteRolePathsById(id);//先删除所有的path
			for(int i=0;i<strArr.length;i++){//再添加新的path
				if(strArr[i].length()>0){
					pathId=Integer.parseInt(strArr[i]);
					map.put("pathid", pathId);
					mRoleService.addRolePath(map);
				}
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
		return mUserService.deleteUserById(id)?1:0;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	@RequestMapping(value="updateUserById", method = RequestMethod.POST)
	@ResponseBody
	public int updateUserById(@RequestParam int id,@RequestParam String name,@RequestParam String pathListId){
		User user=new User();
		user.setId(id);
		user.setName(name);
		String[] strArr=pathListId.split(",");
		int result= mUserService.updateUser(user);
		if(result==1){
			Map<String,Integer> map=new HashMap<String,Integer>();
			map.put("userId", id);
			int roleId=0;
			mUserService.deleteAllRolesOfUser(id);//先删除所有的path
			for(int i=0;i<strArr.length;i++){//再添加新的path
				if(strArr[i].length()>0){
					roleId=Integer.parseInt(strArr[i]);
					map.put("roleId", roleId);
					mUserService.addUserRole(map);
				}
			}
		}
		return result;
	}

}

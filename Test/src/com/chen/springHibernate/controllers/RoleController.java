package com.chen.springHibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chen.springHibernate.bean.Role;
import com.chen.springHibernate.service.RoleService;

/**  
* @author Chen Benwen 
* @E-mail:  
* @version V 1.0.0
* @创建时间：Dec 30, 2015 1:01:31 PM  
* @类说明 :
*/

@Controller
@Scope("prototype")
@RequestMapping(value = "/role")
public class RoleController {
	
	@Autowired
	private RoleService mRoleService;
	
	@RequestMapping(value="createRole", method = RequestMethod.GET)
	public String showRole(String name){
		return "/register_role";
	}
	
	@RequestMapping(value="createRole", method = RequestMethod.POST)
	@ResponseBody
	public String createRole(@RequestParam String name,@RequestParam String priority){
		System.out.println("role name:"+name);
		char prio=(char) Integer.parseInt(priority);
		Role role=new Role();
		role.setName(name);
		role.setPriority(prio);
		if(name!=null){
			mRoleService.register(role);
			return "success";
		}
		return "failed";
	}
}

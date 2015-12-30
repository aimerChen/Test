package com.chen.springHibernate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String createRole(@RequestParam String name){
		System.out.println("role name:"+name);
		if(name!=null){
			mRoleService.register(name);
			return "success";
		}
		return "failed";
	}
}

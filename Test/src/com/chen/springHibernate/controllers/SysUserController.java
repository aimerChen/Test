package com.chen.springHibernate.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("prototype")
@RequestMapping(value = "/sysuser")
public class SysUserController {
	
	@RequestMapping(value="home", method = RequestMethod.GET)
	public String home(){
		return "/sysuser/home";
	}
}

package com.chen.springHibernate.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**  
* @author Chen Benwen 
* @E-mail:  
* @version V 1.0.0
* @创建时间：Dec 30, 2015 3:46:37 PM  
* @类说明 :
*/
@Controller
@Scope("prototype")
@RequestMapping(value = "/admin")
public class AdminController {
	
	@RequestMapping(value="home", method = RequestMethod.GET)
	public String home(){
		return "/admin/home";
	}
}

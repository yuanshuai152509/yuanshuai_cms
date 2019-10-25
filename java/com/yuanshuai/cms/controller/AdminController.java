package com.yuanshuai.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @ClassName: AdminController 
 * @Description: 管理员后台控制器
 * @author: yuanshuai
 * @date: 2019年9月11日 下午1:45:55
 */
@RequestMapping("admin")
@Controller
public class AdminController {
	
	/**
	 * 
	 * @Title: index 
	 * @Description: 进入管理员后台首页
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = {"index","/",""})
	public String index() {
		
		return "admin/index";
	}

}

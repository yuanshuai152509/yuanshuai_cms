package com.yuanshuai.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: MyController 
 * @Description: 个人中心
 * @author: yuanshuai
 * @date: 2019年9月17日 上午8:29:25
 */
@RequestMapping("my")
@Controller
public class MyController {

	/**
	 * 
	 * @Title: index 
	 * @Description: 个人中心的首页
	 * @return
	 * @return: String
	 */
	@GetMapping({"/","","/index"})
	public  String index() {
		
		return "my/index";
		
	}
}

package com.yuanshuai.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.service.UserService;
import com.yuanshuai.cms.util.CMSContant;
import com.yuanshuai.cms.util.CMSException;
import com.yuanshuai.cms.vo.UserVO;

/**
 * 
 * @ClassName: PassportController 
 * @Description: 登录注册的控制器
 * @author: yuanshuai
 * @date: 2019年9月18日 上午8:30:53
 */
@RequestMapping("passport")
@Controller
public class PassportController {
	@Resource
	private UserService userService;
	/**
	 * 
	 * @Title: reg 
	 * @Description: 执行注册
	 * @param model
	 * @param userVO
	 * @return
	 * @return: String
	 */
	@PostMapping("reg")
	public String reg( Model model , @Valid UserVO userVO,BindingResult result,RedirectAttributes redirectAttributes ) {
		
		try {
			
			//如果输入的信息不符合要求.则回到主页面
			if(result.hasErrors()) {
				
				return "passport/reg";
			}
			
			userService.insertSelective(userVO);
			//注册成功.跳转到登录页面,并且在登录显示注册的用户名
			redirectAttributes.addFlashAttribute("username", userVO.getUsername());
			return "redirect:/passport/login";
			
		}catch (CMSException e) {
			e.printStackTrace();//该异常给程序员看.
			
			model.addAttribute("error", "注册失败:"+e.getMessage());
		} 		
		catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("error", "注册失败,系统出现未知异常.");
		}
		
		return "passport/reg";//如果注册失败.则回到注册页面
		
		
		
	}
	
	
	/**
	 * 去注册
	 * @Title: reg 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@GetMapping("reg")
	public String reg() {
		
		return "passport/reg";
		
	}
	
	/**
	 * 
	 * @Title: login 
	 * @Description: 等录页面
	 * @return
	 * @return: String
	 */
	@GetMapping("login")
	public String login() {
		
		return "passport/login";
		
	}

	

	/**
	 * 
	 * @Title: login 
	 * @Description: 登陆
	 * @return
	 * @return: String
	 */
	@PostMapping("login")
	public String login(User user,Model model,HttpServletRequest request) {
		try {
			
			User user2 = userService.login(user);
			////登录成功
			//1.存入session
			HttpSession session = request.getSession();
		
			
			//2.根据角色 进入的角色页面
			if(CMSContant.ROLE_USER.equals(user2.getRole())) {//普通用户,进入个人中心
				session.setAttribute("user", user2);
				return "redirect:/my";
				
			}
			session.setAttribute("admin", user2);
			return "redirect:/admin";//进入管理员
			
			
		}catch (CMSException e) {
			e.printStackTrace();//该异常给程序员看.
			
			model.addAttribute("error", "登录失败:"+e.getMessage());
		} 		
		catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("error", "登录失败,系统出现未知异常.");
		}
		
		return "passport/login";//如果注册失败.则回到注册页面
		
	}
	/**
	 * 注销用户
	 * @Title: logout 
	 * @Description: TODO
	 * @param request
	 * @return
	 * @return: String
	 */
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/passport/login";
		
	}
}

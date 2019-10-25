package com.yuanshuai.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.github.pagehelper.PageInfo;
import com.yuanshuai.cms.domain.Comment;
import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.service.CommentService;
import com.yuanshuai.cms.util.PageUtil;

@RequestMapping("comment")
@Controller
public class CommentController {
	
	@Resource
	private CommentService commentService;
	
	@GetMapping("selects")
	public String selects(Model model ,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "5")Integer pageSize,HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if(null==session) {
			return "redirect:/passport/login";//没有或登录失效.
		}
		User user = (User) session.getAttribute("user");
		
		PageInfo<Comment> info = commentService.selectsByUserId(user.getId(), page, pageSize);
	   String pages = PageUtil.page(page, info.getPages(), "/comment/selects", pageSize);
		model.addAttribute("comments", info.getList());
		model.addAttribute("pages", pages);
		return "/my/comments";
	}

}

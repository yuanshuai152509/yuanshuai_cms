package com.yuanshuai.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;


import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.service.UserService;
import com.github.pagehelper.PageInfo;

public class UserServiceImplTest extends JunitParent {

	
	@Resource
	private UserService userService ;
	@Test
	public void testSelects() {
		
		PageInfo<User> info = userService.selects(null, 0, 3)	;
		List<User> list = info.getList();
		
		System.out.println(list);
		
	}

	@Test
	public void testInsertSelective() {
	}

	@Test
	public void testSelectByPrimaryKey() {
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

}

package com.yuanshuai.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuanshuai.cms.dao.UserMapper;
import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.service.UserService;
import com.yuanshuai.cms.util.CMSException;
import com.yuanshuai.cms.util.Md5Util;
import com.yuanshuai.cms.vo.UserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;

	@Override
	public PageInfo<User> selects(String name,Integer page, Integer pageSize){
		PageHelper.startPage(page, pageSize);
		List<User> list = userMapper.selects(name);
		return new PageInfo<User>(list);
	}

	@Override
	public int insertSelective(UserVO userVO) {
		
		  //判断用户的注册信息是否符合要求
		 //1.两次密码是否一致
		if(!userVO.getPassword().equals(userVO.getRepassword()))
		throw new CMSException("两次密码输入的不一致!");
		//2.判断注册的用户,是否已经存在
		User user = userMapper.selectByUsername(userVO.getUsername());
		if(null!=user)
			throw new CMSException("注册用户已经存在.不能重复注册!");	
		
		
		//3.对密码进行加密
		String md5Password = Md5Util.md5Encoding(userVO.getPassword());
		
		userVO.setPassword(md5Password);
		
		//4.用户去其他属性进行默认值处理
		userVO.setLocked(0);//不锁定
		userVO.setCreated(new Date());//注册时间
		userVO.setUpdated(new Date());//注册时间
		userVO.setNickname(userVO.getUsername());//用户昵称等于用户名
		return userMapper.insertSelective(userVO);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public User login(User user) {
		//后台登录校验
		//1.user对象不能为空
		if(null==user)
		 throw new CMSException("用户名不能为空!");
		//2用户名或密码都不能空
        if(null==user.getUsername()||null==user.getPassword())
         throw new CMSException("用户名或密码不能为空!");	
        //判断用户是否存在
        User user2 = userMapper.selectByUsername(user.getUsername());
        if(null==user2)
        throw new CMSException("用户名不存在!");	  	
        else {
          if(!user2.getPassword().equals(Md5Util.md5Encoding(user.getPassword())))
          throw new CMSException("密码不正确!");
        }
        if(user2.getLocked()==1)
        	 throw new CMSException("账户被冻结!"); 	
		
		return user2;
	}

}

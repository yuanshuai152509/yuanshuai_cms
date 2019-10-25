package com.yuanshuai.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yuanshuai.cms.domain.User;
/**
 * 
 * @ClassName: UserMapper 
 * @Description: TODO
 * @author: yuanshuai
 * @date: 2019年9月10日 下午2:33:15
 */
public interface UserMapper {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 用户列表查询
	 * @param name
	 * @return
	 * @return: List<User>
	 */
	List<User> selects(@Param("username")String username);
	/**
	 * 
	 * @Title: selectByUsername 
	 * @Description: 根据用户查询用户
	 * @param username
	 * @return
	 * @return: User
	 */
	User selectByUsername(String username);
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
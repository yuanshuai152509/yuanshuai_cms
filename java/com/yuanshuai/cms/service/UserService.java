package com.yuanshuai.cms.service;


import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.vo.UserVO;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: UserService
 * @Description: TODO
 * @author: yuanshuai
 * @date: 2019年9月10日 下午2:57:48
 */
public interface UserService {

	/**
	 * 
	 * @Title: selects
	 * @Description: 用户列表查询
	 * @param name
	 * @return
	 * @return: List<User>
	 */
	PageInfo<User> selects(String name,Integer page, Integer pageSize);

	/**
	 * 
	 * @Title: insertSelective
	 * @Description: 注册用户
	 * @param record
	 * @return
	 * @return: int
	 */
	int insertSelective(UserVO record);

	/**
	 * 
	 * @Title: selectByPrimaryKey
	 * @Description: 单查用户
	 * @param id
	 * @return
	 * @return: User
	 */
	User selectByPrimaryKey(Integer id);

	/**
	 * 
	 * @Title: updateByPrimaryKeySelective
	 * @Description: 修改用户信息
	 * @param record
	 * @return
	 * @return: int
	 */
	int updateByPrimaryKeySelective(User record);
/**
 * 登录
 * @Title: login 
 * @Description: TODO
 * @param user
 * @return
 * @return: User
 */
	User login(User user);

}

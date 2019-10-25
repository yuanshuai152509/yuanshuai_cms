package com.yuanshuai.cms.service;

import com.yuanshuai.cms.domain.Comment;


import com.github.pagehelper.PageInfo;

public interface CommentService {
	/**
	 * 
	 * @Title: selects 
	 * @Description: TODO
	 * @return
	 * @return: List<Comment>
	 */
		PageInfo<Comment> selects(Integer articleId,Integer page ,Integer pageSize);
		/**
		 * 增加评论
		 * @Title: insert 
		 * @Description: TODO
		 * @return
		 * @return: int
		 */
		int insert(Comment comment);//
		
		/**
		 * 
		 * @Title: selectsByUserId 
		 * @Description: TODO
		 * @param 根据用户查询我的评论
		 * @return
		 * @return: List<Comment>
		 */
		PageInfo<Comment> selectsByUserId(Integer userId,Integer page ,Integer pageSize);
}

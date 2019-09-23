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
		PageInfo<Comment> selects(Integer page ,Integer pageSize);
		/**
		 * 增加评论
		 * @Title: insert 
		 * @Description: TODO
		 * @return
		 * @return: int
		 */
		int insert(Comment comment);//
}

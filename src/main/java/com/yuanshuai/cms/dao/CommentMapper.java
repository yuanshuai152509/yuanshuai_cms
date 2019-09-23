package com.yuanshuai.cms.dao;

import java.util.List;

import com.yuanshuai.cms.domain.Comment;
/**
 * 
 * @ClassName: CommentMapper 
 * @Description: 评论
 * @author: charles
 * @date: 2019年9月21日 上午8:30:13
 */
public interface CommentMapper {
/**
 * 
 * @Title: selects 
 * @Description: TODO
 * @return
 * @return: List<Comment>
 */
	List<Comment> selects();
	/**
	 * 增加评论
	 * @Title: insert 
	 * @Description: TODO
	 * @return
	 * @return: int
	 */
	int insert(Comment comment);//
}

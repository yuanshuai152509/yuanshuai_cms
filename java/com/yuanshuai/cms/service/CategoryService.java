package com.yuanshuai.cms.service;

import java.util.List;

import com.yuanshuai.cms.domain.Category;

public interface CategoryService {
	/**
	 * 
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询其下所有分类
	 * @param cid
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectsByChannelId(Integer cid);
}

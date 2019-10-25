package com.yuanshuai.cms.dao;

import java.util.List;

import com.yuanshuai.cms.domain.Category;

public interface CategoryMapper {
	/**
	 * 
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询其下所有分类
	 * @param cid
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectsByChannelId(Integer cid);
	
	
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}
package com.yuanshuai.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuanshuai.cms.dao.CategoryMapper;
import com.yuanshuai.cms.domain.Category;
import com.yuanshuai.cms.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Resource
	private CategoryMapper categoryMapper;
	@Override
	public List<Category> selectsByChannelId(Integer cid) {
		// TODO Auto-generated method stub
		return categoryMapper.selectsByChannelId(cid);
	}

}

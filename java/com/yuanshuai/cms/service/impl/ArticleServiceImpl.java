package com.yuanshuai.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuanshuai.cms.dao.ArticleMapper;
import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper  articleMapper;

	@Override
	public PageInfo<Article> selects(Article article,Integer page,Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Article> list = articleMapper.selects(article);
		return new PageInfo<Article>(list);
	}
	//保存10 个文本文件
	@Override
	public int insertSelective(Article record) {
		return articleMapper.insertSelective(record);
	}

	@Override
	public Article selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Article record) {
		// TODO Auto-generated method stub
		return articleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Article selectPre(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.selectPre(article);
	}

	@Override
	public Article selectNext(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.selectNext(article);
	}
	@Override
	public List<Article> findAll() {
		// TODO Auto-generated method stub
		return articleMapper.findAll();
	}
	
	

}

package com.yuanshuai.cms.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuanshuai.cms.dao.ArticleRepository;
import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class ImportMysqlToEs {
	
	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleRepository articleRepository;
	@Test
	public void testImporttoes() {
		//从MySQL获取文章
		List<Article> articles = articleService.findAll();
		//保存到es
		articleRepository.saveAll(articles);
		
	}
}

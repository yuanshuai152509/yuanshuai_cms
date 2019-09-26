package com.yuanshuai.cms.service.impl;


import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.domain.Category;
import com.yuanshuai.cms.service.ArticleService;
import com.yuanshuai.cms.service.CategoryService;
import com.yuanshuai.common.utils.DateUtil;
import com.yuanshuai.common.utils.RandomUtil;
import com.yuanshuai.common.utils.StreamUtil;


public class ArticleServiceImplTest extends JunitParent {
	@Resource
	private CategoryService categoryService;
	@Resource
	private ArticleService articleService;
	
	@Test
	public void testSelects() {
	}

	
//读取文本插入文章的框
	@Test
	public void testInsertSelective() {
		//读取一个目录下的多个文件
		File file = new File("d:/articles");
		//获取目录下的所有文件
		File[] files = file.listFiles();
		for (File file2 : files) {
			//读取文件内容
			String content = StreamUtil.readTextFile(file2);
			Article article = new Article();
			String filename = file2.getName();//文件名包含后缀
			String title = filename.substring(0, filename.lastIndexOf("."));//把文件的后缀去掉
			article.setTitle(title);//1文章标题
			article.setContent(content);//2文件内容
			//在文本内容中截取前140个字作为摘要
			String summary = content.substring(0, 140);
			article.setSummary(summary);//3文章摘要
			
			//"点击量" 和 "是否热门" 、 "频道" 字段使用随机值
			article.setHits(RandomUtil.random(0, 10000));//点击量
			article.setHot(RandomUtil.random(0, 1));//是否热门
			int channelId = RandomUtil.random(1, 9);
			article.setChannelId(channelId);//栏目
			//根据栏目ID查询对应的分类集合
			List<Category> list = categoryService.selectsByChannelId(channelId);
			//从集合中随机获取一个
			Category category = list.get(RandomUtil.random(0, list.size()-1));
			article.setCategoryId(category.getId());//分类
			Calendar c = Calendar.getInstance();
			c.set(2019, 0, 1, 0, 0, 0);//用2019-01-01 0:0:0初始化日历
			article.setCreated(DateUtil.randomDate(c.getTime(), new Date()));
			//发布人
			article.setUserId(135);
			article.setStatus(1);//已审核
			article.setDeleted(0);//未删除
			articleService.insertSelective(article);//执行插入
			
			
			
			
			
			
		}
		
		
	}

	@Test
	public void testSelectByPrimaryKey() {
	}

	@Test
	public void testUpdateByPrimaryKeyWithBLOBs() {
	}

}

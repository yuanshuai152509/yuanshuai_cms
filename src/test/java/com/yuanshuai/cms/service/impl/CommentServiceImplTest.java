package com.yuanshuai.cms.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.domain.Comment;
import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.service.ArticleService;
import com.yuanshuai.cms.service.CommentService;

import com.github.pagehelper.PageInfo;
import com.yuanshuai.common.utils.DateUtil;
import com.yuanshuai.common.utils.RandomUtil;
import com.yuanshuai.common.utils.StringUtil;

public class CommentServiceImplTest  extends JunitParent{
	
	@Resource
	private CommentService commentService;
	@Resource
	private ArticleService articleService;

	@Test
	public void testInsert() {
		
		//评论人
		User user = new User();
		user.setId(135);
		//评论的文章
		Article a = new Article();
		a.setStatus(1);//只能给审核过的文章评论
		a.setDeleted(0);//给没有删除的文章评论
		//查询出12篇文章
		PageInfo<Article> info = articleService.selects(a, 1, 12);
		List<Article> list = info.getList();
		
	
		
		for(int i =0;i<1000;i++) {
			
			Comment comment = new Comment();
			//1.评论人
			comment.setUser(user);
		
				//随机从list集合中获取文章对象
				int j = RandomUtil.random(0, list.size()-1);
				Article article = list.get(j);
				//2.评论的文章	
			comment.setArticle(article);
			//获取从2019-1-1 00:00:00至今随机日期
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date =null;
			try {
				date = DateUtil.randomDate(s.parse("2019-01-01 00:00:00"), new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//3.评论日期
			comment.setCreated(date);
			//4.评论内容
				//随机产生150个字
				comment.setContent(StringUtil.randomChineseString(150));
			//5执行插入
		commentService.insert(comment);
		}
	}

	@Test
	public void testSelects() {
	}

}

package com.yuanshuai.cms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.service.ArticleService;

//kafka监听消息的类
public class ArticleListener implements MessageListener<String, String>{
	//注入article保存对象
	@Autowired
	ArticleService articleService;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		// TODO Auto-generated method stub
		String jsonString = data.value();
		if(jsonString.startsWith("articleID")) {
			//在消费端获取文章id，再执行数据库加一操作
			String[] split = jsonString.split("=");
			String id = split[1];
			Article article = articleService.selectByPrimaryKey(Integer.parseInt(id));
			article.setHits(article.getHits()+1);
			//更新导数据库
			articleService.updateByPrimaryKeySelective(article);
			
		}else {
			System.out.println(jsonString);
			Article movie = JSON.parseObject(jsonString,Article.class);
			articleService.insertSelective(movie);
		}
	}

}

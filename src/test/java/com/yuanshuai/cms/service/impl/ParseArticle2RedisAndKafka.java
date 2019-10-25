package com.yuanshuai.cms.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.common.utils.DateUtil;
import com.yuanshuai.common.utils.RandomUtil;
import com.yuanshuai.common.utils.StreamUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class ParseArticle2RedisAndKafka {
	//注入redisTemplate
	@Autowired
	RedisTemplate redisTemplate;
	//注入kafka
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Test
	public void readArticle() {
		//声明保存文章保存的路径
		File file = new File("D:/articles");
		//遍历文章所在的目录
		File[] listFiles = file.listFiles();
		//判断这个路径下是否有这些文章
		for (int i = 1; i < listFiles.length; i++) {
			if(listFiles[i].isFile()) {
				//如果是文件，我们就获取文件名
				String fileName = listFiles[i].getName();
				//去掉.txt
				String title = fileName.replace(".txt", "");
				//读取文件的内容作为文章的内容
				String content = StreamUtil.readTextFile(listFiles[i]);
				//创建一个article对象，用来封装数据
				Article article = new Article();
				article.setTitle(title);
				article.setContent(content);
				Calendar instance = Calendar.getInstance();
				instance.set(2019, 0, 0 , 0 ,0);
				article.setCreated(DateUtil.randomDate(instance.getTime(), new Date()));
				article.setHits(RandomUtil.random(0, 1000));
				article.setHot(RandomUtil.random(0, 1));
				article.setDeleted(0);
				
				article.setSummary("堪比好莱坞大片");//3文章摘要
				
				article.setCategoryId(1);
				//设置未审核
				article.setStatus(0);
				article.setUserId(135);
				article.setChannelId(2);
				String jsonString = JSON.toJSONString(article);
				//把每一篇文章保存到redis
				redisTemplate.opsForValue().set("redis_article_"+i, jsonString);
				//从redis获取文章发送kafka
				String redisDB = (String) redisTemplate.opsForValue().get("redis_article_"+i);
				//发送
				System.out.println(redisDB);
				kafkaTemplate.send("articles",redisDB);
			}
			
		
		}
	}
}

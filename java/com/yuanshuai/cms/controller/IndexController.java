package com.yuanshuai.cms.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.domain.Category;
import com.yuanshuai.cms.domain.Channel;
import com.yuanshuai.cms.domain.Slide;
import com.yuanshuai.cms.service.ArticleService;
import com.yuanshuai.cms.service.CategoryService;
import com.yuanshuai.cms.service.ChannelService;
import com.yuanshuai.cms.service.SlideService;
import com.yuanshuai.cms.util.PageUtil;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: IndexController 
 * @Description: cms首页
 * @author: yuanshuai
 * @date: 2019年9月19日 上午8:27:28
 */
@RequestMapping(value = {"index","","/"})
@Controller
public class IndexController {
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private SlideService slideService;
	
	@Resource
	private CategoryService categoryService;
	@Resource
	private ArticleService articleService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	//进入系统首页
	@GetMapping(value = "")
	public String index(Model model,Article article,@RequestParam(defaultValue = "1")Integer page
			,@RequestParam(defaultValue = "5")Integer pageSize) {
		
		//0设置查询条件
		article.setStatus(1);//查询审过的文章
		article.setDeleted(0);//查询没有被删除的文章
		//1查询出所有栏目
			List<Channel> channels = channelService.selects();
			model.addAttribute("channels", channels);
		//2.如果栏目为null 则查询热门文章
			if(article.getChannelId()==null) {
				//广告数据--轮播图
				List<Slide> selects = slideService.selects();
				model.addAttribute("slides", selects);
				article.setHot(1);
				//显示热点文章:专高2的代码
				//0.第一次查询
				//1.先从redis中查询
				List<Article> redisDB = (List<Article>) redisTemplate.opsForValue().get("HotArticles");
				//2.判断从redis中查询的数据是否为null
				if(redisDB!=null&&redisDB.size()>0) {
					System.err.println("从redis中查询了热点文章");
					//3.如果不为空,直接把数据响应给前台
					model.addAttribute("hotArticles",redisDB);
				}else {
				PageInfo<Article> info1 = articleService.selects(article, page, pageSize);
				System.err.println("从mysql中查询了热点文章...");
				redisTemplate.opsForValue().set("HotArticles", info1.getList(), 1, TimeUnit.DAYS);
				//model.addAttribute("hotArticles", info.getList());
				String pages = PageUtil.page(page, info1.getPages(), "/", pageSize);
				model.addAttribute("pages", pages);
				model.addAttribute("hotArticles",info1.getList());
				
			}
			}
				
			//3.如果栏目不为null 则查询栏目下的分类及文章
			if(article.getChannelId()!=null) {
				//查询栏目下所有的分类
				List<Category> categorys= categoryService.selectsByChannelId(article.getChannelId());
				model.addAttribute("categorys", categorys);
				//栏目或分类下 的文章
				PageInfo<Article> info = articleService.selects(article, page, pageSize);
				String pages = PageUtil.page(page, info.getPages(), "/", pageSize);
				model.addAttribute("articles", info.getList());
				model.addAttribute("pages", pages);
				
			}
		    //封装的查询条件
			model.addAttribute("article", article);
		
		
		//3.最新文章.--按照文章发布日期倒序显示最近的10篇文章
			Article article2 = new Article();
			article2.setStatus(1);//只显示审核过的文章
			article2.setDeleted(0);//查询没有被删除的文章
			//PageInfo<Article> info2 = articleService.selects(article2, 1, 10);
			//model.addAttribute("lastArticles", info2.getList());
			List<Article> redisNewArticles = (List<Article>) redisTemplate.opsForValue().get("NewArticles");
			if(redisNewArticles!=null && redisNewArticles.size()>0) {
				//不是null
				System.out.println("从redis中查询的最新文章。。");
				model.addAttribute("lastArticles", redisNewArticles);
			}else {
				System.out.println("从MySQL中查询的最新文章。。");
				//是null，需要从MySQL中查询
				PageInfo<Article> info2 = articleService.selects(article2, 1, 10);
				//保存到redis
				redisTemplate.opsForValue().set("NewArticles", info2.getList());
				model.addAttribute("lastArticles",info2.getList());
			}
			return "index/index";
	}
	

}

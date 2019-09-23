package com.yuanshuai.cms.controller;

import java.util.List;

import javax.annotation.Resource;

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
	
	//进入系统首页
	@GetMapping(value = "")
	public String index(Model model,Article article,@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "5")Integer pageSize) {
		
		//0设置查询条件
		article.setStatus(1);//查询审过的文章
		article.setDeleted(0);//查询没有被删除的文章
		//1查询出所有栏目
			List<Channel> channels = channelService.selects();
			model.addAttribute("channels", channels);
		//2.如果栏目为null 则查询热门文章
			if(article.getChannelId()==null) {
				article.setHot(1);
				PageInfo<Article> info = articleService.selects(article, page, pageSize);
				model.addAttribute("hotArticles", info.getList());
				String pages = PageUtil.page(page, info.getPages(), "/", pageSize);
				model.addAttribute("pages", pages);
				//广告数据--轮播图
				List<Slide> selects = slideService.selects();
				model.addAttribute("slides", selects);
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
			PageInfo<Article> info2 = articleService.selects(article2, 1, 10);
			model.addAttribute("lastArticles", info2.getList());
			
			
			//
			
		return "index/index";
		
	}

}

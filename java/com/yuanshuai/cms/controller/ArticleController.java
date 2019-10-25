package com.yuanshuai.cms.controller;



import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshuai.cms.dao.ArticleRepository;
import com.yuanshuai.cms.domain.Article;
import com.yuanshuai.cms.domain.Comment;
import com.yuanshuai.cms.domain.User;
import com.yuanshuai.cms.service.ArticleService;
import com.yuanshuai.cms.service.CommentService;
import com.yuanshuai.cms.util.ESUtils;
import com.yuanshuai.cms.util.PageUtil;
import com.github.pagehelper.PageInfo;

@RequestMapping("article")
@Controller
public class ArticleController {

	@Resource
	private ArticleService articleService;
	
	@Resource
	private CommentService commentService;
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@RequestMapping("search")
	public String search(String key , Model model,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "5")Integer pageSize) {
		//显示搜索花费的时间
		long start = System.currentTimeMillis();
		//es普通的搜索显示
//		List<Article> articles = articleRepository.findByTitle(key);
		//es高亮显示
		AggregatedPage<Article> selectObjects = (AggregatedPage<Article>) ESUtils.selectObjects(elasticsearchTemplate, Article.class, page, pageSize, new String[] {"title"}, key);
		List<Article> articles = selectObjects.getContent();
		long end = System.currentTimeMillis();
		System.err.println("本次搜索耗时:"+(end-start)+"毫秒");
		String pages = PageUtil.page(page, (int)selectObjects.getTotalElements(), "/article/search?key="+key, pageSize);
		model.addAttribute("pages",pages);
		model.addAttribute("hotArticles", articles);
		model.addAttribute("key",key);
		return "index/index";
	}
	
	
	/**
	 * 添加评论
	 * @Title: comment 
	 * @Description: TODO
	 * @param comment
	 * @param request
	 * @return
	 * @return: boolean
	 */
	
	@ResponseBody
	@PostMapping("comment")
	public  boolean comment(Comment comment,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(null ==session)
			return false;
		//评论人
		comment.setUser((User)session.getAttribute("user"));
		comment.setCreated(new Date());
		return commentService.insert(comment)>0;
			
	}
	
	public void SelectCommentServiceById(Model model, @RequestParam(defaultValue = "")String name,
			@RequestParam(defaultValue = "")String pageNum
			) {
		model.addAttribute("name",name);
		model.addAttribute("pageNum",pageNum);
		
	}
	
	//上一篇、下一篇文章
	/**
	 *  检查是否有上一篇
	 * @Title: checkPre 
	 * @Description: TODO
	 * @param article
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@GetMapping("checkPre")
	public boolean checkPre(Article article) {
		Article pre = articleService.selectPre(article);
		return pre!=null;
	}
	/**
	 *  检查是否有下一篇
	 * @Title: checkPre 
	 * @Description: TODO
	 * @param article
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@GetMapping("checkNext")
	public boolean checkNext(Article article) {
		Article pre = articleService.selectNext(article);
		return pre!=null;
	}
	/**
	 * 
	 * @Title: selectPre 
	 * @Description: 上一篇
	 * @param model
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
		@GetMapping("selectPre")
		public String selectPre(Model model,Article article, @RequestParam(defaultValue = "1") Integer page,
				@RequestParam(defaultValue = "10") Integer pageSize) {
			//去查询上一篇的内容
			Article pre = articleService.selectPre(article);

			// 评论
			PageInfo<Comment> info = commentService.selects(pre.getId(), page, pageSize);
			String pages = PageUtil.page(page, info.getPages(), "/article/selectByUser", pageSize);

			model.addAttribute("article", pre);//把上一篇内容放model
			model.addAttribute("pages", pages);
			model.addAttribute("comments", info.getList());
			return "my/article";
		}
		
		/**
		 * 
		 * @Title: selectPre 
		 * @Description: 上一篇
		 * @param model
		 * @param article
		 * @param page
		 * @param pageSize
		 * @return
		 * @return: String
		 */
			@GetMapping("selectNext")
			public String selectNext(Model model,Article article, @RequestParam(defaultValue = "1") Integer page,
					@RequestParam(defaultValue = "10") Integer pageSize) {
				//去查询上一篇的内容
				Article next = articleService.selectNext(article);

				// 评论
				PageInfo<Comment> info = commentService.selects(next.getId(), page, pageSize);
				String pages = PageUtil.page(page, info.getPages(), "/article/selectByUser", pageSize);

				model.addAttribute("article", next);//把上一篇内容放model
				model.addAttribute("pages", pages);
				model.addAttribute("comments", info.getList());
				return "my/article";
			}
	
	/**
	 * 
	 * @Title: select 
	 * @Description: 个人查看单个文章
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	//注入线程池
	@Autowired
	ThreadPoolTaskExecutor executor;
	@GetMapping("selectByUser")
	public String  selectByUser(Integer id,Model model,
			@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "10")Integer pageSize,HttpServletRequest request) {
		Article article = articleService.selectByPrimaryKey(id);//根据id查询文章
//		当用户浏览文章时，将“Hits_${文章ID}_${用户IP地址}”为key，
//		查询Redis里有没有该key，
//		如果有key，则不做任何操作
//		。如果没有，则使用Spring线程池异步执行数据库加1操作
//		，并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，而且有效时长为5分钟。
		//1拼接key  **A
/**		String ip = request.getRemoteAddr();
		String redisKey = "hits_"+id +"_"+ip;
		//2查询redis里有没有该key（先注入redistemplate）
		 String key = (String) redisTemplate.opsForValue().get(redisKey);
		//3如果有key不做任何操作
		//4。如果没有，则使用Spring线程池异步执行数据库加1操作 /在spring
		 if(key==null) {
			 executor.execute(new Runnable() {
				//从线程池中获取一个线程，异步执行
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//获取文章点击量
					Integer hits = article.getHits();
					//点击量+1
					article.setHits(hits+1);
					System.out.println(redisKey+"保存到redis");
					//保存到数据库
					articleService.updateByPrimaryKeySelective(article);
					//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，而且有效时长为5分钟。
					redisTemplate.opsForValue().set(redisKey, "",5,TimeUnit.MINUTES);
				}
			});
		 }
		 
		 */
		//**B
		//当用户浏览文章时，往Kafka发送文章ID，在消费端获取文章ID，再执行数据库加1操作。
		//生产者，把生产者的配置文件加载过来 。 spring-beans加载此配置文件
		kafkaTemplate.send("articles","articleID="+id+"");
		
		
		//评论
		PageInfo<Comment> info = commentService.selects(article.getId(), page, pageSize);
	
		String pages = PageUtil.page(page, info.getPages(), "/article/selectByUser", pageSize);
		
		
		model.addAttribute("article", article);
		model.addAttribute("pages", pages);
		model.addAttribute("comments", info.getList());
		return "my/article";
		
	}
	
	/**
	 * 
	 * @Title: selectsByUser 
	 * @Description: 个人中心查看文章
	 * @param model
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@GetMapping("selectsByUser")
	public String selectsByUser(Model model,Article article ,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "3")Integer pageSize,HttpServletRequest request) {
		
		//个人中心.只能查看自己的文章
			HttpSession session = request.getSession(false);
			if(session==null)
				return "redirect:/passport/login";//session失效,重新登录
			
			User user = (User) session.getAttribute("user");
			//只能查看自己的文章
			article.setUserId(user.getId());
			
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
	    String pages = PageUtil.page(page, info.getPages(), "/article/selectsByUser?title="+article.getTitle(), pageSize);
	    model.addAttribute("articles", info.getList());
	    model.addAttribute("pages", pages);
	    model.addAttribute("article", article);
	    
		
		return "my/articles";
		
	}
	
	
	
	/**
	 * 发布文章
	 * @Title: publish 
	 * @Description: TODO
	 * @param artice
	 * @param file
	 * @return
	 * @return: boolean
	 */
	
	@Value("${filePath}")
	private String filePath;//文件上传路径
	
	
	@ResponseBody
	@PostMapping("publish")
	public boolean publish(Article artice,MultipartFile file,HttpServletRequest request) {
		
	    //1.上传文章标题图片
		if(!file.isEmpty()) {
		  	
			//获取原始上传文件的名称//a.jpg
			String originalFilename = file.getOriginalFilename();	
			//为了防止图片名称重复.使用UUID 统一处理上传的名称名称
			String newFilename =UUID.randomUUID()+ originalFilename.substring(originalFilename.lastIndexOf("."));
			//文件上传路径
			//String path="d:/pic/";
			File file2 = new File(filePath + newFilename);
		
			try {
				//把文件写入硬盘
				file.transferTo(file2);
	
				
				//封装标题图片的地址
				artice.setPicture(newFilename);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		//2.把文章内容保存到数据库
		//默认文章的基本属性
		//文章在发送的时候，实时的显示在前台页面，保证redis和MySQL中的数据一致
		//只要牵扯到redis存取数据的，为了保证数据的实时性只要牵着到对数据增删改查的操作，必须把redis中的数据清空
		//从而就可以达到数据同步效果
		redisTemplate.delete("NewArticles");
		artice.setHot(0);//文章为非热门
		artice.setStatus(0);//文章为待审核
		artice.setHits(0);//文章点击量默认为0
		artice.setDeleted(0);//文章删除状态0
		artice.setCreated(new Date());//文章发布时间
		artice.setUpdated(new Date());//文章修改时间
		//发布人
		//false:从request获取session.对象,如果没有则返回null.如果有则返回session.. 
		HttpSession session = request.getSession(false);
		if(session!=null) {
			User user = (User) session.getAttribute("user");
		artice.setUserId(user.getId());//发布人
		}else {
			return false;//没有登录.不能发布
		}
		return	articleService.insertSelective(artice)>0;
		
	}
	
	/**
	 *  去发布文章页面
	 * @Title: publish 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@GetMapping("publish")
	public String publish() {
		
		return "my/publish";
	}
	
	
	/**
	 * 
	 * @Title: selects 
	 * @Description: 管理员查看文章
	 * @param model
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@RequestMapping("selectsByAdmin")
	public String selects(Model model,Article article ,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "3")Integer pageSize) {
		
		if(article.getStatus()==null||article.getStatus().equals(""))
		  article.setStatus(0);//默认待审核状态
		
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
	    String pages = PageUtil.page(page, info.getPages(), "/article/selectsByAdmin?status="+article.getStatus(), pageSize);
	    model.addAttribute("articles", info.getList());
	    model.addAttribute("pages", pages);
	    model.addAttribute("article", article);
	    
		
		return "admin/articles";
		
	}
	/**
	 * 
	 * @Title: select 
	 * @Description: 管理员查看单个文章
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@GetMapping("selectByAdmin")
	public String  select(Integer id,Model model) {
		Article article = articleService.selectByPrimaryKey(id);
		
		model.addAttribute("article", article);
		return "admin/article";
		
	}
	/**
	 * 去文章修改页面 。。                            
	 * @Title: update 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@GetMapping("update")
	public String update(Integer id,Model model) {
		Article article = articleService.selectByPrimaryKey(id);
		
		model.addAttribute("article", article);
		return "/my/articleupdate";
	}
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新文章--审核文章,删除文章
	 * @param article
	 * @return
	 * @return: boolean
	 */
	@Autowired
	ArticleRepository articleRepository;
	@ResponseBody
	@PostMapping("update")
	public boolean update(Article article) {
		int isSuccess = articleService.updateByPrimaryKeySelective(article);
		//在审核之前，保存此文章到es索引库
		Article article1 = articleService.selectByPrimaryKey(article.getId());
		articleRepository.save(article1);
		//修改文章的审核状态
		return isSuccess >0;
		
		
		//return articleService.updateByPrimaryKeySelective(article)>0;
	}
	
	@ResponseBody
	@PostMapping("publishupdate")
	public boolean publishupdate(Article artice,MultipartFile file,HttpServletRequest request) {
		
	    //1.上传文章标题图片
		if(!file.isEmpty()) {
		  	
			//获取原始上传文件的名称//a.jpg
			String originalFilename = file.getOriginalFilename();	
			//为了防止图片名称重复.使用UUID 统一处理上传的名称名称
			String newFilename =UUID.randomUUID()+ originalFilename.substring(originalFilename.lastIndexOf("."));
			//文件上传路径
			//String path="d:/pic/";
			File file2 = new File(filePath + newFilename);
		
			try {
				//把文件写入硬盘
				file.transferTo(file2);
				
				//封装标题图片的地址
				artice.setPicture(newFilename);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		//2.把文章内容保存到数据库
		//默认文章的基本属性
		artice.setHot(0);//文章为非热门
		artice.setStatus(0);//文章为待审核
		artice.setHits(0);//文章点击量默认为0
		artice.setDeleted(0);//文章删除状态0
		artice.setCreated(new Date());//文章发布时间
		artice.setUpdated(new Date());//文章修改时间
		//发布人
		//false:从request获取session.对象,如果没有则返回null.如果有则返回session.. 
		HttpSession session = request.getSession(false);
		if(session!=null) {
			User user = (User) session.getAttribute("user");
		artice.setUserId(user.getId());//发布人
		}else {
			return false;//没有登录.不能发布
		}
		return	articleService.updateByPrimaryKeySelective(artice)>0;
		
	}
	
	
	
}

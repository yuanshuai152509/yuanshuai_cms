package com.yuanshuai.cms.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanshuai.cms.domain.Channel;
import com.yuanshuai.cms.service.ChannelService;
/**
 * 栏目controller
 * @ClassName: ChannelController 
 * @Description: TODO
 * @author: yuanshuai
 * @date: 2019年9月17日 上午9:30:10
 */
@RequestMapping("channel")
@Controller
public class ChannelController {
	@Resource
	private ChannelService channelService;
	
	@ResponseBody
	@GetMapping("selects")
	public List<Channel> selects(){
		return channelService.selects();	
	}

}

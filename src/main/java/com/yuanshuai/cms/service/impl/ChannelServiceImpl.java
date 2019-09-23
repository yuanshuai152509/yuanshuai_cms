package com.yuanshuai.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuanshuai.cms.dao.ChannelMapper;
import com.yuanshuai.cms.domain.Channel;
import com.yuanshuai.cms.service.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelMapper channelMapper ;

	@Override
	public List<Channel> selects() {
		return channelMapper.selects();
	}

}

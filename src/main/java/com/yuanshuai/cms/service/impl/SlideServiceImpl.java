package com.yuanshuai.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuanshuai.cms.dao.SlideMapper;
import com.yuanshuai.cms.domain.Slide;
import com.yuanshuai.cms.service.SlideService;

@Service
public class SlideServiceImpl implements  SlideService {
	@Resource
	private SlideMapper slideMapper;

	

	@Override
	public List<Slide> selects() {
		// TODO Auto-generated method stub
		return slideMapper.selects();
	}

}

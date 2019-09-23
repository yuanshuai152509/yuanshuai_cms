package com.yuanshuai.cms.dao;

import java.util.List;

import com.yuanshuai.cms.domain.Channel;
/**
 * 
 * @ClassName: ChannelMapper 
 * @Description: 栏目
 * @author: yuanshuai
 * @date: 2019年9月17日 上午9:20:38
 */
public interface ChannelMapper {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 所有的栏目
	 * @return
	 * @return: List<Channel>
	 */
	List<Channel> selects();
	
    int deleteByPrimaryKey(Integer id);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
}
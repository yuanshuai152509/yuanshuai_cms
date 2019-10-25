package com.yuanshuai.cms.util;
/**
 * 
 * @ClassName: CMSException 
 * @Description: CMS的自定义异常
 * @author: yuanshuai
 * @date: 2019年9月18日 下午1:51:41
 */
public class CMSException extends RuntimeException {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	
	public CMSException() {}

	public CMSException(String message) {
		super(message);
	}

}

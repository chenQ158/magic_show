package com.qc.magic.utils;

import java.util.UUID;

/**
 * 存放静态常量变量
 * @author john
 *
 */
public class Constant {
	public static final String MAGIC = "afsd6f4sa1df6asd4f65s4dfa";
	
	public static final String WEBSITE_PREFIX = "http://127.0.0.1:8080";
	
	//这个应该固定不变的
	public static final String MAGIC_KEY = UUID.fromString("magic").toString().replaceAll("-", "");
	
	//邮件发送方名称
	public static final String EMAIL_SENDER_NAME="清城工作室";
	
	//激活邮件的激活链接
	public static final String ACTIVETE_URL = "http://127.0.0.1:8080/doActivated?id=%d&code=%s&magic="+MAGIC; 
}

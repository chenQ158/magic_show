package com.qc.magic.utils;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

/**
 * 邮件发送工具
 * @author john
 *
 */
@Service
public class EmailUtils implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private FreeMarkerConfigurer templateEngine;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		mailSender = new JavaMailSenderImpl();

        // 请输入自己的邮箱和密码，用于发送邮件
        mailSender.setUsername("chenq158@qq.com");
        mailSender.setPassword("izsdamkzzfbahbec");
        mailSender.setHost("smtp.qq.com");
        // 请配置自己的邮箱和密码

        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
	}
	
	public boolean sendWithHtmlTemplate(String to, String subject,
										String templateName, Map<String, Object> model) {
		try {
			String nick = MimeUtility.encodeText("清城工作室");
			InternetAddress from = new InternetAddress(nick + "<chenq158@qq.com>");
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			// 通过模板获取内容
			Template template = templateEngine.getConfiguration().getTemplate(templateName, Locale.SIMPLIFIED_CHINESE, "utf-8");
			String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(result, true);
			
			mailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
			return false;
		}
	}
}

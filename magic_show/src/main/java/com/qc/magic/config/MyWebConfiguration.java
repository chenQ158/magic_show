package com.qc.magic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.qc.magic.interceptor.LoginedInterceptor;
import com.qc.magic.interceptor.PassportInterceptor;

@Component
@Configuration
public class MyWebConfiguration implements WebMvcConfigurer {

	@Autowired
	private PassportInterceptor passportInterceptor;
	@Autowired
	private LoginedInterceptor loginedInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(passportInterceptor);
		registry.addInterceptor(loginedInterceptor).addPathPatterns("/profile/**");
	}
}

package com.qc.magic.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qc.magic.model.User;
import com.qc.magic.model.Vo.HostHolder;
import com.qc.magic.service.UserService;
import com.qc.magic.utils.MapCache;

/**
 * 通行拦截器
 * @author john
 *
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

	final static String MAGIC_KEY="magic";
	
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String referer = request.getHeader("Referer");
		if (referer != null && request.getRequestURI().contains("/regLogin")) {
			request.setAttribute("next", referer);
		}
		
		// 先判断cookie是否有magic
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) return true;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(MAGIC_KEY)) {
				String key = cookie.getValue();
				MapCache mapCache = MapCache.single();
				if (mapCache == null) return true;
				// 通过magic获取缓存中的user
				// 判断user是否过期
				String json = mapCache.hget("USER", key);
				if (null == json) return true;
				User user = JSONObject.parseObject(json, User.class);
				if (user == null) return true;
				// 判断user是否存在
				User sear_user = userService.getUserById( user.getId());
				if (user.equals( sear_user ) ) {
					hostHolder.set(user);
					break;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null && hostHolder.getUser() != null) {
			modelAndView.addObject("current_user", hostHolder.getUser());
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		hostHolder.clear();
	}
	
}

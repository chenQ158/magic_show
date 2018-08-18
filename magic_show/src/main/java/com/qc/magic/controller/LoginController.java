package com.qc.magic.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.UserService;
import com.qc.magic.utils.Constant;
import com.qc.magic.utils.MapCache;

/**
 * 登陆注册Controller
 * @author john
 *
 */
@Controller
public class LoginController {
	
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping("/regLogin")
	public String regLogin(@RequestParam(value="next", required=false) String next,
						   @RequestParam(value="msg", required=false) String msg,
						   Model model) {
		if (StringUtils.isNotBlank(next)) model.addAttribute("next", next);
		if (StringUtils.isNotBlank(msg)) model.addAttribute("msg", msg);
		return "login";
	}
	
	@RequestMapping(value="/login", method= {RequestMethod.POST})
	public String login(@RequestParam("email") String email,
						@RequestParam("password") String password,
						@RequestParam(value="remember", required=false) String rememberme,
						@RequestParam(value="next", required=false) String next,
						HttpServletResponse response,
						RedirectAttributes attrs) {

		// 对email和password进行校验和登陆
		ViewObject vo = userService.login(email, password, rememberme);
		// 如果登陆成功，就会包含相当于sessionId的magic
		if (vo.get("magic") != null) {
			// 存入cookie
			Cookie cookie = new Cookie("magic", (String) vo.get("magic"));
			cookie.setPath("/");
			if (StringUtils.isNotBlank(rememberme)) {
				// 记住密码，默认时间为7天
				cookie.setMaxAge(3600*24*7);
			}
			response.addCookie(cookie);
			System.out.println("cookie:magic:"+vo.get("magic"));
			
			// 如果有next,则判断是否是站内地址
			if (StringUtils.isNotBlank(next)
					&& (next.startsWith(Constant.WEBSITE_PREFIX) 
							|| next.startsWith("/"))) {
				next = next.replace(Constant.WEBSITE_PREFIX, "");
				return "redirect:"+next;
			} else {
				return "redirect:/";
			}
		} else {
			if (StringUtils.isNotBlank(next)
					&& (next.startsWith(Constant.WEBSITE_PREFIX) 
							|| next.startsWith("/"))) {
				attrs.addAttribute("next", next);
			}
			// 登陆失败，返回错误信息
			attrs.addAllAttributes(vo.getMap());
			return "redirect:/regLogin";
		}
	}
	
	
	@RequestMapping(value="/reg", method= {RequestMethod.POST})
	public String register(@RequestParam("email") String email,
						   @RequestParam("password") String password,
						   @RequestParam(value="next", required=false) String next,
						   HttpServletResponse response,
						   HttpServletRequest request,
						   RedirectAttributes attrs) {
		try {
			// 对email和password进行校验和注册
			ViewObject vo = userService.register(email, password);
			// 判断是否注册成功
			if (vo.get("magic") != null) {
				// 存入cookie
				Cookie cookie = new Cookie("magic", (String) vo.get("magic"));
				cookie.setPath("/");
				response.addCookie(cookie);
				
				// 如果有next,则判断是否是站内地址
				if (StringUtils.isNotBlank(next) 
						&& (next.startsWith(Constant.WEBSITE_PREFIX) 
								|| next.startsWith("/"))) {
					next = next.replace(Constant.WEBSITE_PREFIX, "");
					return "redirect:"+next;
				} else {
					return "redirect:/";
				}
			} else {
				if (StringUtils.isNotBlank(next)
						&& (next.startsWith(Constant.WEBSITE_PREFIX) 
								|| next.startsWith("/"))) {
					attrs.addAttribute("next", next);
				}
				attrs.addAllAttributes(vo.getMap());
				return "redirect:/regLogin";
			}
		} catch (Exception e) {
			logger.error("注册异常"+e);
			attrs.addAllAttributes(ViewObject.valueOf(500, "注册页面", request.getHeader("Referer")));
			return "redirect:/error";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(@CookieValue(value="magic", required=false) String magic,
			HttpServletResponse response) {
		magic = magic == null ? "":magic;
		Cookie cookie = new Cookie("magic", magic);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		// 清空缓存
		MapCache.single().clear();
		return "/";
	}
}

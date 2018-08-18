package com.qc.magic.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.qc.magic.model.Image;
import com.qc.magic.model.User;
import com.qc.magic.model.Vo.HostHolder;
import com.qc.magic.model.Vo.PageEntity;
import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.CommentService;
import com.qc.magic.service.ImageService;
import com.qc.magic.service.UserService;

/**
 * 用户设置Controller
 * @author john
 *
 */
@Controller
public class ProfileController {

	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CommentService commentService;
	
	static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	/**
	 * 跳转到用户主页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/profile/{id}")
	public String profile(@PathVariable Integer id,
						  Model model,
						  HttpServletRequest request,
						  RedirectAttributes attrs) {
		try {
			// 先判断是否是用户本身，如果是则直接取
			User user = hostHolder.getUser();
			if (user != null && user.getId().equals(id)) {
				
			} else {
				user = userService.getUserById(id);
			}
			model.addAttribute("user", user);
			// 将图片及分页信息放入
			PageEntity<Image> imagesPage = imageService.getImagesPage(user.getId(), 1, 3);
			model.addAttribute("images", imagesPage.getCur_list());
			model.addAllAttributes(imagesPage.getPageDetails());
			return "profile";
		} catch (Exception e) {
			logger.error("用户主页异常: " + e);
			
			attrs.addAllAttributes(ViewObject.valueOf(500, "用户主页", request.getHeader("Referer")));
			return "redirect:/error";
		}
	}
	
	/**
	 * 获取用户分页图片
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/profile/images/{userId}/{page}/{size}")
	@ResponseBody
	public String getImagesPage(@PathVariable Integer userId,
								@PathVariable Integer page,
								@PathVariable Integer size) {
		try {
			PageEntity<Image> pageEntity = imageService.getImagesPage(userId, page, size);
			ViewObject vo = new ViewObject();
			// 是否有下页
			vo.set("has_next", pageEntity.getHasNext());
			
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Image image : pageEntity.getCur_list()) {
				ViewObject imageVo = new ViewObject();
				int count = commentService.getCommentCount(image.getId());
				imageVo.set("comment_count", count);
				imageVo.set("id", image.getId());
				imageVo.set("url", image.getUrl());
				mapList.add(imageVo.getMap());
			}
			vo.set("images", mapList);
			return JSONObject.toJSONString(vo.getMap());
		} catch (Exception e) {
			logger.error("获取图片分页异常"+e);
			ViewObject vo = new ViewObject();
			vo.set("code", 1);
			vo.set("msg", "请求错误");
			return JSONObject.toJSONString(vo);
		}
	}
	
}

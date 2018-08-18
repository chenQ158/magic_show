package com.qc.magic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.qc.magic.model.Image;
import com.qc.magic.model.Vo.PageEntity;
import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.CommentService;
import com.qc.magic.service.ImageService;
import com.qc.magic.service.UserService;
import com.qc.magic.utils.DateUtils;

@Controller
public class HomeController {
	
	static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private ImageService imageService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;

	@RequestMapping({"/", "/index"})
	public String index(Model model,
						HttpServletRequest request,
						RedirectAttributes attrs,
						@RequestParam(value="offset", defaultValue="1") Integer offset,
						@RequestParam(value="limit", defaultValue="10") Integer limit) {
		try {
			// 传入最新的10张图片
			PageEntity<Image> imageList = null;
			if (offset == 1 && limit == 10) {
				imageList = imageService.getLatestImages();
			} else {
				imageList = imageService.getImagesPage(false, offset, limit);
			}
			// 每张图片的用户
			// 每张图片的评论及评论用户
			List<ViewObject> imageVos = new ArrayList<>();
			for (Image image : imageList.getCur_list()) {
				ViewObject obj = new ViewObject();
				obj.set("image", image);
				obj.set("user", userService.getUserById(image.getUserId()));
				obj.set("commentVos", commentService.getCommentAndOwner(image.getId()));
				obj.set("time", DateUtils.getStringOfFromDateToNow(image.getCreatedDate()));
				imageVos.add(obj);
			}
			model.addAttribute("imageVos", imageVos);
			model.addAllAttributes(imageList.getPageDetails());
			return "index";
		} catch (Exception e) {
			logger.error("首页异常: " + e);
			attrs.addAllAttributes(ViewObject.valueOf(500, "首页", request.getHeader("Referer")));
			return "rediect:/error";
		}
	}
	
	
	@RequestMapping("/page/{offset}/{limit}")
	@ResponseBody
	public String page(@PathVariable Integer offset, @PathVariable Integer limit) {
		ViewObject model = new ViewObject();
		try {
			PageEntity<Image> imageList = imageService.getImagesPage(false, offset, limit);
			List<ViewObject> imageVos = new ArrayList<>();
			for (Image image : imageList.getCur_list()) {
				ViewObject obj = new ViewObject();
				obj.set("image", image);
				obj.set("user", userService.getUserById(image.getUserId()));
				obj.set("commentVos", commentService.getCommentAndOwner(image.getId()));
				obj.set("time", DateUtils.getStringOfFromDateToNow(image.getCreatedDate()));
				imageVos.add(obj);
			}
			model.set("code", 0);
			model.set("imageVos", imageVos);
			//model.addAll(imageList.getPageDetails());
		} catch (Exception e) {
			logger.error("获取图片信息异常"+e);
			model.set("code", 1);
			model.set("msg", "获取信息失败");
		}
		return JSONObject.toJSONString(model.getMap());
	}
}

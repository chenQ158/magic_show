package com.qc.magic.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * 详情Controller
 * @author john
 *
 */
@Controller
public class DetailsController {

	@Autowired
	private ImageService imageService;
	@Autowired
	private UserService userService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private CommentService commentService;
	
	static final Logger logger = LoggerFactory.getLogger(DetailsController.class);
	
	/**
	 * 跳转到图片详情页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/details/{id}")
	public String toDetails(@PathVariable Integer id,
							RedirectAttributes attrs,
							HttpServletRequest request,
							Model model) {
		try {
			Image image = imageService.getImageById(id);
			if (null == image) throw new IllegalArgumentException("图片不存在");
			User user = userService.getUserById(image.getUserId());
			PageEntity<ViewObject> pageEntity = commentService.getCommentAndOwner(id, 1, 8);
			model.addAttribute("image", image);
			model.addAttribute("user", user);
			model.addAttribute("commentVos", pageEntity.getCur_list());
			model.addAllAttributes(pageEntity.getPageDetails());
			
			return "pageDetail";
		} catch (Exception e) {
			logger.error("图片详情异常: " + e);
			attrs.addAllAttributes(ViewObject.valueOf(500, "图片详情页面", request.getHeader("Referer")));
			return "rediect:/error?code=500";
		}
	}
	
	/**
	 * 添加评论
	 * @param content
	 * @param imageId
	 * @return
	 */
	@RequestMapping(value="/addComment", method= {RequestMethod.POST})
	@ResponseBody
	public String addComment(@RequestParam("content") String content,
							 @RequestParam("imageId") Integer imageId) {
		ViewObject vo = new ViewObject();
		try {
			User user = hostHolder.getUser();
			if (user == null) {//先检查是否登陆
				vo.set("code", 1);
				vo.set("msg", "请先登录之后再评论");
				return JSONObject.toJSONString(vo.getMap());
			}
			// 检验并添加评论
			vo = commentService.addComment(content, imageId, user.getId());
			vo.set("nickname", user.getNickname());
			vo.set("userId", user.getId());
			int commentCount = (int) vo.get("commentCount");
			
			// 更新评论数
			imageService.updateImage(imageId, 0, 0, commentCount+1);
			
			return JSONObject.toJSONString(vo.getMap());
		} catch (Exception e) {
			logger.error("添加评论异常"+e);
			vo.set("code", 1);
			vo.set("msg", "添加评论异常");
			return JSONObject.toJSONString(vo.getMap());
		}
	}
	
	/**
	 * 删除评论
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value="/deleteComment", method= {RequestMethod.POST})
	@ResponseBody
	public String deleteComment(@RequestParam("commentId") Integer commentId) {
		ViewObject vo = new ViewObject();
		if (commentId == null) {
			vo.set("code", 1);
			vo.set("msg", "评论不存在");
		} 
		try {
			commentService.deleteComment(commentId);
			vo.set("code", 0);
			vo.set("msg", "删除成功");
			
			// 更新评论数
			// imageService.updateImage(imageId, 0, 0, commentCount+1);
		} catch (Exception e) {
			vo.set("code", 1);
			vo.set("msg", "删除评论失败");
			logger.error("删除评论异常"+e);
		}
		return JSONObject.toJSONString(vo.getMap());
	}
	
	
	@RequestMapping("/comments/{imageId}/{page}/{limit}")
	@ResponseBody
	public String getCommentsPage(@PathVariable Integer imageId,
								  @PathVariable Integer page,
								  @PathVariable Integer limit) {
		PageEntity<ViewObject> pageEntity = commentService.getCommentAndOwner(imageId, page, limit);
		ViewObject vo = new ViewObject();
		vo.set("comments", pageEntity.getCur_list());
		//vo.addAll(pageEntity.getPageDetails());
		vo.set("code", 0);
		return JSONObject.toJSONString(vo.getMap());
	}
}

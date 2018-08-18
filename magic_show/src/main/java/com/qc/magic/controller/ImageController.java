package com.qc.magic.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qc.magic.model.User;
import com.qc.magic.model.Vo.HostHolder;
import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.ImageService;
import com.qc.magic.service.UploadService;
import com.qc.magic.utils.FileUtils;
import com.qc.magic.utils.ImageUtils;

@Controller
public class ImageController {

	static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	private UploadService uploadService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private HostHolder hostHolder;
	
	@RequestMapping(value="/images/{id}")
	@ResponseBody
	public void showImage(@PathVariable Integer id,
						  HttpServletResponse response) {
		String filename = FileUtils.DEST_DIR + id +".jpg";
		try {
			response.setContentType("image/jpeg");
			FileCopyUtils.copy(new FileInputStream(filename), response.getOutputStream());
		} catch (FileNotFoundException e) {
			logger.error("文件未找到", e);
			response.setStatus(404);
		} catch (IOException e) {
			logger.error("IO异常", e);
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value="/images/upload", method={RequestMethod.POST})
	public String uploadImage(@RequestParam("file") MultipartFile[] files,
							  HttpServletRequest request,
							  RedirectAttributes attrs) {
		try {
			User user = hostHolder.getUser();
			String[] urls = uploadService.upload(files);
			imageService.addImages(user.getId(), urls);
			
			return "redirect:/profile/"+user.getId();
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			attrs.addAllAttributes(ViewObject.valueOf(500, "图片上传页面", request.getHeader("Referer")));
			return "redirect:/error";
		}
	}
	
	@RequestMapping("/images/uploaded/{name}")
	@ResponseBody
	public void getUploadedImage(@PathVariable String name,
			  					 HttpServletResponse response) {
		String filename = ImageUtils.UPLOAD_FILE_DIR + name;
		try {
			response.setContentType("image/jpeg");
			FileCopyUtils.copy(new FileInputStream(filename), response.getOutputStream());
		} catch (FileNotFoundException e) {
			logger.error("文件未找到", e);
			response.setStatus(404);
		} catch (IOException e) {
			logger.error("IO异常", e);
			response.setStatus(500);
		}
	}
}

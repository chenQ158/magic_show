package com.qc.magic.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {

	final static String BASE_URL="http://127.0.0.1:8080/images/";
	final static String[] IMAGE_EXTS = {".jpg",".png",".gif",".tmp",".jpeg"};
	public final static String BASE_UPLOADED_URL="http://127.0.0.1:8080/images/uploaded/";
	public final static String UPLOAD_FILE_DIR="D://upload//";
	
	public static String getRandomUrl() {
		int num = new Random().nextInt(1000);
		return String.format("%s%d", BASE_URL, num);
	}
	
	public static String hasExtName(MultipartFile file) {
		String oriName = file.getOriginalFilename();
		String extName = ContainsExt(oriName);
		if (extName == null) {
			return null;
		}
		// 后续添加一些其他的判断
		
		return extName;
	}
	
	public static String saveFileToLocal(MultipartFile file, String extName) throws FileNotFoundException, IOException {
		String filename = UUID.randomUUID().toString().replaceAll("-", "")+extName;
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(UPLOAD_FILE_DIR+filename));
		return filename;
	}
	
	static String ContainsExt(String name) {
		for (String str : IMAGE_EXTS) {
			if (name.endsWith(str)) {
				return str;
			}
		}
		return null;
	}
	
	
}

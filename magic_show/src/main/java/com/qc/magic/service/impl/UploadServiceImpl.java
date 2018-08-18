package com.qc.magic.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qc.magic.service.UploadService;
import com.qc.magic.utils.ImageUtils;

@Service
public class UploadServiceImpl implements UploadService {

	final static Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
	
	@Override
	public String[] upload(MultipartFile[] files) {
		if (files == null || files.length <= 0) {//或者抛出异常
			return null;
		}
		String[] arr = new String[files.length];
		int index = 0;
		for (MultipartFile file : files) {
			String name = upload(file);
			arr[index++] = ImageUtils.BASE_UPLOADED_URL+name;
		}
		return arr;
	}

	@Override
	public String upload(MultipartFile file) {
		if (file == null || file.isEmpty()) return null;
		String extName = ImageUtils.hasExtName(file);
		if (extName == null) {//如果不是就报异常或者返回null
			return null;
		}
		try {
			String filename = ImageUtils.saveFileToLocal(file, extName);
			return filename;
		} catch (FileNotFoundException e) {
			logger.error("文件不存在", e);
			throw new IllegalArgumentException("文件不存在");
		} catch (IOException e) {
			logger.error("文件读写异常", e);
			throw new IllegalArgumentException("文件读写异常");
		}
	}

}

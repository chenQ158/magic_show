package com.qc.magic.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	String[] upload(MultipartFile[] files);
	
	String upload(MultipartFile file);

}

package com.qc.magic.service;

import com.qc.magic.model.Image;
import com.qc.magic.model.Vo.PageEntity;

public interface ImageService {
	
	void addImage(Image image);

	PageEntity<Image> getLatestImages();
	
	PageEntity<Image> getImagesPage(boolean isAse, int offset, int limit);
	
	PageEntity<Image> getImagesPage(int userId, int offset, int limit);
	
	PageEntity<Image> getImagesPageWithSelect(boolean isAse, int offset, int limit, Image image);
	
	Image getImageById(int id);

	void addImages(Integer id, String[] urls);
	
	void updateImage(Integer id, Integer likeCount, Integer starCount, Integer commentCount);
}

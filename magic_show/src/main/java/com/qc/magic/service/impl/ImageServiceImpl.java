package com.qc.magic.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qc.magic.dao.ImageMapper;
import com.qc.magic.model.Image;
import com.qc.magic.model.ImageExample;
import com.qc.magic.model.ImageExample.Criteria;
import com.qc.magic.model.Vo.PageEntity;
import com.qc.magic.service.ImageService;
import com.qc.magic.utils.PageUtils;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageMapper imageMapper;
	
	@Override
	public PageEntity<Image> getLatestImages() {
		return getImagesPage(false, 0, 10);
	}

	@Override
	public PageEntity<Image> getImagesPage(boolean isAsc, int offset, int limit) {
		PageHelper.startPage(offset, limit);
		ImageExample example = new ImageExample();
		if (!isAsc) {
			example.setOrderByClause("created_date desc");
		} else {
			example.setOrderByClause("created_date asc");
		}
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(0);
		
		List<Image> selected = imageMapper.selectByExample(example);
		PageInfo<Image> info = new PageInfo<>(selected);
		// 返回分页数据
		return PageUtils.getImagePageEntity(info);
	}

	@Override
	public PageEntity<Image> getImagesPageWithSelect(boolean isAse, int offset, int limit, Image image) {
		
		return null;
	}

	@Override
	public Image getImageById(int id) {
		return imageMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void addImage(Image image) {
		imageMapper.insert(image);
	}

	@Override
	public PageEntity<Image> getImagesPage(int userId, int offset, int limit) {
		// 分页
		PageHelper.startPage(offset, limit);
		// 查询
		ImageExample example = new ImageExample();
		example.setOrderByClause("created_date desc");
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		// 结果处理
		List<Image> list = imageMapper.selectByExample(example);
		PageInfo<Image> info = new PageInfo<>(list);
		System.out.println(JSONObject.toJSONString(info));
		// 提取分页数据
		return PageUtils.getImagePageEntity(info);
	}

	@Transactional
	@Override
	public void addImages(Integer userId, String[] urls) {
		for (String url : urls) {
			Image image = new Image();
			image.setUrl(url);
			image.setUserId(userId);
			image.setStatus(0);
			image.setGroupId(0);
			image.setLikeCount(0);
			image.setStarCount(0);
			image.setCommentCount(0);
			image.setCreatedDate(new Date());
			addImage(image);
		}
	}

	@Override
	@Transactional
	public void updateImage(Integer id, Integer likeCount, Integer starCount, Integer commentCount) {
		Image image = new Image();
		image.setId(id);
		if (likeCount != null && likeCount != 0) {
			image.setLikeCount(likeCount);
		}
		if (starCount != null && starCount != 0) {
			image.setStarCount(starCount);
		}
		if (commentCount != null && commentCount != 0) {
			image.setCommentCount(commentCount);
		}
		imageMapper.updateByPrimaryKeySelective(image);
	}
	
}

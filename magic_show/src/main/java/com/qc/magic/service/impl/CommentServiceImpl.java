package com.qc.magic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qc.magic.dao.CommentMapper;
import com.qc.magic.model.Comment;
import com.qc.magic.model.CommentExample;
import com.qc.magic.model.CommentExample.Criteria;
import com.qc.magic.model.Image;
import com.qc.magic.model.Vo.PageEntity;
import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.CommentService;
import com.qc.magic.service.ImageService;
import com.qc.magic.service.UserService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;
	
	@Override
	public List<ViewObject> getCommentAndOwner(int imageId) {
		List<Comment> list = getComments(imageId);
		List<ViewObject> res = new ArrayList<>(); 
		for (Comment comment : list) {
			ViewObject obj = new ViewObject();
			obj.set("comment", comment);
			obj.set("owner", userService.getUserById(comment.getUserId()));
			res.add(obj);
		}
		return res;
	}

	/**
	 * 获取所有评论
	 * @param imageId
	 * @return
	 */
	/*private List<Comment> getCommentsByImageId(int imageId) {
		CommentExample example = new CommentExample();
		// 按时间升序
		example.setOrderByClause("created desc");
		Criteria criteria = example.createCriteria();
		criteria.andImageIdEqualTo(imageId);
		// 状态未删除
		criteria.andStatusEqualTo(0);
		
		List<Comment> list = commentMapper.selectByExample(example);
		return list;
	}*/

	@Override
	public PageEntity<ViewObject> getCommentAndOwner(int imageId, int offset, int limit) {
		return getCommentsPage(imageId, offset, limit);
	}
	
	/**
	 * 分页获取评论及评论者
	 * @param imageId
	 * @param offset
	 * @param limit
	 * @return
	 */
	private  PageEntity<ViewObject> getCommentsPage(int imageId, int offset, int limit) {
		// 分页
		PageHelper.startPage(offset, limit);
		// 处理分页结果
		List<Comment> list = getComments(imageId);

		PageInfo<Comment> pageInfo = new PageInfo<>(list);
		
		// 评论及评论者
		List<ViewObject> voList = new ArrayList<>(); 
		for (Comment comment : pageInfo.getList()) {
			ViewObject obj = new ViewObject();
			obj.set("comment", comment);
			obj.set("owner", userService.getUserById(comment.getUserId()));
			voList.add(obj);
		}
		
		// 获取分页数据
		PageEntity<ViewObject> pageEntity = new PageEntity<>();
		pageEntity.setCur_list(voList);
		pageEntity.setCur_page(pageInfo.getPageNum());
		pageEntity.setPageSize(pageInfo.getPageSize());
		pageEntity.setHasNext(pageInfo.isHasNextPage());
		pageEntity.setTotalCount(pageInfo.getTotal());
		pageEntity.setTotalPage(pageInfo.getPages());
		pageEntity.setIsFirst(pageInfo.isIsFirstPage());
		
		return pageEntity;
	}

	@Override
	public void addComment(Comment comment) {
		commentMapper.insert(comment);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = new Comment();
		comment.setId(commentId);
		comment.setStatus(1);
		commentMapper.updateByPrimaryKeySelective(comment);
	}

	@Override
	public List<Comment> getComments(int imageId) {
		CommentExample example = new CommentExample();
		example.setOrderByClause("created desc");
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(0);
		criteria.andImageIdEqualTo(imageId);
		List<Comment> res = commentMapper.selectByExample(example);
		return res;
	}

	@Override
	public ViewObject addComment(String content, Integer imageId, Integer userId) {
		ViewObject vo = new ViewObject();
		if (StringUtils.isBlank(content)) {
			vo.set("msg", "评论内容不能为空");
			vo.set("code", 1);
			return vo;
		}
		Image image = imageService.getImageById(imageId);
		if (image == null) {
			vo.set("msg", "图片不存在");
			vo.set("code", 1);
			return vo;
		}
		// 执行添加
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImageId(imageId);
		comment.setUserId(userId);
		addComment(comment);
		
		vo.set("code", 0);
		vo.set("msg", "添加评论成功");
		vo.set("commentId", comment.getId());
		vo.set("commentCount", image.getCommentCount());
		return vo;
	}

	@Override
	public int getCommentCount(Integer id) {
		CommentExample example = new CommentExample();
		Criteria criteria = example.createCriteria();
		criteria.andImageIdEqualTo(id);
		criteria.andStatusEqualTo(0);
		long count = commentMapper.countByExample(example);
		return Long.valueOf(count).intValue();
	}

}

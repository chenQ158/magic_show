package com.qc.magic.service;

import java.util.List;

import com.qc.magic.model.Comment;
import com.qc.magic.model.Vo.PageEntity;
import com.qc.magic.model.Vo.ViewObject;

public interface CommentService {

	/**
	 * 通过图片id获取评论以及评论者列表
	 * @param imageId
	 * @return
	 */
	public List<ViewObject> getCommentAndOwner(int imageId);
	
	/**
	 * 通过图片id获取评论以及评论者列表分页
	 * @param imageId
	 * @return
	 */
	public PageEntity<ViewObject> getCommentAndOwner(int imageId, int offset, int limit);
	
	/**
	 * 添加评论
	 * @param comment
	 */
	public void addComment(Comment comment);
	
	/**
	 * 删除评论
	 * @param commentId
	 */
	public void deleteComment(int commentId);
	
	/**
	 * 获取评论
	 * @return
	 */
	public List<Comment> getComments(int commentId);

	/**
	 * 添加评论
	 * @param content
	 * @param imageId
	 * @param id
	 * @return
	 */
	public ViewObject addComment(String content, Integer imageId, Integer id);

	/**
	 * 获取指定照片的评论数
	 * @param id
	 * @return
	 */
	public int getCommentCount(Integer id);
	
}

package com.qc.magic.model;

import java.util.Date;

/**
 * 评论类
 * @author john
 *
 */
public class Comment {
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论图片id
	 */
	private Integer imageId;
	/**
	 * 评论人id
	 */
	private Integer userId;
	/**
	 * 评论状态：0: 正常，1: 删除
	 */
	private Integer status=0;
	/**
	 * 评论时间
	 */
	private Date created=new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
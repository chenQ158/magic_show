package com.qc.magic.model;

import java.util.Date;
/**
 * 图片实体类
 * @author john
 */
public class Image {
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 图片路径
	 */
	private String url;
//	private String title;
	/**
	 * 图片状态：0:正常，1:删除
	 */
	private Integer status;
	
	private Integer starCount;

    private Integer likeCount;

    private Integer commentCount;
	/**
	 * 创建时间
	 */
	private Date createdDate;
	/**
	 * 所属用户id
	 */
    private Integer userId;
	/**
	 * 图片所属分组，默认0
	 */
    private Integer groupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
package com.qc.magic.model.Vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页数据类
 * @author john
 *
 * @param <T>
 */
public class PageEntity <T> {

	private Integer cur_page=0;		//当前页数,从1开始
	private Long totalCount;		//总记录数
	private Integer totalPage;		//总页数
	private Integer pageSize=10;	//每页记录数
	private List<T> cur_list;		//当前页记录列表
	private boolean hasNext;		//是否还有下一页
	private boolean isFirst;		//是否是首页
	
	private Map<String, Object> map = new HashMap<>();
	
	public Map<String, Object> getPageDetails() {
		map.clear();
		map.put("curPage", this.getCur_page());
		map.put("pageSize", this.getPageSize());
		map.put("totalCount", this.getTotalCount());
		map.put("totalPage", this.getTotalPage());
		map.put("hasNext", this.getHasNext());
		map.put("isFirst", this.isFirst());
		return map;
	}
	
	public boolean isFirst() {
		return isFirst;
	}
	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Integer getCur_page() {
		return cur_page;
	}
	public void setCur_page(Integer cur_page) {
		this.cur_page = cur_page;
	}
	public List<T> getCur_list() {
		return cur_list;
	}
	public void setCur_list(List<T> cur_list) {
		this.cur_list = cur_list;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

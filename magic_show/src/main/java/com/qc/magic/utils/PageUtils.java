package com.qc.magic.utils;

import com.github.pagehelper.PageInfo;
import com.qc.magic.model.Vo.PageEntity;

public class PageUtils {
	
	/**
	 * 从PageInfo中获取分页相关的数据
	 * @param info
	 * @return
	 */
	public static <T> PageEntity<T> getImagePageEntity(PageInfo<T> info) {
		PageEntity<T> entity = new PageEntity<T>();
		entity.setCur_list(info.getList());
		entity.setCur_page(info.getPageNum());
		entity.setPageSize(info.getPageSize());
		entity.setTotalCount(info.getTotal());
		entity.setTotalPage(info.getPages());
		entity.setHasNext(info.isHasNextPage());
		entity.setIsFirst(info.isIsFirstPage());
		return entity;
	}

}

package com.qc.magic.model.Vo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.qc.magic.utils.Constant;

public class ViewObject {

	private Map<String, Object> map = new HashMap<>();
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public void set(String key, Object value) {
		map.put(key, value);
	}

	public Map<String, Object> getMap() {
		return map;
	}
	
	public static Map<String, Object> valueOf(Integer code, String pageName, String prev) {
		Map<String, Object> map = new ConcurrentHashMap<>();
		map.clear();
		map.put("code", code);
		map.put("msg", code == 500 ? "服务器发生异常，请联系管理员":"您访问的路径不存在");
		map.put("data", String.format("获取%s失败", pageName));
		map.put("prev", prev==null?Constant.WEBSITE_PREFIX:prev);
		return map;
	}

	public void addAll(Map<String, Object> pageDetails) {
		map.putAll(pageDetails);
	}
}

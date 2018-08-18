package com.qc.magic.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * map缓存实现
 * @author john
 *
 */
public class MapCache {

	/**
	 * 默认存储1024个缓存
	 */
	private static final int DEFAULT_CACHES=1024;
	
	/**
	 * 单例模式唯一对象
	 */
	private static final MapCache INS = new MapCache();
	
	/**
	 * 饿汉式
	 * @return
	 */
	public static MapCache single() {
		return INS;
	}
	
	/**
	 * 缓存容器
	 */
	private Map<String, CacheObject> cachePool;
	
	private MapCache() {
		this(DEFAULT_CACHES);
	}
	
	/**
	 * 使用ConcurrentHashMap保证线程安全
	 * @param cacheCount
	 */
	private MapCache(int cacheCount) {
		cachePool = new ConcurrentHashMap<>(cacheCount);
	}
	
	/**
	 * 通过key获取缓存的对象
	 * @param key
	 * @return
	 */
	public <T> T get(String key) {
		CacheObject cacheObject = cachePool.get(key);
		if (null != cacheObject) {
			Long cur = System.currentTimeMillis() / 1000;
			// 未过期直接返回
			if (cacheObject.getExpired() <= 0 || cacheObject.getExpired() > cur) {
				@SuppressWarnings("unchecked")
				T res = (T) cacheObject.getValue();
				return res;
			}
			// 过期的删除
			if (cur > cacheObject.getExpired()) {
				cachePool.remove(key);
			}
		}
		return null;
	}
	
	/**
	 * 读取一个hash类型缓存
	 * @param key
	 * @param field
	 * @return
	 */
	public <T> T hget(String key, String field) {
		key = key + ":" + field;
		return get(key);
	}
	
	/**
	 * 设置一个不过期缓存
	 * @param key
	 * @param value
	 */
	public void set( String key, Object value) {
		this.set(key, value, -1);
	}
	
	/**
	 * 设置一个缓存并带过期时间
	 * @param key		缓存key
	 * @param value		缓存value
	 * @param expired	过期时间,单位秒(s)
	 */
	public void set(String key, Object value, long expired) {
		// 计算超时时间
		expired = expired > 0 ? System.currentTimeMillis() / 1000 + expired : expired;
		//cachePool大于800时，强制清空缓存池，这个操作有些粗暴会导致误删问题，后期考虑用redis替代MapCache优化
		if (cachePool.size() > 800) {
			cachePool.clear();
		}
		CacheObject cacheObject = new CacheObject(key, value, expired);
		cachePool.put(key, cacheObject);
	}
	
	/**
	 * 添加一个hash缓存
	 * @param key
	 * @param field
	 */
	public void hset(String key, String field, Object value) {
		this.hset(key, field, value, -1);
	}
	
	/**
	 * 添加一个hash缓存并设置过期时间
	 * @param key
	 * @param field
	 * @param value
	 * @param expired
	 */
	public void hset(String key, String field, Object value, long expired) {
		key = key + ":" + field;
		expired = expired > 0 ? expired + System.currentTimeMillis() / 1000 : expired;
		CacheObject cacheObject = new CacheObject(key, value, expired);
		cachePool.put(key, cacheObject);
	}
	
	/**
	 * 根据key删除缓存
	 * @param key
	 */
	public void del(String key) {
		cachePool.remove(key);
	}
	
	/**
	 * 根据key和field删除缓存
	 * @param key
	 * @param field
	 */
	public void del(String key, String field) {
		key = key + ":" + field;
		this.del(key);
	}
	
	/**
	 * 清空缓存
	 */
	public void clear() {
		cachePool.clear();
	}
	
	static class CacheObject {
		private String key;
		private Object value;
		private Long expired;
		
		public CacheObject(String key, Object value, Long expired) {
			super();
			this.key = key;
			this.value = value;
			this.expired = expired;
		}
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public Long getExpired() {
			return expired;
		}
		public void setExpired(Long expired) {
			this.expired = expired;
		}
	}
	
}

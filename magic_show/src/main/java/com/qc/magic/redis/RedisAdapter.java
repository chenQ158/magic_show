package com.qc.magic.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * redis操作类
 * @author john
 *
 */
@Service
public class RedisAdapter {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 添加字符串
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		set(key, value, -1);
	}

	public void set(String key, Object value, int timeout) {
		redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), timeout);
	}
	
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	
	/**
	 * 向set中添加元素
	 * @param key
	 * @param member
	 */
	public void sadd(String key, String member) {
		redisTemplate.opsForSet().add(key, member);
	}
	
	public void srem(String key, String member) {
		redisTemplate.opsForSet().remove(key, member);
	}
	
	public Long scard(String key) {
		return redisTemplate.opsForSet().size(key);
	}
	
	public Set<String> members(String key) {
		return redisTemplate.opsForSet().members(key);
	}
	
	/**
	 * 向list中添加元素
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String value) {
		redisTemplate.opsForList().leftPush(key, value);
	}
	
	public void rpush(String key, String value) {
		redisTemplate.opsForList().rightPush(key, value);
	}
	
	public String lpop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}
	
	public String rpop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}
	
	public String brpop(String key) {
		return redisTemplate.opsForList().rightPop(key, 0, TimeUnit.SECONDS);
	}
	
	public List<String> lrange(String key) {
		long size = redisTemplate.opsForList().size(key);
		return redisTemplate.opsForList().range(key, 0, size-1);
	}
	
	public long llen(String key) {
		return redisTemplate.opsForList().size(key);
	}
	
	/**
	 * 向hash中添加元素
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, Object field, Object value) {
		redisTemplate.opsForHash().put(key, field, value);
	}
	
	public Object hget(String key, Object field) {
		return redisTemplate.opsForHash().get(key, field);
	}
	
	public long hincrby(String key, Object field, int num) {
		return redisTemplate.opsForHash().increment(key, field, num);
	}
	
	public boolean hexists(String key, Object field) {
		return redisTemplate.opsForHash().hasKey(key, field);
	}
	
	public Map<Object, Object> hgetall(String key) {
		return redisTemplate.opsForHash().entries(key);
	}
	
	public boolean del(String key) {
		return redisTemplate.delete(key);
	}
	
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
}

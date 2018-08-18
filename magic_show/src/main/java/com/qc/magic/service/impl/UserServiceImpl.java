package com.qc.magic.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qc.magic.dao.UserMapper;
import com.qc.magic.model.User;
import com.qc.magic.model.UserExample;
import com.qc.magic.model.UserExample.Criteria;
import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.UserService;
import com.qc.magic.utils.ImageUtils;
import com.qc.magic.utils.MapCache;
import com.qc.magic.utils.Md5Utils;
import com.qc.magic.utils.ValidateUtils;

/**
 * 用户Service
 * @author john
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public ViewObject login(String email, String password, String rememberme) {
		ViewObject vo = validate(email, password);
		if (vo != null) {
			return vo;
		} 
		
		vo = new ViewObject();
		User user = getUserByEmail(email);
		if (user == null) {
			vo.set("msg", "用户或者密码不正确");
			return vo;
		}
		if (!Md5Utils.MD5(password+user.getSalt()).equals(user.getPassword())) {
			vo.set("msg", "用户或者密码不正确");
			return vo;
		}
		
		// 设置一个magic值作为sessionId
		String magic = UUID.randomUUID().toString().replaceAll("-", "");
		vo.set("magic", magic);
		// 将magic和user放入缓存中
		String json = JSONObject.toJSONString(user);
		MapCache mapCache = MapCache.single();
		if (StringUtils.isNotBlank(rememberme)) {
			// 如果记住密码就保存7天
			mapCache.hset("USER", magic, json, 3600*24*7);
		} else {
			mapCache.hset("USER", magic, json, 3600);
		}
		System.out.println("cache:magic:"+magic);
		
		return vo;
	}

	@Override
	public User getUserByEmail(String email) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> list = userMapper.selectByExample(example);
		if (list.size() > 0 ) return list.get(0);
		return null;
	}

	@Override
	public ViewObject register(String email, String password) {
		ViewObject vo = validate(email, password);
		if (vo != null) {
			return vo;
		} 
		
		vo = new ViewObject();
		if (getUserByEmail(email) != null) {
			vo.set("msg", "用户已存在");
			return vo;
		} else {
			// 替换email和password中的敏感字符
			// 添加用户
			User user = new User();
			user.setEmail(email);
			user.setHeadUrl(ImageUtils.getRandomUrl());
			user.setIsActived(false);
			user.setNickname("昵称可修改");
			String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
			user.setSalt(salt);
			user.setPassword(Md5Utils.MD5(password+salt));
			user.setActiveCode(UUID.randomUUID().toString().replaceAll("-", ""));
			
			addUser(user);
			
			// 设置一个magic值作为sessionId
			String magic = UUID.randomUUID().toString().replaceAll("-", "");
			vo.set("magic", magic);
			// 将magic和user放入缓存中
			String json = JSONObject.toJSONString(user);
			MapCache.single().hset("USER", magic, json, 3600);
			
			return vo;
		}
	}

	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}
	
	/**
	 * 检测邮件和密码是否合格
	 * @param email
	 * @param password
	 * @return
	 */
	private ViewObject validate(String email, String password) {
		ViewObject vo = new ViewObject();
		if (StringUtils.isBlank(email)) {
			vo.set("msg", "邮件不能为空");
			return vo;
		} else if (!ValidateUtils.isEamil(email)) {
			vo.set("msg", "邮件格式不正确");
			return vo;
		} else if (StringUtils.isBlank(password)) {
			vo.set("msg", "密码不能为空");
			return vo;
		} else if (!ValidateUtils.isLengthEnough(password)) {
			vo.set("msg", "密码长度必须大于10");
			return vo;
		}
		return null;
	}

	@Override
	public void update(User user) {
		userMapper.updateByPrimaryKey(user);
	}


}

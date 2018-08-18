package com.qc.magic.service;

import com.qc.magic.model.User;
import com.qc.magic.model.Vo.ViewObject;

public interface UserService {
	/**
	 * 添加用户
	 * @param user
	 */
	void addUser(User user);
	
	/**
	 * 通过用户id获取用户实体
	 * @param id
	 * @return
	 */
	User getUserById(int id);

	/**
	 * 登陆用户
	 * @param email
	 * @param password
	 * @param rememberme
	 * @return
	 */
	ViewObject login(String email, String password, String rememberme);
	
	/**
	 * 通过邮件获取用户实体
	 * @param email
	 * @return
	 */
	User getUserByEmail(String email);

	/**
	 * 注册用户
	 * @param email
	 * @param password
	 * @return
	 */
	ViewObject register(String email, String password);

	/**
	 * 更新用户状态
	 * @param user
	 */
	void update(User user);

}

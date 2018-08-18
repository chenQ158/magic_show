package com.qc.magic;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.qc.magic.model.User;
import com.qc.magic.model.Vo.ViewObject;
import com.qc.magic.service.UserService;
import com.qc.magic.utils.ImageUtils;
import com.qc.magic.utils.Md5Utils;
import com.qc.magic.utils.RandomUtils;

@RunWith(SpringRunner.class)
@MapperScan("com.qc.magic.dao")
@SpringBootTest
public class MagicShowApplicationTests {

	@Autowired
	private UserService userService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void regUser() {
		ViewObject vo = userService.register("chenqing@12315.com", "0000000000");
		System.out.println(JSONObject.toJSONString(vo));
		
		vo = userService.register("chenqing@12315.com", "0000000000");
		System.out.println(JSONObject.toJSONString(vo));
		
		vo = userService.register(null, "0000000000");
		System.out.println(JSONObject.toJSONString(vo));

		vo = userService.register("", "0000000000");
		System.out.println(JSONObject.toJSONString(vo));

		vo = userService.register("123132312@12313.com", null);
		System.out.println(JSONObject.toJSONString(vo));

		vo = userService.register("123132312@12313.com", "");
		System.out.println(JSONObject.toJSONString(vo));

		vo = userService.register("123132312@12313.com", "123456");
		System.out.println(JSONObject.toJSONString(vo));

		vo = userService.register("123132312313.com", "123456");
		System.out.println(JSONObject.toJSONString(vo));
		
		
	}
	
	@Test
	public void addUsers() {
		for (int i=0; i<100; i++) {
			User user = new User();
			user.setActiveCode(UUID.randomUUID().toString().replaceAll("-", ""));
			user.setEmail(RandomUtils.getRandomEmail());
			user.setNickname(RandomUtils.getRandomName());
			user.setSalt(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
			user.setPassword(Md5Utils.MD5("1234567890"+user.getSalt()));
			user.setHeadUrl(ImageUtils.getRandomUrl());
			userService.addUser(user);
		}
	}
	
	@Test
	public void getUserTest() {
		User user = userService.getUserById(12);
		System.out.println(JSONObject.toJSON(user));
		
		user = userService.getUserByEmail("rqg44691533621352239@owjct.com");
		System.out.println(JSONObject.toJSON(user));
	}
	
	@Test
	public void getUsersTest() {
		ViewObject vo = userService.login("rqg44691533621352239@owjct.com", "123456", null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login("rqg44691533621352239@owjct.com", "1234567890", null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login(null, "1234567890", null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login("", "1234567890", null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login("rqg44691533621352239@owjct.com", null, null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login("rqg44691533621352239@owjct.com", "", null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login("iu7591533621882747@mozpw.com", "1234567890", null);
		System.out.println(JSONObject.toJSON(vo));
		vo = userService.login("illb87071533621354693@zkkep.com", "1234567890", null);
		System.out.println(JSONObject.toJSON(vo));
	}
	
	@Test
	public void printTest() {
		for (int i=0; i<100; i++) {
			System.out.println("email: "+RandomUtils.getRandomEmail());
			System.out.println("name: "+RandomUtils.getRandomName());
		}
	}

}

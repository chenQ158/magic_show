package com.qc.magic.model.Vo;

import org.springframework.stereotype.Component;

import com.qc.magic.model.User;

@Component
public class HostHolder {

	private ThreadLocal<User> threadLocal = new ThreadLocal<>();
	
	public void set(User user) {
		threadLocal.set(user);
	}
	
	public User getUser() {
		return threadLocal.get();
	}
	
	public void clear() {
		threadLocal.remove();
	}
}

package com.qc.magic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

	@RequestMapping("/user/message")
	public String toMessage() {
		
		return "message";
	}
	
}

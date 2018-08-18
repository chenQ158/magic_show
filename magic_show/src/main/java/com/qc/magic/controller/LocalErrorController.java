package com.qc.magic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LocalErrorController extends BasicErrorController {

	public LocalErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
        
    }

	@Override
	public String getErrorPath() {
		return super.getErrorPath();
	}

	/**
     * 覆盖默认的Json响应
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        
        HttpStatus status = getStatus(request);

        //输出自定义的Json格式
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        map.put("msg", body.get("message"));
        
        System.out.println(JSONObject.toJSONString(request.getParameterMap()));

        return new ResponseEntity<Map<String, Object>>(map, status);
    }

    /**
     * 覆盖默认的HTML响应
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //请求的状态
        HttpStatus status = getStatus(request);
        response.setStatus(getStatus(request).value());
        
        
        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        
        if (!request.getParameterMap().isEmpty()) {
        	model.put("msg", request.getParameter("msg"));
        	model.put("code", request.getParameter("code"));
        	model.put("data", request.getParameter("data"));
        	model.put("prev", request.getParameter("prev"));
        } else {
        	int statusCode = (int) model.get("status");
        	if (statusCode == 404) {
        		model.put("msg", "您访问的地址有误");
        	} else if (statusCode == 500) {
        		model.put("msg", "服务器发生错误");
        	} else {
        		model.put("msg", "访问错误");
        	}
        	String referer = request.getHeader("Referer");
        	if (StringUtils.isNotBlank(referer)) {
        		model.put("prev", referer);
        	}
        	model.put("code", statusCode);
        }
        
        System.out.println(JSONObject.toJSONString(model));
        
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        //指定自定义的视图
        return(modelAndView == null ? new ModelAndView("error", model) : modelAndView);
    }
	
}

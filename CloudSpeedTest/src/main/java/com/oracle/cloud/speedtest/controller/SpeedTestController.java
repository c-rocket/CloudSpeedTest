package com.oracle.cloud.speedtest.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.cloud.speedtest.service.SpeedTestService;

@Controller
public class SpeedTestController {

	private static final Logger logger = LoggerFactory.getLogger(SpeedTestController.class);
	
	@Resource
	private SpeedTestService service;
	
	@RequestMapping(value = "/speedtest/start", method = RequestMethod.GET)
	@ResponseBody
	public Boolean startTest() {
		logger.info("Starting Speed Test {}.", new Date());
		
		return service.start(true);
	}
	
	@RequestMapping(value = "/speedtest/uitest/ping", method = RequestMethod.GET)
	@ResponseBody
	public Boolean pingTest() {
		return true;
	}
	
	@RequestMapping(value = "/speedtest/uitest/small", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> uiSmallTest() {
		return service.uiSmallTest();
	}
	
	@RequestMapping(value = "/speedtest/uitest/medium", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> uiMediumTest() {
		return service.uiMediumTest();
	}
	
	@RequestMapping(value = "/speedtest/uitest/large", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> uiLargeTest() {
		return service.uiLargeTest();
	}
	
	@RequestMapping(value = "/speedtest/running", method = RequestMethod.GET)
	@ResponseBody
	public Boolean isRunning() {
		return service.isRunning();
	}
	
	@RequestMapping(value = "/speedtest/results", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getResults() {
		return service.getResults();
	}
	
	@RequestMapping(value = "/speedtest/message", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> getMessage() {
		return service.getMessage();
	}
}

package com.oracle.cloud.speedtest.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	public Boolean startTest(Locale locale) {
		logger.info("Starting Speed Test {}.", new Date());
		
		return service.start();
	}
	
	@RequestMapping(value = "/speedtest/uitest/small", method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> uiSmallTest() {
		return service.uiSmallTest();
	}
	
	@RequestMapping(value = "/speedtest/uitest/medium", method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> uiMediumTest() {
		return service.uiMediumTest();
	}
	
	@RequestMapping(value = "/speedtest/uitest/large", method = RequestMethod.GET)
	@ResponseBody
	public List<Integer> uiLargeTest() {
		return service.uiLargeTest();
	}
	
	@RequestMapping(value = "/speedtest/dbtest", method = RequestMethod.GET)
	@ResponseBody
	public Boolean dbTest() {
		return service.dbTest();
	}
}

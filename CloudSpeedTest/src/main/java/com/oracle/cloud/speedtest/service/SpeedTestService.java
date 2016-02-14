package com.oracle.cloud.speedtest.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oracle.cloud.speedtest.dao.SpeedTestDao;

@Service
public class SpeedTestService {
	private static final Logger logger = LoggerFactory.getLogger(SpeedTestService.class);
	
	private Boolean started = false;
	
	private List<Integer> smallPacket = new ArrayList<Integer>();
	private List<Integer> mediumPacket = new ArrayList<Integer>();
	private List<Integer> largePacket = new ArrayList<Integer>();
	
	@Resource
	private SpeedTestDao dao;
	
	public SpeedTestService(){
		for(int i = 0; i < 10; i++){
			smallPacket.add(i);
		}
		for(int j = 0; j < 1000; j++){
			smallPacket.add(j);
		}
		for(int k = 0; k < 100000; k++){
			smallPacket.add(k);
		}
	}

	public synchronized Boolean start() {
		if(started){
			logger.info("currently running");
			return false;
		}
		started = true;
		logger.info("starting speed test");
		return started;
	}

	public List<Integer> uiSmallTest() {
		if(started){
			return null;
		}
		return smallPacket;
	}

	public List<Integer> uiMediumTest() {
		if(started){
			return null;
		}
		return mediumPacket;
	}

	public List<Integer> uiLargeTest() {
		if(started){
			return null;
		}
		return largePacket;
	}

	public List<Integer> dbTest() {
		// TODO Auto-generated method stub
		return null;
	}
}

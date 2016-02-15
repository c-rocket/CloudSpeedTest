package com.oracle.cloud.speedtest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
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

	private MetricRegistry metrics = new MetricRegistry();

	public SpeedTestService() {
		for (int i = 0; i < 10; i++) {
			smallPacket.add(i);
		}
		for (int j = 0; j < 1000; j++) {
			smallPacket.add(j);
		}
		for (int k = 0; k < 100000; k++) {
			smallPacket.add(k);
		}
	}

	public synchronized Boolean start() {
		started = true;
		logger.info("starting speed test");
		//Start Console Reporter
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(1, TimeUnit.SECONDS);
		
		return started;
	}

	public List<Integer> uiSmallTest() {
		return smallPacket;
	}

	public List<Integer> uiMediumTest() {
		return mediumPacket;
	}

	public List<Integer> uiLargeTest() {
		return largePacket;
	}

	public Boolean dbTest() {
		// Test inserts x3
		dao.insertTestObjects(100);
		return null;
	}
}

package com.oracle.cloud.speedtest.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oracle.cloud.speedtest.dao.SpeedTestDao;
import com.oracle.cloud.speedtest.util.MathUtil;

@Service
public class SpeedTestService {
	private static final Logger logger = LoggerFactory.getLogger(SpeedTestService.class);

	private Boolean started = false;

	private List<Integer> smallPacket = new ArrayList<Integer>();
	private List<Integer> mediumPacket = new ArrayList<Integer>();
	private List<Integer> largePacket = new ArrayList<Integer>();

	private List<Long> dbInserts = new ArrayList<Long>();
	private List<Long> dbFullTableGets = new ArrayList<Long>();
	private int dbFullTableCount = 0;
	
	@Resource
	private SpeedTestDao dao;

	public SpeedTestService() {
		//Build UI packets for testing
		for (int i = 0; i < 10; i++) {
			smallPacket.add(i);
		}
		for (int j = 0; j < 1000; j++) {
			mediumPacket.add(j);
		}
		for (int k = 0; k < 100000; k++) {
			largePacket.add(k);
		}
	}

	public synchronized Boolean start() {
		if (started) {
			return false;
		}
		started = true;
		logger.info("starting speed test");
		Thread tests = new Thread() {
			public void run() {
				try {
					runDBTests();
					started = false;
				} catch (Exception e) {
					logger.error("Error running tests", e);
				}
			}
		};
		tests.start();
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

	private void runDBTests() {
		testInserts();
		testFullTableGets();
	}

	private void testFullTableGets() {
		for (int i = 0; i < 20; i++) {
			long startTime = System.nanoTime();
			List<Map<String, Object>> list = dao.getAll();
			long endTime = System.nanoTime();
			dbFullTableGets.add((endTime - startTime) / 1000000);
			dbFullTableCount = list.size();
		}
	}

	private void testInserts() {
		for (int i = 0; i < 20; i++) {
			long startTime = System.nanoTime();
			dao.insertTestObjects("idxName" + i, "name" + i);
			long endTime = System.nanoTime();
			dbInserts.add((endTime - startTime) / 1000000);
		}
	}

	public Boolean isRunning() {
		return started;
	}

	public List<Map<String, Object>> getResults() {
		if (started) {
			return null;
		}
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		results.add(getResult("DB Inserts",MathUtil.average(dbInserts),20));
		results.add(getResult("Full Table Gets",MathUtil.average(dbFullTableGets),dbFullTableCount));
		return results;
	}

	private Map<String, Object> getResult(String name,Long average, int records) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("name", name);
		result.put("average", average);
		result.put("records", records);
		return result;
	}
}

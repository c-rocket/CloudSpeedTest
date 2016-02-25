package com.oracle.cloud.speedtest.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oracle.cloud.speedtest.dao.SpeedTestDao;
import com.oracle.cloud.speedtest.util.MathUtil;
import com.oracle.cloud.speedtest.util.StringUtil;

@Service
public class SpeedTestService {
	private static final Logger logger = LoggerFactory.getLogger(SpeedTestService.class);

	private Boolean started = false;

	private List<Map<String,Object>> smallPacket = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> mediumPacket = new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> largePacket = new ArrayList<Map<String,Object>>();

	private List<Long> dbInserts = new ArrayList<Long>();
	private List<Long> dbFullTableGets = new ArrayList<Long>();
	private int dbFullTableCount = 0;

	public static final int TEST_RUNS = 3;
	
	private String message = "Tests not running";
	
	@Resource
	private SpeedTestDao dao;

	public SpeedTestService() {
		// Build UI packets for testing
		for (int i = 0; i < 1000; i++) {
			smallPacket.add(testRecord(i));
		}
		for (int j = 0; j < 10000; j++) {
			mediumPacket.add(testRecord(j));
		}
		for (int k = 0; k < 100000; k++) {
			largePacket.add(testRecord(k));
		}
	}

	private Map<String, Object> testRecord(int seed) {
		Map<String, Object> obj = new LinkedHashMap<String, Object>();
		obj.put("name", StringUtil.random(10));
		obj.put("value", seed);
		return obj;
	}

	public synchronized Boolean start(boolean threaded) {
		if (started) {
			return false;
		}
		started = true;
		logger.info("starting speed test");
		if (threaded) {
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
		} else {
			runDBTests();
			started = false;
		}
		return started;
	}

	public List<Map<String,Object>> uiSmallTest() {
		return smallPacket;
	}

	public List<Map<String,Object>> uiMediumTest() {
		return mediumPacket;
	}

	public List<Map<String,Object>> uiLargeTest() {
		return largePacket;
	}

	private void runDBTests() {
		testInserts();
		testFullTableGets();
	}

	private void testFullTableGets() {
		for (int i = 0; i < TEST_RUNS; i++) {
			message = "Running test: Full Table Gets " + (i+1) + " of " + TEST_RUNS;
			logger.info("Running Full table gets: " + i);
			long startTime = System.nanoTime();
			List<Map<String, Object>> list = dao.getAll();
			long endTime = System.nanoTime();
			dbFullTableGets.add((endTime - startTime) / 1000000);
			dbFullTableCount = list.size();
		}
	}

	private void testInserts() {
		for (int i = 0; i < TEST_RUNS; i++) {
			message = "Running test: DB Inserts " + (i+1) + " of " + TEST_RUNS;
			logger.info("Running test inserts: " + i);
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
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		if (started) {
			return results;
		}
		if (dbInserts.size() > 0 && dbFullTableGets.size() > 0) {
			results.add(getResult("DB Inserts", MathUtil.average(dbInserts), 1));
			results.add(getResult("Full Table Gets", MathUtil.average(dbFullTableGets), dbFullTableCount));
		}
		return results;
	}

	private Map<String, Object> getResult(String name, Long average, int records) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("name", name);
		result.put("average", average);
		result.put("records", records);
		result.put("timesRun", TEST_RUNS);
		return result;
	}

	public Map<String,String> getMessage() {
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put("message", message);
		return map;
	}
}

package com.oracle.cloud.speedtest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.oracle.cloud.speedtest.dao.SpeedTestDao;

@RunWith(MockitoJUnitRunner.class)
public class SpeedTestServiceTest {
	@Mock
	private SpeedTestDao dao;

	@InjectMocks
	private SpeedTestService service;

	@Test
	public void getSmallUI() throws Exception {
		// execute
		List<Integer> smallTest = service.uiSmallTest();

		// assert
		assertEquals(10, smallTest.size());
	}

	@Test
	public void getMediumUI() throws Exception {
		// execute
		List<Integer> mediumTest = service.uiMediumTest();

		// assert
		assertEquals(1000, mediumTest.size());
	}

	@Test
	public void getLargeUI() throws Exception {
		// execute
		List<Integer> largeTest = service.uiLargeTest();

		// assert
		assertEquals(100000, largeTest.size());
	}

	@Test
	public void runDBTests() throws Exception {
		// execute
		service.start();
		boolean running = service.isRunning();
		while (running) {
			running = service.isRunning();
		}
		List<Map<String, Object>> results = service.getResults();

		// assert
		Mockito.verify(dao, Mockito.times(20)).insertTestObjects(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(dao, Mockito.times(20)).getAll();
		assertNotNull(results.get(0).get("name"));
		assertNotNull(results.get(0).get("average"));
		assertNotNull(results.get(0).get("records"));
		assertNotNull(results.get(1).get("name"));
		assertNotNull(results.get(1).get("average"));
		assertNotNull(results.get(1).get("records"));
	}
}

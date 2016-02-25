package com.oracle.cloud.speedtest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
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
		assertEquals(100, smallTest.size());
	}

	@Test
	public void getMediumUI() throws Exception {
		// execute
		List<Integer> mediumTest = service.uiMediumTest();

		// assert
		assertEquals(10000, mediumTest.size());
	}

	@Test
	public void getLargeUI() throws Exception {
		// execute
		List<Integer> largeTest = service.uiLargeTest();

		// assert
		assertEquals(1000000, largeTest.size());
	}

	@Test
	public void runDBTests() throws Exception {
		//setup
		List<Map<String,Object>> records = new ArrayList<Map<String,Object>>();
		Mockito.when(dao.getAll()).thenReturn(records);
		
		// execute
		service.start(false);
		List<Map<String, Object>> results = service.getResults();

		// assert
		Mockito.verify(dao, Mockito.times(SpeedTestService.TEST_RUNS)).insertTestObjects(Mockito.anyString(), Mockito.anyString());
		Mockito.verify(dao, Mockito.times(SpeedTestService.TEST_RUNS)).getAll();
		assertNotNull(results.get(0).get("name"));
		assertNotNull(results.get(0).get("average"));
		assertNotNull(results.get(0).get("records"));
		assertNotNull(results.get(1).get("name"));
		assertNotNull(results.get(1).get("average"));
		assertNotNull(results.get(1).get("records"));
	}
}

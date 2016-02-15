package com.oracle.cloud.speedtest.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Transactional
public class SpeedTestDaoTest {

	@Resource
	private SpeedTestDao dao;

	@Test
	public void save() throws Exception {
		// setup
		String name = "name";
		String idxName = "idxName";

		// execute
		Boolean created = dao.insertTestObjects(idxName, name);

		// assert
		assertTrue(created);
	}

	@Test
	public void getAll() throws Exception {
		// execute
		List<Map<String, Object>> testObjects = dao.getAll();

		// assert
		assertTrue(testObjects.size() >= 10000);
	}
}

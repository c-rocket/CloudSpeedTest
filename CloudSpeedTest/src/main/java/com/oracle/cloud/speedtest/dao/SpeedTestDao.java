package com.oracle.cloud.speedtest.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpeedTestDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier(value = "dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Boolean insertTestObjects(String indexedName, String unIndexedName) {
		String sql = "INSERT INTO speed_test (ID,NAME_IDX,NAME_NOT_IDX) VALUES (speed_test_seq.nextval,?,?)";
		int created = jdbcTemplate.update(sql, indexedName, unIndexedName);
		return created != 0;
	}
	
	public List<Map<String,Object>> getAll(){
		String sql = "SELECT * FROM speed_test";
		return jdbcTemplate.queryForList(sql);
	}
}

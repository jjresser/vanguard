package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class VanguardApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void mssqlGet(){
		String sql = "select count(*) from bigsmall";
		int count =  jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(count);
	}

}

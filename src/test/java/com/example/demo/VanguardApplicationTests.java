package com.example.demo;

import com.example.demo.service.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;

@SpringBootTest
class VanguardApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private IdService idService;

	@Test
	void contextLoads() {
	}

	@Test
	void mssqlGet(){
		String sql = "select count(*) from bigsmall";
		int count =  jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(count);
	}

	@Test
	void snowflakeTestWithBatchLogDb(){
		insertLog(idService.generateUniqueId(),
				123, """
							[
								{
						     		"costPrice": 33.83,
						     		"dateOfSale": 1745704496000,
						     		"gameCode": "39775",
						     		"gameName": "EHeZsCvdpsjmFfTJhvFo",
						     		"gameNo": 51,
						     		"id": 9,
						     		"salePrice": 36.88,
						     		"tax": 9,
						     		"type": 1
						     	},
						     	{
						     		"costPrice": 71.81,
						     		"dateOfSale": 1743684576000,
						     		"gameCode": "60135",
						     		"gameName": "ERdFHkimCGNKiNUDGvNK",
						     		"gameNo": 48,
						     		"id": 10,
						     		"salePrice": 78.27,
						     		"tax": 9,
						     		"type": 2
						     	}
						     ]
						""",
				"",
				true

		);
	}

	public void insertLog(long batchId, long threadId, String jsonContent,String errMsg, boolean success) {
		String sql = "INSERT INTO batch_log (batchId, threadId, jsonContent, success, errorMsg, timeStamp) " +
				"VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				batchId,
				threadId,
				jsonContent,
				success,
				errMsg,
				Timestamp.from(Instant.now())  // current UTC timestamp
		);
	}

}

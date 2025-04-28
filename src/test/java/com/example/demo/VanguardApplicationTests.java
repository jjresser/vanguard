package com.example.demo;

import com.example.demo.dto.BatchLog;
import com.example.demo.service.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

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

	@Test
	void t(){
		BatchLog b1 = new BatchLog(
				idService.generateUniqueId(),
				111L, """
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
				false,
				"",
				Timestamp.from(Instant.now())
		);
		BatchLog b2 = new BatchLog(
				idService.generateUniqueId(),
				222L, """
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
				true,
				"",
				Timestamp.from(Instant.now())
		);
		List<BatchLog> lsBatchLog = Arrays.asList(b1,b2);
		String sql = "INSERT INTO batch_log (batchId, threadId, jsonContent, success, errorMsg, timeStamp) " +
				"VALUES (?, ?, ?, ?, ?, ?)";
//		template.batchUpdate(sql, perBatch, batchSize, (ps, gameSale) -> {
//			ps.setInt(1, gameSale.getId());
//			ps.setInt(2, gameSale.getGameNo());
//			ps.setString(3, gameSale.getGameName());
//			ps.setString(4, gameSale.getGameCode());
//			ps.setInt(5, gameSale.getType());
//			ps.setBigDecimal(6, gameSale.getCostPrice());
//			ps.setBigDecimal(7, gameSale.getTax());
//			ps.setBigDecimal(8, gameSale.getSalePrice());
//			ps.setTimestamp(9, gameSale.getDateOfSale());
//		});

		jdbcTemplate.batchUpdate(sql,lsBatchLog,5000,(ps,batchLog)->{
			ps.setLong(1,batchLog.getBatchId());
			ps.setLong(2,batchLog.getThreadId());
			ps.setString(3,batchLog.getJsonContent());
			ps.setBoolean(4,batchLog.isSuccess());
			ps.setString(5,batchLog.getErrMsg());
			ps.setTimestamp(6,batchLog.getTimeStamp());
		});
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

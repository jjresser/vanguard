package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Utils.General;
import com.example.demo.Utils.JsonKit;
import com.example.demo.dto.GameSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CsvImportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IdService idService;

    @Value("${spring.batch.size}")
    private int batchSize;

    //@Transactional
    public ConcurrentHashMap<String,Object> importCsv(InputStream csvInputStream) throws Exception {
        int count = 0;
        List<GameSale> gameSales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(csvInputStream, StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // Skip header
            if (line == null) throw new Exception("CSV header missing");

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                GameSale eachRow = new GameSale(
                        Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        fields[3],
                        Integer.parseInt(fields[4]),
                        new BigDecimal(fields[5]),
                        new BigDecimal(fields[6]),
                        new BigDecimal(fields[7]),
                        General.dateTimeStrTotimestamp(fields[8])
                );
                gameSales.add(eachRow);
                count++;
            }

            String sql = """
                INSERT INTO game_sales (
                    id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            List<List<GameSale>> listOfBatches = General.listOfbatches(gameSales,batchSize);
            Long uuidBatchId = idService.generateUniqueId();
            List<String> error = new ArrayList<>();
            listOfBatches.parallelStream().forEach( perBatch ->{
                batchUpdate(uuidBatchId,perBatch,sql,error);
            });

//            listOfBatches.stream().forEach( perBatch ->{
//                batchUpdate(uuidBatchId,perBatch,batchSize,sql,jdbcTemplate);
//            });
            listOfBatches.clear();
            gameSales.clear();

            ConcurrentHashMap<String,Object> cHm = new ConcurrentHashMap<>();
            cHm.put("count",String.valueOf(count));
            cHm.put("error",error);
            System.gc();
            return cHm;
        }
    }

//    private void batchUpdateParallelStream(Long uuidBatchId , List<GameSale> perBatch, int batchSize, String sql ) {
//        //JdbcTemplate template = new JdbcTemplate(dataSource);
//        batchUpdate(uuidBatchId,perBatch,batchSize,sql);
//    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void batchUpdate(Long uuidBatchId ,List<GameSale> perBatch,String sql,List<String> error) {
        try{
        jdbcTemplate.batchUpdate(sql, perBatch, batchSize, (ps, gameSale) -> {
            ps.setInt(1, gameSale.getId());
            ps.setInt(2, gameSale.getGameNo());
            ps.setString(3, gameSale.getGameName());
            ps.setString(4, gameSale.getGameCode());
            ps.setInt(5, gameSale.getType());
            ps.setBigDecimal(6, gameSale.getCostPrice());
            ps.setBigDecimal(7, gameSale.getTax());
            ps.setBigDecimal(8, gameSale.getSalePrice());
            ps.setTimestamp(9, gameSale.getDateOfSale());
        });
        insertLog(uuidBatchId,Thread.currentThread().getId(),JsonKit.toJSONString(perBatch),"",true);
        }catch(DuplicateKeyException e){
            StringBuilder sb = new StringBuilder();
            System.err.println(sb.append(e).append("\n").append(JsonKit.toJSONString(perBatch)));
            insertLog(uuidBatchId,Thread.currentThread().getId(),JsonKit.toJSONString(perBatch),e.toString(),false);
            error.add(e.toString());
        }catch (DataAccessException e) {
            StringBuilder sb = new StringBuilder();
            System.err.println(sb.append(e).append("\n").append(JsonKit.toJSONString(perBatch)));
            insertLog(uuidBatchId,Thread.currentThread().getId(),JsonKit.toJSONString(perBatch),e.toString(),false);
            error.add(e.toString());
        }
    }

    @Transactional
    public boolean deleteDbGameSaleTableData(){
        try {
            jdbcTemplate.execute("truncate game_sales ");
            jdbcTemplate.execute("truncate batch_log ");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public JSONObject getJSONObjectFromQuery(String sql) {
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            JSONObject json = new JSONObject();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String column = rs.getMetaData().getColumnLabel(i);
                Object value = rs.getObject(i);
                json.put(column, value);
            }
            return json;
        });
    }

    public JSONObject getGameNoById(int id) {
        String sql = "SELECT game_no FROM game_sales WHERE id = ?";
        JSONObject result = new JSONObject();

        try {
            String gameNo = jdbcTemplate.queryForObject(sql, String.class, id);
            result.put("game_no", gameNo);
        } catch (EmptyResultDataAccessException e) {
            result.put("game_no", null);
        }

        return result;
    }

    private void insertLog(long batchId, long threadId, String jsonContent,String errorMsg, boolean success) {
        String sql = "INSERT INTO batch_log (batchId, threadId, jsonContent, success, errorMsg, timeStamp) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                batchId,
                threadId,
                jsonContent,
                success,
                errorMsg,
                Timestamp.from(Instant.now())  // current UTC timestamp
        );
    }
}

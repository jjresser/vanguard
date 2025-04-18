package com.example.demo.service;

import com.example.demo.Utils.General;
import com.example.demo.dto.GameSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvImportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Transactional
    public int importCsv(InputStream csvInputStream) throws Exception {
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
            }

            String sql = """
                INSERT INTO game_sales (
                    id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            int batchSize = 5000;
            List<List<GameSale>> batches = General.batchUpdatePartition(gameSales,batchSize);

//            batches.parallelStream().forEach( perBatch ->{
//                batchUpdateParallelStream(perBatch,batchSize,sql);
//            });

            batches.stream().forEach( perBatch ->{
                batchUpdate(perBatch,batchSize,sql,jdbcTemplate);
            });

            return gameSales.size();
        }
    }

    private void batchUpdateParallelStream(List<GameSale> perBatch, int batchSize, String sql) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        batchUpdate(perBatch,batchSize,sql,template);
    }

    private void batchUpdate(List<GameSale> perBatch, int batchSize,String sql,JdbcTemplate template) {
        template.batchUpdate(sql, perBatch, batchSize, (ps, gameSale) -> {
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
    }

    @Transactional
    public boolean deleteDbGameSaleTableData(){
        try {
            jdbcTemplate.execute("truncate game_sales ");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

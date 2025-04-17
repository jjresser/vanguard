package com.example.demo.repository;

import com.example.demo.dto.GameSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class GameSaleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public List<GameSale> getAllSales() {
//        String sql = "SELECT * FROM game_sales";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GameSale.class));
//    }

    public List<GameSale> getAllSales(
             int page,
             int size
    ) {
        int offset = page * size;

        String sql = """
        SELECT id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale
        FROM game_sales
        ORDER BY id ASC
        LIMIT ? OFFSET ?
    """;

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(GameSale.class),
                size,
                offset
                );
    }

    public List<GameSale> getSalesByDateRange(LocalDate from, LocalDate to,int page , int size) {
        int offset = page * size;
        String sql = """
        SELECT id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale 
        FROM game_sales WHERE date_of_sale BETWEEN ? AND ?
        ORDER BY id ASC
        LIMIT ? OFFSET ?
        """;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(GameSale.class),
                Timestamp.valueOf(from.atStartOfDay()),
                Timestamp.valueOf(to.atTime(LocalTime.MAX)),
                size,
                offset
        );
    }

    public List<GameSale> getSalesByPriceFilter(BigDecimal price, String operator, int page , int size) {
        int offset = page * size;
        String sql;
        if (">".equals(operator)) {
            sql =   """
                     SELECT id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale
                     FROM game_sales WHERE sale_price > ?
                     ORDER BY id ASC
                     LIMIT ? OFFSET ?
                    """;
        } else if ("<".equals(operator)) {
            sql =   """
                     SELECT id, game_no, game_name, game_code, type, cost_price, tax, sale_price, date_of_sale
                     FROM game_sales WHERE sale_price < ?
                     ORDER BY id ASC
                     LIMIT ? OFFSET ?
                    """;
        } else {
            throw new IllegalArgumentException("Invalid operator: use '<' or '>'");
        }

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GameSale.class), price, size , offset);
    }
}

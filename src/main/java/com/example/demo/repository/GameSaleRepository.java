package com.example.demo.repository;

import com.example.demo.Dto.GameSale;
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

    public List<GameSale> getAllSales() {
        String sql = "SELECT * FROM game_sales";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GameSale.class));
    }

    public List<GameSale> getSalesByDateRange(LocalDate from, LocalDate to) {
        String sql = "SELECT * FROM game_sales WHERE date_of_sale BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GameSale.class),
                Timestamp.valueOf(from.atStartOfDay()), Timestamp.valueOf(to.atTime(LocalTime.MAX)));
    }

    public List<GameSale> getSalesByPriceFilter(BigDecimal price, String operator) {
        String sql;
        if (">".equals(operator)) {
            sql = "SELECT * FROM game_sales WHERE sale_price > ?";
        } else if ("<".equals(operator)) {
            sql = "SELECT * FROM game_sales WHERE sale_price < ?";
        } else {
            throw new IllegalArgumentException("Invalid operator: use '<' or '>'");
        }

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GameSale.class), price);
    }
}

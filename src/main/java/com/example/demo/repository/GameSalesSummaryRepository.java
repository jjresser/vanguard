package com.example.demo.repository;

import com.example.demo.Dto.DailySalesSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class GameSalesSummaryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DailySalesSummary> getGameCounts(LocalDate from, LocalDate to) {
        String sql = """
            SELECT summary_date, game_no, total_games_sold, total_sales
            FROM game_sales_summary
            WHERE summary_date BETWEEN ? AND ?
        """;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DailySalesSummary.class),
                from, to
        );
    }

    public List<DailySalesSummary> getTotalSales(LocalDate from, LocalDate to) {
        String sql = """
            SELECT summary_date, game_no, total_games_sold, total_sales
            FROM game_sales_summary
            WHERE summary_date BETWEEN ? AND ?
        """;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DailySalesSummary.class),
                from, to
        );
    }

    public List<DailySalesSummary> getTotalSalesByGameNo(int gameNo, LocalDate from, LocalDate to) {
        String sql = """
            SELECT summary_date, game_no, total_games_sold, total_sales
            FROM game_sales_summary
            WHERE game_no = ? AND summary_date BETWEEN ? AND ?
        """;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DailySalesSummary.class),
                gameNo, from, to
        );
    }
}

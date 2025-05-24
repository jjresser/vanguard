package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CronJobService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void executeScheduledTask() {
        try {
            String sql = """
                    INSERT INTO game_sales_summary (summary_date, game_no, total_games_sold, total_sales)
                    SELECT
                        DATE(date_of_sale) AS summary_date,
                        game_no,
                        COUNT(*) AS total_games_sold,
                        SUM(sale_price) AS total_sales
                    FROM game_sales
                    GROUP BY DATE(date_of_sale), game_no
                    ON DUPLICATE KEY UPDATE
                        total_games_sold = VALUES(total_games_sold),
                        total_sales = VALUES(total_sales);
                    """;
            int rowsAffected = jdbcTemplate.update(sql);
            System.out.println("Cron Job Executed. Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

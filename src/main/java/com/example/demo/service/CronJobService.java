package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CronJobService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * This method will run every 1 minute
     */
    @Transactional
    //@Scheduled(cron = "0 */${spring.cronjob.minutes} * * * *")// Runs at 0th second of every minute
    public void executeScheduledTask() {
        try {
            //jdbcTemplate.execute("truncate game_sales_summary");
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

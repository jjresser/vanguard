INSERT INTO game_sales_summary (summary_date, game_no, total_games_sold, total_sales)
SELECT
    DATE(date_of_sale) AS summary_date,
    game_no,
    COUNT(*) AS total_games_sold,
    SUM(sale_price) AS total_sales
FROM games_sales
GROUP BY DATE(date_of_sale), game_no
ON DUPLICATE KEY UPDATE
    total_games_sold = VALUES(total_games_sold),
    total_sales = VALUES(total_sales);

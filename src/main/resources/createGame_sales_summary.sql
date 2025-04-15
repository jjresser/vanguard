CREATE TABLE game_sales_summary (
    summary_date DATE NOT NULL,
    game_no INT NOT NULL,
    total_games_sold INT NOT NULL,
    total_sales DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (summary_date, game_no)
);

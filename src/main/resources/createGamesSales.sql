CREATE TABLE game_sales (
    id INT PRIMARY KEY AUTO_INCREMENT,           -- Running number starting from 1
    game_no INT NOT NULL CHECK (game_no BETWEEN 1 AND 100),
    game_name VARCHAR(20) NOT NULL,
    game_code VARCHAR(5) NOT NULL,
    type TINYINT NOT NULL CHECK (type IN (1, 2)), -- 1 = Online, 2 = Offline
    cost_price DECIMAL(6,2) NOT NULL CHECK (cost_price <= 100.00),
    tax DECIMAL(5,2) NOT NULL DEFAULT 9.00,       -- 9% tax, fixed
    sale_price DECIMAL(6,2) NOT NULL,             -- Includes tax
    date_of_sale TIMESTAMP NOT NULL
);

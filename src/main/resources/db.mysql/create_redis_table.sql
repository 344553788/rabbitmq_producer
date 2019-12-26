CREATE TABLE IF NOT EXISTS redis_table (
    id INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    host VARCHAR(56),
    port INT(10),
    db INT(4),
    user_name VARCHAR(64),
    pass VARCHAR(255),
    min_idle INT(4),
    max_idle INT(4),
    max_active INT(4),
    max_wait INT(4),
    time_out INT(10)
); 


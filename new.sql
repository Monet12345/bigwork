CREATE TABLE User_db (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      user_id VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      salt VARCHAR(255) NOT NULL,
                      user_name VARCHAR(255) NOT NULL,
                      nick_name VARCHAR(255) NOT NULL,
                      workspace_id VARCHAR(255) NOT NULL,
                      gmt_creat DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      gmt_update DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

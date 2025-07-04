CREATE TABLE USERS (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    token VARCHAR(255),
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    active BOOLEAN
);

CREATE TABLE PHONES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(20),
    city_code VARCHAR(10),
    country_code VARCHAR(10),
    user_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES USERS(id)
);



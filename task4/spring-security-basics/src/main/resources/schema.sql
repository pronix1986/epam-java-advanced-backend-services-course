DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
username VARCHAR(50)  NOT NULL,
password VARCHAR(100) NOT NULL,
enabled  INT      NOT NULL DEFAULT 1,
PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities
(
username  VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
FOREIGN KEY (username) REFERENCES users (username)
);
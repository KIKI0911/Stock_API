DROP TABLE IF EXISTS stocks;

CREATE TABLE stocks (
  id int unsigned AUTO_INCREMENT,
  symbol VARCHAR(100) NOT NULL,
  companyName VARCHAR(100) NOT NULL,
  quantity int NOT NULL,
  price int NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO stocks (id, symbol, companyName, quantity, price) VALUES (1, 7203, "トヨタ自動車", 100, 2640);
INSERT INTO stocks (id, symbol, companyName, quantity, price) VALUES (2, 9861, "吉野家ホールディングス", 100, 3131);
INSERT INTO stocks (id, symbol, companyName, quantity, price) VALUES (3, 3197, "スカイラークホールディングス", 100, 2059);
INSERT INTO stocks (id, symbol, companyName, quantity, price) VALUES (4, 9101, "日本郵船", 100, 4333);
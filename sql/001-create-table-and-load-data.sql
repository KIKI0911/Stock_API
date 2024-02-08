DROP TABLE IF EXISTS stocks;

CREATE TABLE stocks (
  id int unsigned AUTO_INCREMENT,
  symbol VARCHAR(10) NOT NULL,
  companyName VARCHAR(255) NOT NULL,
  quantity int NOT NULL,
  price int NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO stocks (symbol, companyName, quantity, price) VALUES ("7203","トヨタ自動車",100, 2640);
INSERT INTO stocks (symbol, companyName, quantity, price) VALUES ("9861", "吉野家ホールディングス",100, 3131);
INSERT INTO stocks (symbol, companyName, quantity, price) VALUES ("3197", "スカイラークホールディングス", 100, 2059);
INSERT INTO stocks (symbol, companyName, quantity, price) VALUES ("9101", "日本郵船",100, 4333);

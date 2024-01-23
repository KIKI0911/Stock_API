package com.user.stock.mapper;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.user.stock.entity.Stock;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockMapperTest {

    @Autowired
    StockMapper stockMapper;

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    void すべての株式が取得できること () {
        List<Stock> stocks = stockMapper.findAllStocks();
        assertThat(stocks)
                .hasSize(4)
                .contains(
                        new Stock(1,7203,"トヨタ自動車",100, 2640),
                        new Stock(2,9861, "吉野家ホールディングス",100, 3131),
                        new Stock(3,3197, "スカイラークホールディングス", 100, 2059),
                        new Stock(4,9101, "日本郵船",100, 4333));

    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    void 指定したシンボルの株式が取得できること() {
        assertThat(stockMapper.findStockBySymbol(7203))
                .contains(new Stock(1,7203,"トヨタ自動車",100, 2640));
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    void 存在しないシンボルを指定する場合に空の情報を獲得すること() {
        Optional<Stock> stock = stockMapper.findStockBySymbol(9999);
        assertThat(stock).isEmpty();
    }
}
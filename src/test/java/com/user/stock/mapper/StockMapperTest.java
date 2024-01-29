package com.user.stock.mapper;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
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
                .containsExactlyInAnyOrder(
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
    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet(value = "datasets/insertStockTest.yml", ignoreCols = "id")
    public void 正常に新規の株式が登録できること() {
        Stock stock = new Stock(5, 2897, "日清食品", 100, 5160);
        stockMapper.insertStock(stock);
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet("datasets/updateStockTest.yml")
    @Transactional
    public void パラメーターがnullでない場合は正しく更新されることを確認する() {
        Stock stock = new Stock(1, 7203, "トヨタ自動車", 500, 3000);
        stockMapper.updateStock(stock);
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet("datasets/stocks.yml")
    @Transactional
    public void パラメーターがnullの場合に更新されないことを確認する() {
        Stock stock = new Stock(1, null, null, null, null);
        stockMapper.updateStock(stock);
    }
}

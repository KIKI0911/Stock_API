package com.user.stock.service;

import com.user.stock.entity.Stock;
import com.user.stock.exception.StockAlreadyExistsException;
import com.user.stock.exception.StockNotFoundException;
import com.user.stock.mapper.StockMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    StockServiceImpl stockServiceImpl;

    @Mock
    StockMapper stockMapper;

    @Test
    public void 存在する株式データを全件取得すること() {

        List<Stock> stock = List.of(
                new Stock(1, 7203, "トヨタ自動車", 100, 2640),
                new Stock(2, 9861, "吉野家ホールディングス", 100, 3131),
                new Stock(3, 3197, "スカイラークホールディングス", 100, 2059),
                new Stock(4, 9101, "日本郵船", 100, 4333)
        );
        doReturn(stock).when(stockMapper).findAllStocks();

        List<Stock> actual = stockServiceImpl.findAllStocks();
        assertThat(actual).isEqualTo(stock);
        verify(stockMapper).findAllStocks();
    }

    @Test
    public void 存在する株式のシンボルを指定した時に特定の株式を返すこと() throws StockNotFoundException {
        doReturn(Optional.of(new Stock(1, 7203, "トヨタ自動車", 100, 2640))).when(stockMapper).findStockBySymbol(7203);

        Stock actual = stockServiceImpl.findStockBySymbol(7203);
        assertThat(actual).isEqualTo(new Stock(1, 7203, "トヨタ自動車", 100, 2640));
        verify(stockMapper, times(1)).findStockBySymbol(7203);
    }

    @Test
    public void 存在しない株式のシンボルを指定したときに例外処理が返されること() throws StockNotFoundException {

        doReturn(Optional.empty()).when(stockMapper).findStockBySymbol(7000);
        assertThrows(StockNotFoundException.class, () -> {
            stockServiceImpl.findStockBySymbol(7000);
        });
        verify(stockMapper, times(1)).findStockBySymbol(7000);
    }

    @Test
    public void 正常に新規の株式が登録できること() {
        Stock stock = new Stock(null, 2897, "日清食品", 100, 5160);

        doNothing().when(stockMapper).insertStock(stock);
        assertThat(stockServiceImpl.insertStock(2897, "日清食品", 100, 5160)).isEqualTo(stock);
        verify(stockMapper).insertStock(stock);
    }

    @Test
    public void すでに存在する株式を登録する時にエラーが返されること() {
        when(stockMapper.findStockBySymbol(7203)).thenReturn(Optional.of(new Stock(1, 7203, "トヨタ自動車", 100, 2640)));
        assertThrows(StockAlreadyExistsException.class, () -> {
            stockServiceImpl.insertStock(7203, "トヨタ自動車", 100, 2640);
        });
    }
}

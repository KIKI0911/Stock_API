package com.user.stock.service;

import com.user.stock.exception.StockAlreadyExistsException;
import com.user.stock.mapper.StockMapper;
import com.user.stock.exception.StockNotFoundException;
import com.user.stock.entity.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;

    @Autowired
    public StockServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    //GET
    @Override
    public List<Stocks> findAllStocks() {
        return stockMapper.findAllStocks();
    }

    @Override
    public Stocks findStockBySymbol(Integer symbol) {
        Optional<Stocks> stocks = stockMapper.findStockBySymbol(symbol);
        return stocks.orElseThrow(() -> new StockNotFoundException("Stock not found:" + symbol));
    }

    @Override
    public Stocks insertStock(Integer symbol, String companyName, Integer quantity, Integer price) {
        Optional<Stocks> stockOptional = this.stockMapper.findStockBySymbol(symbol);
        if (stockOptional.isPresent()) {
            throw new StockAlreadyExistsException("Stock already exists");
        }
        Stocks stocks = new Stocks(null, symbol, companyName, quantity, price);
        stockMapper.insertStock(stocks);
        return stocks;
    }
}

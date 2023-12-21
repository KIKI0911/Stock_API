package com.user.stock.service;

import com.user.stock.mapper.StockMapper;
import com.user.stock.exception.StockNotFoundException;
import com.user.stock.entity.Stocks;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;

    public StockServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    //GET
    @Override
    public List<Stocks> findAllStocks(){
        return stockMapper.findAllStocks();
    }

    @Override
    public Stocks findCertainStock(Integer symbol){
        Optional<Stocks> stocks = stockMapper.findCertainStock(symbol);
        return stocks.orElseThrow(() -> new StockNotFoundException("Stock not found:" + symbol));
    }
}

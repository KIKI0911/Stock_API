package com.user.stock.service;


import com.user.stock.entity.Stocks;

import java.util.List;

public interface StockService {

    List<Stocks> findAllStocks();

    public Stocks findCertainStock(Integer symbol);
}

package com.user.stock.service;


import com.user.stock.entity.Stocks;

import java.util.List;

public interface StockService {

    List<Stocks> findAllStocks();

    public Stocks findStockBySymbol(Integer symbol);
}

package com.user.stock.service;


import com.user.stock.controller.StockRequest;
import com.user.stock.entity.Stocks;

import java.util.List;

public interface StockService {

    List<Stocks> findAllStocks();

    public Stocks findStockBySymbol(Integer symbol);

    public Stocks insertedStock(Integer symbol, String companyName, Integer quantity, Integer price);

}

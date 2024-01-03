package com.user.stock.service;


import com.user.stock.controller.StockRequest;
import com.user.stock.entity.Stocks;

import java.util.List;

public interface StockService {

    List<Stocks> findAllStocks();

    public Stocks findStockBySymbol(Integer symbol);

    public Stocks insertStock(Integer symbol, String companyName, Integer quantity, Integer price);

    public Stocks updateStock(Integer symbol, StockRequest stockRequest);
}

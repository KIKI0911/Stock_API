package com.user.stock.service;


import com.user.stock.controller.request.StockRequest;
import com.user.stock.entity.Stock;

import java.util.List;

public interface StockService {

    List<Stock> findAllStocks();

    public Stock findStockBySymbol(Integer symbol);

    public Stock insertStock(Integer symbol, String companyName, Integer quantity, Integer price);

    public Stock updateStock(Integer symbol, StockRequest stockRequest);

    public Stock deleteStock(Integer symbol);
}


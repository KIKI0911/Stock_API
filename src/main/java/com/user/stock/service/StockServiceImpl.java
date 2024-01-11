package com.user.stock.service;

import com.user.stock.controller.request.StockRequest;
import com.user.stock.exception.StockAlreadyExistsException;
import com.user.stock.mapper.StockMapper;
import com.user.stock.exception.StockNotFoundException;
import com.user.stock.entity.Stock;
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
    public List<Stock> findAllStocks() {
        return stockMapper.findAllStocks();
    }

    @Override
    public Stock findStockBySymbol(Integer symbol) {
        Optional<Stock> stocks = stockMapper.findStockBySymbol(symbol);
        return stocks.orElseThrow(() -> new StockNotFoundException("Stock not found:" + symbol));
    }

    @Override
    public Stock insertStock(Integer symbol, String companyName, Integer quantity, Integer price) {
        Optional<Stock> stockOptional = this.stockMapper.findStockBySymbol(symbol);
        if (stockOptional.isPresent()) {
            throw new StockAlreadyExistsException("Stock already exists");
        }
        Stock stock = new Stock(null, symbol, companyName, quantity, price);
        stockMapper.insertStock(stock);
        return stock;
    }

    @Override
    public Stock updateStock(Integer symbol, StockRequest stockRequest) {
        Stock existingStock = this.stockMapper.findStockBySymbol(symbol).orElseThrow(() -> new StockNotFoundException("Stock not found:" + symbol));

        existingStock.setSymbol(stockRequest.getSymbol());
        existingStock.setCompanyName(stockRequest.getCompanyName());
        existingStock.setQuantity(stockRequest.getQuantity());
        existingStock.setPrice(stockRequest.getPrice());

        stockMapper.updateStock(existingStock);
        return existingStock;
    }

    @Override
    public Stock deleteStock(Integer symbol) {

        Stock stock = this.stockMapper.findStockBySymbol(symbol).orElseThrow(() -> new StockNotFoundException("Stock not found:" + symbol));
        stockMapper.deleteStock(symbol);
        return stock;
    }
}

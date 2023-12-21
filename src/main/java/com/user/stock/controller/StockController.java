package com.user.stock.controller;

import com.user.stock.service.StockService;
import com.user.stock.entity.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stocks> findAllStocks() {
        return stockService.findAllStocks();
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stocks> findStock(@PathVariable("symbol") Integer symbol) {
        Stocks stocks = stockService.findCertainStock(symbol);
        return ResponseEntity.ok(stocks);
    }
}

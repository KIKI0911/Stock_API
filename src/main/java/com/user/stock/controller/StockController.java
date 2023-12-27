package com.user.stock.controller;

import com.user.stock.service.StockService;
import com.user.stock.entity.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Stocks> findStockBySymbol(@PathVariable("symbol") Integer symbol) {
        Stocks stocks = stockService.findStockBySymbol(symbol);
        return ResponseEntity.ok(stocks);
    }

    @PostMapping
    public ResponseEntity<StockResponse> insertStock(@RequestBody StockRequest stockRequest, UriComponentsBuilder uriBuilder) {
        Stocks stocks = stockService.insertedStock(stockRequest.getSymbol(), stockRequest.getCompanyName(), stockRequest.getQuantity(), stockRequest.getPrice());
        URI location = uriBuilder.path("/users").buildAndExpand(stocks.getId()).toUri();
        StockResponse body = new StockResponse("Stock created");
        return ResponseEntity.created(location).body(body);
    }
}

package com.user.stock.controller;

import com.user.stock.controller.request.StockRequest;
import com.user.stock.controller.response.StockResponse;
import com.user.stock.service.StockService;
import com.user.stock.entity.Stock;
import org.apache.ibatis.annotations.Delete;
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
    public List<Stock> findAllStocks() {
        return stockService.findAllStocks();
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> findStockBySymbol(@PathVariable("symbol") Integer symbol) {
        Stock stock = stockService.findStockBySymbol(symbol);
        return ResponseEntity.ok(stock);
    }

    @PostMapping
    public ResponseEntity<StockResponse> insertStock(@RequestBody StockRequest stockRequest, UriComponentsBuilder uriBuilder) {
        Stock stock = stockService.insertStock(stockRequest.getSymbol(), stockRequest.getCompanyName(), stockRequest.getQuantity(), stockRequest.getPrice());
        URI location = uriBuilder.path("/users").buildAndExpand(stock.getId()).toUri();
        StockResponse body = new StockResponse("Stock created");
        return ResponseEntity.created(location).body(body);
    }

    @PatchMapping("/{symbol}")
    public ResponseEntity<StockResponse> updateStock(@PathVariable("symbol") Integer symbol, @RequestBody StockRequest stockRequest, UriComponentsBuilder uriBuilder) {
        Stock stock = stockService.updateStock(symbol, stockRequest);
        URI location = uriBuilder.path("/users/{symbol}").buildAndExpand(stock.getSymbol()).toUri();
        StockResponse body = new StockResponse("Stock updated");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<StockResponse> deleteStock(@PathVariable("symbol") Integer symbol, UriComponentsBuilder uriBuilder) {
        Stock stock = stockService.deleteStock(symbol);
        URI location = uriBuilder.path("/users/{symbol}").buildAndExpand(stock.getSymbol()).toUri();
        StockResponse body = new StockResponse("Stock deleted");
        return ResponseEntity.ok(body);
    }
}
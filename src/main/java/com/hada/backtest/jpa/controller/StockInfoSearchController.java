package com.hada.backtest.jpa.controller;

import com.hada.backtest.jpa.service.StockInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class StockInfoSearchController {
    private final StockInfoService stockInfoService;

    public StockInfoSearchController(StockInfoService stockInfoService) {
        this.stockInfoService = stockInfoService;
    }

    @GetMapping("/search-stock")
    public ResponseEntity<List<String>> searchStock(@RequestParam String searchTerm) {
        List<String> searchResult = stockInfoService.getStockByQuery(searchTerm);
        return ResponseEntity.ok(searchResult);
    }
}

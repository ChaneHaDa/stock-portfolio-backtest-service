package com.hada.backtest.jpa.controller;

import com.hada.backtest.jpa.service.StockInfoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockInfoController {

    private final StockInfoService stockInfoService;

    public StockInfoController(StockInfoService stockInfoService) {
        this.stockInfoService = stockInfoService;
    }

    @GetMapping("/search-stock")
    public ResponseEntity<List<String>> searchStock(@RequestParam("searchTerm") String searchTerm) {
        List<String> searchResult = stockInfoService.getStockByQuery(searchTerm);
        return ResponseEntity.ok(searchResult);
    }
}

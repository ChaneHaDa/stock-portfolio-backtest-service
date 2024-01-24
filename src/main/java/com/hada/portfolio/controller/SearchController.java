package com.hada.portfolio.controller;

import com.hada.portfolio.stock.info.StockInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class SearchController {
    private final StockInfoService stockInfoService;

    public SearchController(StockInfoService stockInfoService) {
        this.stockInfoService = stockInfoService;
    }
    @GetMapping("/searchStock")
    public ResponseEntity<List<String>> searchStock(@RequestParam String searchTerm) {
        List<String> searchResult = stockInfoService.findAllByQuery(searchTerm);
        return ResponseEntity.ok(searchResult);
    }
}

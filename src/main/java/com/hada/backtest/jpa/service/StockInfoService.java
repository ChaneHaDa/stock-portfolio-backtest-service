package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.entity.StockInfo;
import com.hada.backtest.jpa.repository.StockInfoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockInfoService {

    private final StockInfoRepository stockInfoRepository;

    public StockInfoService(StockInfoRepository stockInfoRepository) {
        this.stockInfoRepository = stockInfoRepository;
    }

    public List<String> getStockByQuery(String query) {
        List<StockInfo> stockInfos;
        if (query.charAt(0) <= '9' && query.charAt(0) >= '0') {
            stockInfos = stockInfoRepository.findByCodeContaining(query);
        } else {
            stockInfos = stockInfoRepository.findByNameContaining(query);
        }

        List<String> result = new ArrayList<>();
        for (StockInfo stockInfo : stockInfos) {
            result.add(stockInfo.getName() + " (" + stockInfo.getCode() + ")");
        }

        return result;
    }
}
package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.dto.StockPriceDTO;
import com.hada.backtest.jpa.repository.StockPriceRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockPriceService {

    private final StockPriceRepository stockPriceRepository;

    public StockPriceService(StockPriceRepository stockPriceRepository) {
        this.stockPriceRepository = stockPriceRepository;
    }

    public List<StockPriceDTO> getStockPriceListByCode(String code) {
        List<StockPriceDTO> stockPriceList = stockPriceRepository.findAllByCode(code).stream()
                .map(StockPriceDTO::fromEntity).toList();
        return stockPriceList;
    }

}

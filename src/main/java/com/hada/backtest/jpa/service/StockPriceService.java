package com.hada.backtest.jpa.service;

import com.hada.backtest.jpa.dto.StockPriceDTO;
import com.hada.backtest.jpa.entity.StockPrice;
import com.hada.backtest.jpa.repository.StockPriceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockPriceService {
    private final StockPriceRepository stockPriceRepository;
    public StockPriceService(StockPriceRepository stockPriceRepository) {
        this.stockPriceRepository = stockPriceRepository;
    }

    public List<StockPriceDTO> getStockPriceListByCode(String code) {
        List<StockPriceDTO> stockPriceList = stockPriceRepository.findAllByCode(code).stream().map(StockPriceDTO::fromEntity).toList();
        return stockPriceList;
    }

    public List<StockPrice> findAllByCodeAndYear(String code, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return stockPriceRepository.findByCodeAndDateBetween(code, startDate, endDate);
    }

}

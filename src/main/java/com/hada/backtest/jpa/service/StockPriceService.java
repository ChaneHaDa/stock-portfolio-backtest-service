package com.hada.backtest.jpa.service;

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

    public List<StockPrice> getStockPriceByCode(String code) {
        return stockPriceRepository.findAllByCode(code);
    }

    public List<List<Long>> getPriceByYears(String code, int startYear, int endYear) {
        List<List<Long>> priceByYearsList = new ArrayList<>();
        List<StockPrice> stockPriceList = stockPriceRepository.findAllByCode(code);

        for (int i = 0; i <= endYear - startYear; i++) {
            List<Long> monthList = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                monthList.add(0L);
            }
            priceByYearsList.add(monthList);
        }

        stockPriceList.forEach(stockPrice -> {
            priceByYearsList.get(stockPrice.getDate().getYear() - startYear).set(stockPrice.getDate().getMonthValue() - 1, stockPrice.getPrice());
        });

        return priceByYearsList;
    }

    public List<StockPrice> findAllByCodeAndYear(String code, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return stockPriceRepository.findByCodeAndDateBetween(code, startDate, endDate);
    }

    public List<Long> getPricesByItmsNmAndYear(String itmsNm, int year) {
        List<StockPrice> stockPrices = findAllByCodeAndYear(itmsNm, year);

        List<Long> clprs = new ArrayList<>();

        for(int i = 0 ; i < 12 ; i++) {
            clprs.add(0L);
        }

        stockPrices.forEach(stockPrice -> {
            clprs.set(stockPrice.getDate().getMonthValue() - 1, stockPrice.getPrice());
        });
        return clprs;
    }

}

package com.hada.backtest.stock.price;

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

    public List<StockPrice> saveAll(List<StockPrice> stockPrices) {
        return stockPriceRepository.saveAll(stockPrices);
    }

    public Page<StockPrice> findAll(int page, boolean isDesc) {
        List<Sort.Order> sorts = new ArrayList<>();
        if(isDesc) {
            sorts.add(Sort.Order.desc("basDt"));
        }else {
            sorts.add(Sort.Order.asc("basDt"));
        }
        Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
        return stockPriceRepository.findAll(pageable);
    }

    public Page<StockPrice> findAllByItmsNmOrSrtnCd(String query, int page, boolean isDesc) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (isDesc) {
            sorts.add(Sort.Order.desc("basDt"));
        } else {
            sorts.add(Sort.Order.asc("basDt"));
        }

        Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));

        if (query.charAt(0) <= '9' && query.charAt(0) >= '0') {
            return stockPriceRepository.findAllBySrtnCd(query, pageable);
        } else {
            return stockPriceRepository.findAllByItmsNm(query, pageable);
        }
    }

    public Long getPriceByItmsNmAndMonth(String itmsNm, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<StockPrice> stockPrices = stockPriceRepository.findByItmsNmAndBasDtBetween(itmsNm, startDate, endDate);
        if(stockPrices.size() > 0) {
            return stockPrices.get(0).getClpr();
        }
        return 0L;
    }

    public List<StockPrice> findAllByItmsNmAndYear(String itmsNm, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return stockPriceRepository.findByItmsNmAndBasDtBetween(itmsNm, startDate, endDate);
    }

    public List<Long> getPricesByItmsNmAndYear(String itmsNm, int year) {
        List<StockPrice> stockPrices = findAllByItmsNmAndYear(itmsNm, year);

        List<Long> clprs = new ArrayList<>();

        for(int i = 0 ; i < 12 ; i++) {
            clprs.add(0L);
        }

        stockPrices.forEach(stockPrice -> {
            clprs.set(stockPrice.getBasDt().getMonthValue() - 1, stockPrice.getClpr());
        });
        return clprs;
    }

}

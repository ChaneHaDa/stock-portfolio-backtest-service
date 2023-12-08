package com.hada.portfolio.stock.price;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockPriceService {
    private final StockPriceRepository stockPriceRepository;
    public StockPriceService(StockPriceRepository stockPriceRepository) {
        this.stockPriceRepository = stockPriceRepository;
    }

    public StockPrice save(StockPrice stockPrice) {
        return stockPriceRepository.save(stockPrice);
    }

    public List<StockPrice> findByItmsNm(String itmsNm) {
        return stockPriceRepository.findByItmsNm(itmsNm);
    }

    public List<StockPrice> findByBasDt(LocalDate basDt) {
        return stockPriceRepository.findByBasDt(basDt);
    }

    public List<StockPrice> findByItmsNmAndBasDtBetween(String itmsNm, LocalDate startDate, LocalDate endDate) {
        return stockPriceRepository.findByItmsNmAndBasDtBetween(itmsNm, startDate, endDate);
    }

}

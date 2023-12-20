package com.hada.portfolio.stock.price;

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

    public StockPrice save(StockPrice stockPrice) {
//        if(stockPriceRepository.findByItmsNmAndBasDt(stockPrice.getItmsNm(), stockPrice.getBasDt()) == null) {
//            return stockPriceRepository.save(stockPrice);
//        }
//        return null;

        return stockPriceRepository.save(stockPrice);
    }

    public List<StockPrice> saveAll(List<StockPrice> stockPrices) {
        return stockPriceRepository.saveAll(stockPrices);
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
}

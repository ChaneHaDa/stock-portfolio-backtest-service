package com.hada.portfolio.stock.info;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoService {
    private final StockInfoRepository stockInfoRepository;
    public StockInfoService(StockInfoRepository stockInfoRepository) {
        this.stockInfoRepository = stockInfoRepository;
    }

    public StockInfo save(StockInfo stockInfo) {
        if(stockInfoRepository.findByItmsNm(stockInfo.getItmsNm()) == null) {
            return stockInfoRepository.save(stockInfo);
        }
        return null;
    }

    public List<StockInfo> findAll() {
        return stockInfoRepository.findAll();
    }

    public Page<StockInfo> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return stockInfoRepository.findAll(pageable);
    }

    public StockInfo findByItmsNmOrSrtnCd(String query) {
        if(query.charAt(0) <= '9' && query.charAt(0) >= '0') {
            return stockInfoRepository.findBySrtnCd(query);
        }else{
            return stockInfoRepository.findByItmsNm(query);
        }
    }

    public Page<StockInfo> findAllByMrktCtg(String filter, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return stockInfoRepository.findAllByMrktCtg(filter, pageable);
    }
}

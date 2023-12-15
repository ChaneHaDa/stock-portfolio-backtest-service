package com.hada.portfolio.stock.info;

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

    public StockInfo findByItmsNm(String itmsNm) {
        return stockInfoRepository.findByItmsNm(itmsNm);
    }

    public StockInfo findBySrtnCd(String srtnCd) {
        return stockInfoRepository.findBySrtnCd(srtnCd);
    }

    public List<StockInfo> findAll() {
        return stockInfoRepository.findAll();
    }

}

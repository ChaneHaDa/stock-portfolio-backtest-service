package com.hada.portfolio.stock.info;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
    StockInfo findByItmsNm(String itmsNm);
    StockInfo findBySrtnCd(String srtnCd);
}

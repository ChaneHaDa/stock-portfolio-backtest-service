package com.hada.portfolio.stock.info;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
    StockInfo findByItmsNm(String itmsNm);
    StockInfo findBySrtnCd(String srtnCd);
    List<StockInfo> findByItmsNmContaining(String keyword);
    List<StockInfo> findBySrtnCdContaining(String keyword);
//    Page<StockInfo> findAllByMrktCtg(String filter, Pageable pageable);
}

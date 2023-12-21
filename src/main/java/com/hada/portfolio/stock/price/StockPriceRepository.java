package com.hada.portfolio.stock.price;

import com.hada.portfolio.stock.info.StockInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    List<StockPrice> findByItmsNm(String itmsNm);
    List<StockPrice> findByBasDt(LocalDate basDt);
    List<StockPrice> findByItmsNmAndBasDtBetween(String itmsNm, LocalDate startDate, LocalDate endDate);
    StockPrice findByItmsNmAndBasDt(String itmsNm, LocalDate basDt);
    Page<StockPrice> findAllBySrtnCd(String query, Pageable pageable);
    Page<StockPrice> findAllByItmsNm(String query, Pageable pageable);

}

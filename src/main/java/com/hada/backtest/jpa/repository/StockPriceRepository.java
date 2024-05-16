package com.hada.backtest.jpa.repository;

import com.hada.backtest.jpa.entity.StockPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    List<StockPrice> findByCodeAndDateBetween(String itmsNm, LocalDate startDate, LocalDate endDate);
    List<StockPrice> findAllByCode(String code);
}

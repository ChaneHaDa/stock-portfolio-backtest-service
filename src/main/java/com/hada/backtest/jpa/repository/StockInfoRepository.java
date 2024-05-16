package com.hada.backtest.jpa.repository;

import com.hada.backtest.jpa.entity.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockInfoRepository extends JpaRepository<StockInfo, Long> {
    List<StockInfo> findByCodeContaining(String keyword);
    List<StockInfo> findByNameContaining(String keyword);
}

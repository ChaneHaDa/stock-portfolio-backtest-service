package com.hada.backtest;

import com.hada.backtest.jpa.dto.PortfolioInputDTO;
import com.hada.backtest.jpa.dto.PortfolioItemDTO;
import com.hada.backtest.jpa.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PortfolioApplicationTests {
    @Autowired
    private PortfolioService portfolioService;

    @Test
    public void test1() {
        List<PortfolioItemDTO> items = new ArrayList<>();
        items.add(new PortfolioItemDTO("900110", "aaaa", 1));
        items.add(new PortfolioItemDTO("900270", "aaaa", 0.1));
        PortfolioInputDTO portfolioInputDTO = new PortfolioInputDTO("test", "test", items);
        portfolioService.savePortfolio(portfolioInputDTO, null);
    }
}

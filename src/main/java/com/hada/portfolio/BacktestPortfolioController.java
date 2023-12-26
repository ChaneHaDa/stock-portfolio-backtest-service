package com.hada.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backtest-portfolio")
public class BacktestPortfolioController {

    @GetMapping("")
    public String index() {
        return "backtest_portfolio";
    }

}

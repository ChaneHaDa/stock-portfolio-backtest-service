package com.hada.backtest.controller;

import com.hada.backtest.dto.BacktestInputDTO;
import com.hada.backtest.dto.BacktestItemDTO;
import com.hada.backtest.service.BacktestService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backtest-portfolio")
public class BacktestController {
    
    private final BacktestService backtestService;

    public BacktestController(BacktestService backtestService) {
        this.backtestService = backtestService;
    }

    @GetMapping("")
    public String getBacktest(Model model) {
        List<BacktestItemDTO> items = new ArrayList<>();
        items.add(new BacktestItemDTO("삼성전자 (005930)", 0.5));
        model.addAttribute("backtestInputDTO", new BacktestInputDTO("2020", "2023", 100000, items, 1));
        return "backtest_portfolio";
    }

    @PostMapping("")
    public String postBacktest(@ModelAttribute BacktestInputDTO backtestInputDTO, Model model) {
        model.addAttribute("backtestInputDTO", backtestInputDTO);
        model.addAttribute("backtestReturnDTO", backtestService.getBacktestReturnDTO(backtestInputDTO));
        return "backtest_portfolio";
    }

}

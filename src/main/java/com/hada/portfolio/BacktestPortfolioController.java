package com.hada.portfolio;


import com.hada.portfolio.stock.price.StockPriceService;
import com.hada.portfolio.utils.RorCalculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backtest-portfolio")
public class BacktestPortfolioController {

    private final StockPriceService stockPriceService;

    public BacktestPortfolioController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    @GetMapping("")
    public String index() {
        return "backtest_portfolio";
    }

    @PostMapping("")
    public String indexPost(@RequestParam HashMap<String, String> params, Model model) {
        System.out.println(params);

        List<Double> weights = new ArrayList<>();

        String stock1 = params.get("input1");
        String stock2 = params.get("input3");

        List<Double> stock1RorList = RorCalculator.getRorList(stockPriceService.getPricesByItmsNmAndYear(stock1,2023), true);
        List<Double> stock2RorList = RorCalculator.getRorList(stockPriceService.getPricesByItmsNmAndYear(stock2,2023), true);

        weights.add(Double.parseDouble(params.get("input2")));
        weights.add(Double.parseDouble(params.get("input4")));

        List<Double> portfolioRorList = RorCalculator.getPortfolioRorList(List.of(stock1RorList, stock2RorList), weights);


        Map<String, Object> data = new HashMap<>();
        portfolioRorList.add(0, 0.0);

        data.put("periods", List.of( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        data.put("returns", portfolioRorList);
        data.put("amount", RorCalculator.getCashByRorList(portfolioRorList, 10000000));

        model.addAttribute("data", data);
        return "backtest_portfolio";
    }

}

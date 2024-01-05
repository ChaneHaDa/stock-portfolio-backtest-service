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
    public String index(Model model) {
        model.addAttribute("count", 1);
        model.addAttribute("startAmount", 10000000);
        return "backtest_portfolio";
    }

    @PostMapping("")
    public String indexPost(@RequestParam HashMap<String, String> params, Model model) {

        for (String key : params.keySet()) {
            String value = params.get(key);
            model.addAttribute(key, value);
        }

        List<String> stockNames = new ArrayList<>();
        List<Double> weights = new ArrayList<>();


        for(int i = 1 ; i <= Integer.parseInt(params.get("count")) ; i++){
            stockNames.add(params.get("stock" + i));
            weights.add(Double.parseDouble(params.get("weight" + i)));
        }

        List<List<Double>> rorList = new ArrayList<>();
        for(String stockName : stockNames){
            rorList.add(RorCalculator.getRorList(stockPriceService.getPricesByItmsNmAndYear(stockName, 2023), true));
        }

        List<Double> portfolioRorList = RorCalculator.getPortfolioRorList(rorList, weights);

        Map<String, Object> backtestResult = new HashMap<>();
        backtestResult.put("startAmount", params.get("startAmount"));
        backtestResult.put("totalRor", RorCalculator.getTotalRor(portfolioRorList));
        backtestResult.put("endAmount", Double.valueOf(params.get("startAmount")) * (1 + RorCalculator.getTotalRor(portfolioRorList)) / 100);

        model.addAttribute("backtestResult", backtestResult);

        return "backtest_portfolio";
    }

}

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
        model.addAttribute("count", 3);
        return "backtest_portfolio";
    }

    @PostMapping("")
    public String indexPost(@RequestParam HashMap<String, String> params, Model model) {
        System.out.println(params);

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

        Map<String, Object> data = new HashMap<>();
        portfolioRorList.add(0, 0.0);

        Map<String, Object> portfolioForm = new HashMap<>();
        portfolioForm.put("startAmount", "111111");

        model.addAttribute("portfolioForm", portfolioForm);

        data.put("periods", List.of( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        data.put("returns", portfolioRorList);
        data.put("amount", RorCalculator.getCashByRorList(portfolioRorList, 10000000));

        model.addAttribute("data", data);

        return "backtest_portfolio";
    }

}

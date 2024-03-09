    package com.hada.backtest.controller;


    import com.hada.backtest.stock.price.StockPriceService;
    import com.hada.backtest.utils.RorCalculator;
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
            model.addAttribute("totalRor", 0.0);
            return "backtest_portfolio";
        }

        @PostMapping("")
        public String indexPost(@RequestParam HashMap<String, String> params, Model model) {

            for (String key : params.keySet()) {
                String value = params.get(key);
                model.addAttribute(key, value);
            }

            int startYear = Integer.parseInt(params.get("startYear"));
            int endYear = Integer.parseInt(params.get("endYear"));

            List<String> stockNames = new ArrayList<>();
            List<Double> weights = new ArrayList<>();

            for(int i = 1 ; i <= Integer.parseInt(params.get("count")) ; i++){
                String stockName = params.get("stock" + i);
                int indexOfParenthesis = stockName.lastIndexOf('(');
                stockNames.add(stockName.substring(0, indexOfParenthesis - 1).trim());
                weights.add(Double.parseDouble(params.get("weight" + i)));
            }

            List<Double> rorList = new ArrayList<>();
            HashMap<Integer, List<List<Double>>> rorListMap = new HashMap<>();
            HashMap<Integer, List<Double>> portfolioRorListMap = new HashMap<>();
            HashMap<Integer, Double> totalRorMap = new HashMap<>();
            double maxTotalRor = -999.0;
            double minTotalRor = 999.0;

            for(int i = startYear ; i <= endYear ; i++){
                List<List<Double>> rorListByYear = new ArrayList<>();

                for(String stockName : stockNames){
                    rorListByYear.add(RorCalculator.getRorList(stockPriceService.getPricesByItmsNmAndYear(stockName, i), true));
                }

                rorListMap.put(i, rorListByYear);
                portfolioRorListMap.put(i, RorCalculator.getPortfolioRorList(rorListByYear, weights));
                totalRorMap.put(i, RorCalculator.getTotalRor(portfolioRorListMap.get(i)));
                rorList.add(totalRorMap.get(i));
                if(totalRorMap.get(i) > maxTotalRor){
                    maxTotalRor = totalRorMap.get(i);
                }
                if(totalRorMap.get(i) < minTotalRor){
                    minTotalRor = totalRorMap.get(i);
                }
            }

            double totalRor = RorCalculator.getTotalRor(rorList);

            Map<String, Object> backtestResult = new HashMap<>();
            backtestResult.put("startAmount", params.get("startAmount"));
            backtestResult.put("totalRor", totalRor);
            backtestResult.put("endAmount", (int)Math.round(Double.parseDouble(params.get("startAmount")) * (1 + totalRor / 100)));
            backtestResult.put("maxRor", maxTotalRor);
            backtestResult.put("minRor", minTotalRor);

            model.addAttribute("backtestResult", backtestResult);

            Map<String, Object> backtestResultByYear = new HashMap<>();

            for(int i = startYear ; i <= endYear ; i++){
                Map<String, Object> backtestResultByYearMap = new HashMap<>();
                List<Double> PortfolioRorList = portfolioRorListMap.get(i);
                List<List<Double>> RorListByYear = rorListMap.get(i);
                Double MaxRor = PortfolioRorList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
                Double MinRor = PortfolioRorList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();

                for(int j = 0 ; j < RorListByYear.size() ; j++){
                    Map<String, Object> backtestResultByStockMap = new HashMap<>();
                    List<Double> rorListByStock = RorListByYear.get(j);

                    Double maxRorByStock = rorListByStock.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
                    Double minRorByStock = rorListByStock.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
                    Double totalRorByStock = RorCalculator.getTotalRor(rorListByStock);

                    backtestResultByStockMap.put("maxRorByStock", maxRorByStock);
                    backtestResultByStockMap.put("minRorByStock", minRorByStock);
                    backtestResultByStockMap.put("totalRorByStock", totalRorByStock);
                    backtestResultByYearMap.put(stockNames.get(j), backtestResultByStockMap);
                }

                backtestResultByYearMap.put("maxRor", MaxRor);
                backtestResultByYearMap.put("minRor", MinRor);
                backtestResultByYearMap.put("totalRor", totalRorMap.get(i));
                backtestResultByYear.put(String.valueOf(i), backtestResultByYearMap);
            }

            model.addAttribute("stockNames", stockNames);
            model.addAttribute("backtestResultByYear", backtestResultByYear);
            model.addAttribute("totalRor", totalRor);

            return "backtest_portfolio";
        }

    }

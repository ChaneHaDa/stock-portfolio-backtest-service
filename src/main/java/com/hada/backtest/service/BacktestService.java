package com.hada.backtest.service;

import com.hada.backtest.dto.BacktestInputDTO;
import com.hada.backtest.dto.BacktestItemDTO;
import com.hada.backtest.dto.BacktestReturnDTO;
import com.hada.backtest.jpa.dto.PortfolioStockDTO;
import com.hada.backtest.jpa.dto.StockPriceDTO;
import com.hada.backtest.jpa.service.StockPriceService;
import com.hada.backtest.utils.RorCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BacktestService {
    private final StockPriceService stockPriceService;

    public BacktestService(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    public List<PortfolioStockDTO> getortfolioStockDTOList(List<String> code, List<Double> weightList) {
        List<PortfolioStockDTO> portfolioStockDTOList = new ArrayList<>();
        for(String s : code) {
            List<StockPriceDTO> stockPrices = stockPriceService.getStockPriceListByCode(s);
            Map<String, Long> price = new HashMap<>();
            for(StockPriceDTO stockPriceDTO : stockPrices) {
                price.put(stockPriceDTO.getDate().toString(), stockPriceDTO.getPrice());
            }
            portfolioStockDTOList.add(new PortfolioStockDTO(stockPrices.get(0).getName(), s, price, weightList.get(code.indexOf(s))));
        }
        return portfolioStockDTOList;
    }

    public List<Double> getPortfolioRorList(List<PortfolioStockDTO> portfolioStockDTOList, int startYear, int endYear) {
        List<List<Long>> priceByStockList = new ArrayList<>();
        List<List<Double>> rorByStockList = new ArrayList<>();
        List<Double> weightList = new ArrayList<>();
        for (PortfolioStockDTO portfolioStockDTO : portfolioStockDTOList) {
            List<Long> priceList = new ArrayList<>();
            weightList.add(portfolioStockDTO.getWeight());
            for (int i = startYear; i <= endYear + 1; i++) {
                String key = String.valueOf(i) + "-01-01";
                if(portfolioStockDTO.getPriceMap().containsKey(key)) {
                    priceList.add(portfolioStockDTO.getPriceMap().get(key));
                }else {
                    priceList.add(0L);
                }
            }
            priceByStockList.add(priceList);
            rorByStockList.add(RorCalculator.getRorList(priceList));
        }

        return RorCalculator.getPortfolioRorList(rorByStockList, weightList);
    }

    public BacktestReturnDTO getBacktestReturnDTO(BacktestInputDTO backtestInputDTO) {
        int startYear = Integer.parseInt(backtestInputDTO.getStartDate());
        int endYear = Integer.parseInt(backtestInputDTO.getEndDate());
        List<String> stockNameList = new ArrayList<>();
        List<String> stockCodeList = new ArrayList<>();
        List<Double> weightList = new ArrayList<>();

        for(BacktestItemDTO item : backtestInputDTO.getItems()) {
            String stockName = item.getStock();
            int indexOfParenthesis1 = stockName.lastIndexOf('(');
            int indexOfParenthesis2 = stockName.lastIndexOf(')');
            stockNameList.add(stockName);
            stockCodeList.add(stockName.substring(indexOfParenthesis1 + 1, indexOfParenthesis2));
            weightList.add(item.getWeight());
        }
        List<Double> rorList = getPortfolioRorList(getortfolioStockDTOList(stockCodeList, weightList), startYear, endYear);
        double totalRor = RorCalculator.getTotalRor(rorList);
        double endAmount = backtestInputDTO.getStartAmount() * (1 + totalRor);
        double maxRor = rorList.stream().max(Double::compareTo).orElse(0.0);
        double minRor = rorList.stream().min(Double::compareTo).orElse(0.0);

        return new BacktestReturnDTO(endAmount, totalRor, maxRor, minRor, stockNameList);
    }
}

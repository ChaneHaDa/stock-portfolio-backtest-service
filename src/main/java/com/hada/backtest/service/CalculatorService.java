package com.hada.backtest.service;

import com.hada.backtest.dto.CalculatorRorInputDTO;
import com.hada.backtest.dto.CalculatorRorReturnDTO;
import com.hada.backtest.dto.CalculatorWelfareInputDTO;
import com.hada.backtest.dto.CalculatorWelfareReturnDTO;
import com.hada.backtest.utils.RorCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

    public CalculatorRorReturnDTO calculateRor(CalculatorRorInputDTO calculatorRorInputDTO) {
        double ror = RorCalculator.getRor(calculatorRorInputDTO.getBuyPrice(), calculatorRorInputDTO.getSellPrice());
        long profit = (long) ((calculatorRorInputDTO.getSellPrice() - calculatorRorInputDTO.getBuyPrice()) * calculatorRorInputDTO.getQuantity());

        return new CalculatorRorReturnDTO(ror, profit);
    }

    public CalculatorWelfareReturnDTO calculateWelfare(CalculatorWelfareInputDTO calculatorWelfareInputDTO) {
        List<Double> rorList = RorCalculator.getWelfareRorList(calculatorWelfareInputDTO.getRor(), calculatorWelfareInputDTO.getTerm());
        List<Double> revenueList = new ArrayList<>();
        List<Double> amountList = new ArrayList<>();
        double price = calculatorWelfareInputDTO.getPrice();

        revenueList.add(Math.round(price * (rorList.get(0) / 100) * 100) / 100.0);
        amountList.add(Math.round((price + revenueList.get(0)) * 100) / 100.0);
        for(int i = 1 ; i < rorList.size() ; i++){
            Double revenue = (price * (rorList.get(i) / 100)) - (price * (rorList.get(i - 1) / 100));
            revenueList.add(Math.round(revenue * 100) / 100.0);
            amountList.add(Math.round((amountList.get(i - 1) + revenueList.get(i)) * 100) / 100.0);
        }

        double totalProfit = (long) (price * (rorList.get(rorList.size() - 1) / 100));
        double finalAmount = (long) (price * (1 + rorList.get(rorList.size() - 1) / 100));

        return new CalculatorWelfareReturnDTO(totalProfit, finalAmount, revenueList, amountList, rorList);
    }
}

package com.hada.backtest.controller;

import com.hada.backtest.utils.RorCalculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {
    @GetMapping("")
    public String index(Model model) {
        return "calculator";
    }

    @GetMapping("/ror")
    public String getRor(Model model,
                         @RequestParam(defaultValue = "0.0") String buyPrice,
                         @RequestParam(defaultValue = "0.0") String sellPrice,
                         @RequestParam(defaultValue = "0") String quantity) {
        try {
            double buyPriceDouble = Double.parseDouble(buyPrice);
            double sellPriceDouble = Double.parseDouble(sellPrice);
            long quantityLong = Long.parseLong(quantity);


            model.addAttribute("ror", RorCalculator.getRor(buyPriceDouble, sellPriceDouble));
            model.addAttribute("profit", (long) ((sellPriceDouble - buyPriceDouble) * quantityLong));
            model.addAttribute("buyPrice", buyPriceDouble);
            model.addAttribute("sellPrice", sellPriceDouble);
            model.addAttribute("quantity", quantityLong);
        } catch (NumberFormatException e) {
            // 잘못된 입력 형식에 대한 처리
            return "calculator";
        }
        return "calculator";
    }

    @GetMapping("/welfare")
    public String getWelfare(Model model,
                             @RequestParam(defaultValue = "0.0") String price,
                             @RequestParam(defaultValue = "0") String term,
                             @RequestParam(defaultValue = "0.0") String welfareRor) {
        try {
            double priceDouble = Double.parseDouble(price);
            long termLong = Long.parseLong(term);
            double welfareRorDouble = Double.parseDouble(welfareRor);

            model.addAttribute("price", price);
            model.addAttribute("term", term);
            model.addAttribute("welfareRor", welfareRor);

            List<Double> rorList = RorCalculator.getWelfareRorList(welfareRorDouble, termLong);
            List<Double> revenueList = new ArrayList<>();
            List<Double> priceList = new ArrayList<>();

            revenueList.add(Math.round(priceDouble * (rorList.get(0) / 100) * 100) / 100.0);
            priceList.add(Math.round((priceDouble + revenueList.get(0)) * 100) / 100.0);

            for(int i = 1 ; i < rorList.size() ; i++){
                Double revenue = (priceDouble * (rorList.get(i) / 100)) - (priceDouble * (rorList.get(i - 1) / 100));
                revenueList.add(Math.round(revenue * 100) / 100.0);
                priceList.add(Math.round((priceList.get(i - 1) + revenueList.get(i)) * 100) / 100.0);
            }

            model.addAttribute("totalProfit", (long) (priceDouble * (rorList.get(rorList.size() - 1) / 100)));
            model.addAttribute("finalAmount", (long) (priceDouble * (1 + rorList.get(rorList.size() - 1) / 100)));

            model.addAttribute("rorList", rorList);
            model.addAttribute("revenueList", revenueList);
            model.addAttribute("priceList", priceList);

        } catch (NumberFormatException e) {
            // 잘못된 입력 형식에 대한 처리
            return "calculator";
        }
        return "calculator";
    }
}

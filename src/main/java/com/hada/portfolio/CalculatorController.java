package com.hada.portfolio;

import com.hada.portfolio.utils.RorCalculator;
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

            // 사용자가 제대로된 숫자값을 입력하지 않았을 경우를 처리할 수 있습니다.
            // buyPriceDouble, sellPriceDouble, quantityLong를 사용하여 작업을 수행합니다.

            model.addAttribute("ror", (sellPriceDouble - buyPriceDouble) / buyPriceDouble * 100);
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
            System.out.println(rorList);

        } catch (NumberFormatException e) {
            // 잘못된 입력 형식에 대한 처리
            return "calculator";
        }
        return "calculator";
    }
}

package com.hada.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {
    @GetMapping("")
    public String index(Model model) {
        return "calculator";
    }

    @GetMapping("/ror")
    public String getRor(Model model, @RequestParam double buyPrice, @RequestParam double sellPrice, @RequestParam long quantity) {
        model.addAttribute("ror", (sellPrice - buyPrice) / (double) buyPrice * 100);
        model.addAttribute("profit", (long) ((sellPrice - buyPrice) / (double) buyPrice * 100 * quantity));
        model.addAttribute("buyPrice", buyPrice);
        model.addAttribute("sellPrice", sellPrice);
        model.addAttribute("quantity", quantity);

        return "calculator";
    }
}

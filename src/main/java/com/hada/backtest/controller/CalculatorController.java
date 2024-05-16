package com.hada.backtest.controller;

import com.hada.backtest.dto.CalculatorRorInputDTO;
import com.hada.backtest.dto.CalculatorWelfareInputDTO;
import com.hada.backtest.service.CalculatorService;
import com.hada.backtest.utils.RorCalculator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("")
    public String calculatorIndex() {
        return "calculator";
    }

    @GetMapping("/ror")
    public String getRor(Model model, @Valid @ModelAttribute CalculatorRorInputDTO calculatorRorInputDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "calculator";
        }
        model.addAttribute("CalculatorRorInputDTO", calculatorRorInputDTO);
        model.addAttribute("CalculatorRorReturnDTO", calculatorService.calculateRor(calculatorRorInputDTO));
        return "calculator";
    }

    @GetMapping("/welfare")
    public String getWelfare(Model model, @ModelAttribute CalculatorWelfareInputDTO calculatorWelfareInputDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "calculator";
        }
        model.addAttribute("calculatorWelfareInputDTO", calculatorWelfareInputDTO);
        model.addAttribute("calculatorWelfareReturnDTO", calculatorService.calculateWelfare(calculatorWelfareInputDTO));
        return "calculator";
    }
}

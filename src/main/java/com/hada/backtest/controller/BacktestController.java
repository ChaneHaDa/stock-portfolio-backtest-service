    package com.hada.backtest.controller;

    import com.hada.backtest.dto.BacktestInputDTO;
    import com.hada.backtest.dto.BacktestItemDTO;
    import com.hada.backtest.service.BacktestService;
    import jakarta.validation.Valid;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.List;

    @Controller
    @RequestMapping("/backtest-portfolio")
    public class BacktestController {

        private final BacktestService backtestService;

        public BacktestController(BacktestService backtestService) {
            this.backtestService = backtestService;
        }

        @GetMapping("")
        public String getBacktest(Model model) {
            List<BacktestItemDTO> items = new ArrayList<>();
            items.add(new BacktestItemDTO("삼성전자 (005930)", 0.5));
            model.addAttribute("backtestInputDTO", new BacktestInputDTO("2020", "2023", 100000, items, 1));
            return "backtest_portfolio";
        }

        @PostMapping("")
        public String postBacktest(Model model, @Valid @ModelAttribute BacktestInputDTO backtestInputDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "backtest_portfolio";
            }

            int weightSum = 0;
            for (BacktestItemDTO item : backtestInputDTO.getItems()) {
                weightSum += item.getWeight();
            }
            if (weightSum != 1) {
                bindingResult.rejectValue("items", "error.items", "종목 비중의 합이 1이 되어야 합니다.");
                return "backtest_portfolio";
            }

            model.addAttribute("backtestInputDTO", backtestInputDTO);
            model.addAttribute("backtestReturnDTO", backtestService.getBacktestReturnDTO(backtestInputDTO));
            return "backtest_portfolio";
        }

    }

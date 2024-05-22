package com.hada.backtest.jpa.controller;

import com.hada.backtest.dto.BacktestInputDTO;
import com.hada.backtest.dto.BacktestItemDTO;
import com.hada.backtest.jpa.dto.PortfolioInputDTO;
import com.hada.backtest.jpa.dto.PortfolioInputItemDTO;
import com.hada.backtest.jpa.entity.Portfolio;

import com.hada.backtest.jpa.service.PortfolioService;
import com.hada.backtest.jpa.service.SiteUserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final SiteUserService siteUserService;

    public PortfolioController(PortfolioService portfolioService, SiteUserService siteUserService) {
        this.portfolioService = portfolioService;
        this.siteUserService = siteUserService;
    }

    @PostMapping("/save")
    public String savePortfolio(Model model, @Valid @ModelAttribute PortfolioInputDTO portfolioInputDTO, BindingResult bindingResult, Principal principal) {
        if(principal == null) {
            return "redirect:/user/login";
        }
        if(bindingResult.hasErrors()) {
            List<BacktestItemDTO> items = portfolioInputDTO.getItems().stream().map(BacktestItemDTO::fromPortfolioInoutItemDTO).toList();
            model.addAttribute("backtestInputDTO", new BacktestInputDTO("2020", "2023", 100000, items, 1));
            return "backtest_portfolio";
        }
        portfolioService.savePortfolio(portfolioInputDTO, principal.getName());
        return "redirect:/portfolio";
    }

    @GetMapping("")
    public String getPotfolios(Principal principal, Model model) {
        if(principal == null) {
            return "redirect:/login";
        }
        List<Portfolio> portfolios = portfolioService.getPortfoliosByUser(siteUserService.getUser(principal.getName()));
        model.addAttribute("portfolios", portfolios);
        return "portfolio_index";
    }

    @PostMapping("/compositions")
    public String getPortfolio(@RequestParam("id") Long id, Model model) {
        PortfolioInputDTO portfolioInputDTO = portfolioService.getPortfolioComposition(id);
        model.addAttribute("portfolioInputDTO", portfolioInputDTO);
        return "portfolio_compositions";
    }

    @PostMapping("/backtest")
    public String getBacktest(@ModelAttribute PortfolioInputDTO portfolioInputDTO, Model model) {
        List<BacktestItemDTO> items = portfolioInputDTO.getItems().stream().map(BacktestItemDTO::fromPortfolioInoutItemDTO).toList();
        BacktestInputDTO backtestInputDTO = new BacktestInputDTO("2020", "2023", 100000, items, items.size());
        model.addAttribute("backtestInputDTO", backtestInputDTO);
        return "backtest_portfolio";
    }

    @PostMapping("/update")
    public String updatePortfolio(@Valid @ModelAttribute PortfolioInputDTO portfolioInputDTO, BindingResult bindingResult, Principal principal) {
        if(principal == null) {
            return "redirect:/user/login";
        }
        if(bindingResult.hasErrors()) {
            return "portfolio_compositions";
        }

        double weightSum = 0;
        for (PortfolioInputItemDTO item : portfolioInputDTO.getItems()) {
            weightSum += item.getWeight();
        }
        if (weightSum != 1) {
            bindingResult.rejectValue("items", "error.items", "종목 비중의 합이 1이 되어야 합니다.");
            return "portfolio_compositions";
        }

        portfolioService.updatePortfolio(portfolioInputDTO);
        return "redirect:/portfolio";
    }

    @PostMapping("/delete")
    public String deletePortfolio(@RequestParam("id") Long id) {
        portfolioService.delete(id);
        return "redirect:/portfolio";
    }
}

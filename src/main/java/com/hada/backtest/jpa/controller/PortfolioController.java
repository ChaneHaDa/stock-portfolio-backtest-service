package com.hada.backtest.jpa.controller;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioItem;
import com.hada.backtest.jpa.service.PortfolioService;
import com.hada.backtest.jpa.service.PortfolioItemService;
import com.hada.backtest.jpa.entity.SiteUser;
import com.hada.backtest.jpa.service.SiteUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioItemService portfolioCompositionService;
    private final SiteUserService siteUserService;

    public PortfolioController(PortfolioService portfolioService, PortfolioItemService portfolioCompositionService, SiteUserService siteUserService) {
        this.portfolioService = portfolioService;
        this.portfolioCompositionService = portfolioCompositionService;
        this.siteUserService = siteUserService;
    }

    @PostMapping("/save")
    public String savePortfolio(@RequestParam Map<String, Object> portfolio, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }

        String portfolioName = (String) portfolio.get("portfolioName");
        String description = (String) portfolio.get("description");
        List<String> itmsNmList = new ArrayList<>();
        List<String> srtnCdList = new ArrayList<>();
        List<String> stockWeightList = new ArrayList<>();
        portfolio.forEach((k, v) -> {
            if (k.contains("stock")) {
                int indexOfParenthesis = ((String) v).lastIndexOf('(');
                itmsNmList.add(((String) v).substring(0, indexOfParenthesis - 1).trim());
                srtnCdList.add(((String) v).substring(indexOfParenthesis + 1, ((String) v).length() - 1));
            }
            if(k.contains("weight")) {
                stockWeightList.add((String) v);
            }
        });
        String username = principal.getName();
        SiteUser siteUser = siteUserService.getUser(username);

        Portfolio portfolio1 = new Portfolio();
        portfolio1.setName(portfolioName);
        portfolio1.setDescription(description);
        portfolio1.setSiteUser(siteUser);

        List<PortfolioItem> portfolioItemList = new ArrayList<>();
        for(int i = 0 ; i < itmsNmList.size() ; i++) {
            PortfolioItem portfolioItem = new PortfolioItem();
            portfolioItem.setCode(itmsNmList.get(i));
            portfolioItem.setName(srtnCdList.get(i));
            portfolioItem.setWeight(Double.parseDouble(stockWeightList.get(i)));
            portfolioItem.setPortfolio(portfolio1);
            portfolioItemList.add(portfolioItem);
        }

        portfolioService.save(portfolio1);
        portfolioCompositionService.saveAll(portfolioItemList);

        return "redirect:/portfolio_index";
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
    public String getPortfolio(@RequestParam Long portfolioId, @RequestParam String portfolioName, Model model) {
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
        List<PortfolioItem> portfolioItems = portfolioCompositionService.getPortfolioCompositions(portfolio);
        model.addAttribute("count", portfolioItems.size());
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("portfolioId", portfolioId);

        for(int i = 0; i < portfolioItems.size() ; i++) {
            model.addAttribute("stock" + (i + 1), portfolioItems.get(i).getCode() + " (" + portfolioItems.get(i).getName() + ")");
            model.addAttribute("weight" + (i + 1), portfolioItems.get(i).getWeight());
            model.addAttribute("id" + (i + 1), portfolioItems.get(i).getId());
        }

        return "portfolio_compositions";
    }

    @PostMapping("/backtest")
    public String backtest(@RequestParam Long portfolioId, Model model) {
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
        List<PortfolioItem> portfolioItems = portfolioCompositionService.getPortfolioCompositions(portfolio);
        model.addAttribute("count", portfolioItems.size());
        model.addAttribute("startAmount", 10000000);
        model.addAttribute("totalRor", 0.0);

        for(int i = 0; i < portfolioItems.size() ; i++) {
            model.addAttribute("stock" + (i + 1), portfolioItems.get(i).getCode() + " (" + portfolioItems.get(i).getName() + ")");
            model.addAttribute("weight" + (i + 1), portfolioItems.get(i).getWeight());
        }

        return "backtest_portfolio";
    }

    @PostMapping("/update")
    public String updatePortfolio(@RequestParam Map<String, Object> portfolio, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }

        String portfolioName = (String) portfolio.get("portfolioName");
        String description = (String) portfolio.get("description");
        List<String> itmsNmList = new ArrayList<>();
        List<String> srtnCdList = new ArrayList<>();
        List<String> stockWeightList = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        portfolio.forEach((k, v) -> {
            if (k.contains("stock")) {
                int indexOfParenthesis = ((String) v).lastIndexOf('(');
                itmsNmList.add(((String) v).substring(0, indexOfParenthesis - 1).trim());
                srtnCdList.add(((String) v).substring(indexOfParenthesis + 1, ((String) v).length() - 1));
            }
            if(k.contains("weight")) {
                stockWeightList.add((String) v);
            }
            if(k.contains("id")) {
                idList.add(Long.parseLong((String) v));
            }
        });

        String username = principal.getName();
        SiteUser siteUser = siteUserService.getUser(username);

        Portfolio portfolio1 = new Portfolio();
        portfolio1.setName(portfolioName);
        portfolio1.setDescription(description);
        portfolio1.setSiteUser(siteUser);
        portfolio1.setId(Long.parseLong((String) portfolio.get("portfolioId")));

        List<PortfolioItem> portfolioItemList = new ArrayList<>();
        for(int i = 0 ; i < itmsNmList.size() ; i++) {
            PortfolioItem portfolioItem = new PortfolioItem();
            portfolioItem.setCode(itmsNmList.get(i));
            portfolioItem.setName(srtnCdList.get(i));
            portfolioItem.setWeight(Double.parseDouble(stockWeightList.get(i)));
            portfolioItem.setId(idList.get(i));
            portfolioItem.setPortfolio(portfolio1);
            portfolioItem.setId(idList.get(i));
            portfolioItemList.add(portfolioItem);
        }

        portfolioService.save(portfolio1);
        portfolioCompositionService.saveAll(portfolioItemList);

        return "redirect:/portfolio";
    }

    @PostMapping("/delete")
    public String deletePortfolio(@RequestParam Long portfolioId) {
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
        portfolioCompositionService.deleteAll(portfolio);
        portfolioService.delete(portfolioId);

        return "redirect:/portfolio";
    }
}

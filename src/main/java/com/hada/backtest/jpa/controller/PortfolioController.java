package com.hada.backtest.jpa.controller;

import com.hada.backtest.jpa.entity.Portfolio;
import com.hada.backtest.jpa.entity.PortfolioComposition;
import com.hada.backtest.jpa.service.PortfolioService;
import com.hada.backtest.jpa.service.PortfolioCompositionService;
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
    private final PortfolioCompositionService portfolioCompositionService;

    private final SiteUserService siteUserService;

    public PortfolioController(PortfolioService portfolioService, PortfolioCompositionService portfolioCompositionService, SiteUserService siteUserService) {
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
        portfolio1.setPortfolioName(portfolioName);
        portfolio1.setDescription(description);
        portfolio1.setUsername(siteUser);

        List<PortfolioComposition> portfolioCompositionList = new ArrayList<>();
        for(int i = 0 ; i < itmsNmList.size() ; i++) {
            PortfolioComposition portfolioComposition = new PortfolioComposition();
            portfolioComposition.setItmsNm(itmsNmList.get(i));
            portfolioComposition.setSrtnCd(srtnCdList.get(i));
            portfolioComposition.setAllocation(Double.parseDouble(stockWeightList.get(i)));
            portfolioComposition.setPortfolio(portfolio1);
            portfolioCompositionList.add(portfolioComposition);
        }

        portfolioService.save(portfolio1);
        portfolioCompositionService.saveAll(portfolioCompositionList);

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
        List<PortfolioComposition> portfolioCompositions = portfolioCompositionService.getPortfolioCompositions(portfolio);
        model.addAttribute("count", portfolioCompositions.size());
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("portfolioId", portfolioId);

        for(int i = 0 ; i < portfolioCompositions.size() ; i++) {
            model.addAttribute("stock" + (i + 1), portfolioCompositions.get(i).getItmsNm() + " (" + portfolioCompositions.get(i).getSrtnCd() + ")");
            model.addAttribute("weight" + (i + 1), portfolioCompositions.get(i).getAllocation());
            model.addAttribute("id" + (i + 1), portfolioCompositions.get(i).getId());
        }

        return "portfolio_compositions";
    }

    @PostMapping("/backtest")
    public String backtest(@RequestParam Long portfolioId, Model model) {
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
        List<PortfolioComposition> portfolioCompositions = portfolioCompositionService.getPortfolioCompositions(portfolio);
        model.addAttribute("count", portfolioCompositions.size());
        model.addAttribute("startAmount", 10000000);
        model.addAttribute("totalRor", 0.0);

        for(int i = 0 ; i < portfolioCompositions.size() ; i++) {
            model.addAttribute("stock" + (i + 1), portfolioCompositions.get(i).getItmsNm() + " (" + portfolioCompositions.get(i).getSrtnCd() + ")");
            model.addAttribute("weight" + (i + 1), portfolioCompositions.get(i).getAllocation());
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
        portfolio1.setPortfolioName(portfolioName);
        portfolio1.setDescription(description);
        portfolio1.setUsername(siteUser);
        portfolio1.setId(Long.parseLong((String) portfolio.get("portfolioId")));

        List<PortfolioComposition> portfolioCompositionList = new ArrayList<>();
        for(int i = 0 ; i < itmsNmList.size() ; i++) {
            PortfolioComposition portfolioComposition = new PortfolioComposition();
            portfolioComposition.setItmsNm(itmsNmList.get(i));
            portfolioComposition.setSrtnCd(srtnCdList.get(i));
            portfolioComposition.setAllocation(Double.parseDouble(stockWeightList.get(i)));
            portfolioComposition.setId(idList.get(i));
            portfolioComposition.setPortfolio(portfolio1);
            portfolioComposition.setId(idList.get(i));
            portfolioCompositionList.add(portfolioComposition);
        }

        portfolioService.save(portfolio1);
        portfolioCompositionService.saveAll(portfolioCompositionList);

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

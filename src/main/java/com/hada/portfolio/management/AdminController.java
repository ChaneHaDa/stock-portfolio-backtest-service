package com.hada.portfolio.management;

import com.hada.portfolio.stock.StockDataLoader;
import com.hada.portfolio.stock.info.StockInfo;
import com.hada.portfolio.stock.info.StockInfoService;
import com.hada.portfolio.stock.price.StockPrice;
import com.hada.portfolio.stock.price.StockPriceService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StockDataLoader stockDataLoader;
    private final StockInfoService stockInfoService;
    private final StockPriceService stockPriceService;

    public AdminController(StockDataLoader stockDataLoader, StockInfoService stockInfoService, StockPriceService stockPriceService) {
        this.stockDataLoader = stockDataLoader;
        this.stockInfoService = stockInfoService;
        this.stockPriceService = stockPriceService;
    }
    @GetMapping("")
    public String getMangementPage() {
        return "layout_admin";
    }

    @GetMapping("/stockinfo")
    public String showStockInfoPage(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<StockInfo> pagingStocks = stockInfoService.findAll(page);

        model.addAttribute("paging", pagingStocks);
        model.addAttribute("totalStocks", pagingStocks.getTotalElements());

        return "stockinfo";
    }

    @GetMapping("/stockinfo/search")
    public String searchStockInfoPage(Model model, @RequestParam String query) {
        StockInfo stock = stockInfoService.findByItmsNmOrSrtnCd(query);
        model.addAttribute("stock", stock);
        model.addAttribute("query", query);
        return "search_stockinfo";
    }

    @GetMapping("/update/stockinfo")
    public String updateStockInfoPage() {
        return "update_stockinfo";
    }

    @PostMapping("/update/stockinfo")
    public String updateStockInfoPage(@RequestParam(name = "baseDate") String baseDate, Model model) {
        if(baseDate == null || baseDate.equals("")) {
            model.addAttribute("error", "날짜를 입력하세요.");
            return "update_stockinfo";
        }

        System.out.println(baseDate);

        LocalDate baseDates = LocalDate.parse(baseDate, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate today = LocalDate.now();
        if (baseDates.isAfter(today)) {
            model.addAttribute("error", baseDate + "는 미래의 날짜입니다.");
            return "update_stockinfo";
        }

        stockDataLoader.loadStockInfoByBaseDate(baseDate.replaceAll("-", ""));
        return "redirect:/admin/stockinfo";
    }

    // stock price

    @GetMapping("/stockprice")
    public String showStockPricePage(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(required=false) String query , @RequestParam(defaultValue = "desc") String filter) {
        Page<StockPrice> pagingStocks;
        model.addAttribute("filter", filter);

        if(query != null && !query.equals("")) {
            pagingStocks = stockPriceService.findAllByItmsNmOrSrtnCd(query, page, filter.equals("desc"));
            model.addAttribute("paging", pagingStocks);
            model.addAttribute("totalStocks", pagingStocks.getTotalElements());
            model.addAttribute("query", query);
            return "stockprice";
        }


        pagingStocks = stockPriceService.findAll(page, filter.equals("desc"));
        model.addAttribute("paging", pagingStocks);
        model.addAttribute("totalStocks", pagingStocks.getTotalElements());

        return "stockprice";
    }

    @GetMapping("/update/stockprice")
    public String updateStockPricePage() {
        return "update_stockprice";
    }

    @PostMapping("/update/stockprice")
    public String updateStockPricePage(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, Model model) {
        if(startDate == null || startDate.equals("")) {
            model.addAttribute("error", "시작 날짜를 입력하세요.");
            return "update_stockprice";
        }
        if(endDate == null || endDate.equals("")) {
            model.addAttribute("error", "끝 날짜를 입력하세요.");
            return "update_stockprice";
        }

        LocalDate startDates = LocalDate.parse(startDate, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDates = LocalDate.parse(endDate, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate today = LocalDate.now();
        if (endDates.isAfter(today)) {
            model.addAttribute("error", endDate + "는 미래의 날짜입니다.");
            return "update_stockprice";
        }

        if(startDates.isAfter(endDates)) {
            model.addAttribute("error", "시작 날짜가 끝 날짜보다 늦습니다.");
            return "update_stockprice";
        }

        stockDataLoader.loadStockPriceByTerm(startDate.replaceAll("-", ""), endDate.replaceAll("-", ""));

        return "redirect:/admin/stockprice";
    }


}

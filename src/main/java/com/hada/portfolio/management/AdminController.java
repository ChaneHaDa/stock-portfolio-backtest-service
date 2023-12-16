package com.hada.portfolio.management;

import com.hada.portfolio.stock.StockDataLoader;
import com.hada.portfolio.stock.info.StockInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StockDataLoader stockDataLoader;

    public AdminController(StockDataLoader stockDataLoader) {
        this.stockDataLoader = stockDataLoader;
    }
    @GetMapping("")
    public String getMangementPage() {
        return "layout_admin";
    }

    @GetMapping("/stockinfo")
    public String showStockInfo(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<StockInfo> pagingStocks = stockDataLoader.getStockInfo(page);
        model.addAttribute("paging", pagingStocks);
        model.addAttribute("totalStocks", pagingStocks.getTotalElements());

        return "stockinfo";
    }

    @GetMapping("/update/stockinfo")
    public String updateStockInfoPage() {
        return "update_stockinfo";
    }

    @PostMapping("/update/stockinfo")
    public String updateStockInfo(@RequestParam(name = "baseDate") String baseDate, Model model) {
        if(baseDate == null || baseDate.equals("")) {
            model.addAttribute("error", "날짜를 입력하세요.");
            return "update_stockinfo";
        }

        LocalDate baseDates = LocalDate.parse(baseDate, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate today = LocalDate.now();
        if (baseDates.isAfter(today)) {
            model.addAttribute("error", baseDate + "는 미래의 날짜입니다.");
            return "update_stockinfo";
        }

        stockDataLoader.loadStockInfoByBaseDate(baseDate.replaceAll("-", ""));
        return "redirect:/admin/stockinfo";
    }
}

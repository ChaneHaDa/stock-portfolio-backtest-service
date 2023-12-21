package com.hada.portfolio.stock;

import com.google.gson.JsonArray;
import com.hada.portfolio.api.DataPortalApi;
import com.hada.portfolio.stock.info.StockInfo;
import com.hada.portfolio.stock.info.StockInfoService;
import com.hada.portfolio.stock.price.StockPrice;
import com.hada.portfolio.stock.price.StockPriceService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockDataLoader {
    private final StockInfoService stockInfoService;
    private final StockPriceService stockPriceService;
    private final DataPortalApi dataPortalApi;

    public StockDataLoader(StockInfoService stockInfoService, StockPriceService stockPriceService, DataPortalApi dataPortalApi) {
        this.stockInfoService = stockInfoService;
        this.stockPriceService = stockPriceService;
        this.dataPortalApi = dataPortalApi;
    }

    public void loadStockInfoByBaseDate(String baseDate) {
        JsonArray stockJson = dataPortalApi.getStock(baseDate);

        if(stockJson == null){
            return;
        }

        for(int i = 0 ; i < stockJson.size() ; i++){
            StockInfo stockInfo = new StockInfo();
            stockInfo.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockInfo.setMrktCtg(stockJson.get(i).getAsJsonObject().get("mrktCtg").getAsString());
            stockInfo.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockInfo.setIsinCd(stockJson.get(i).getAsJsonObject().get("isinCd").getAsString());

            stockInfoService.save(stockInfo);
        }
    }

    public void loadStockPriceByBaseDate(String baseDate) {
        JsonArray stockJson = dataPortalApi.getStock(baseDate);

        if(stockJson == null){
            return;
        }
        List<StockPrice> stockPrices = new ArrayList<>();

        for(int i = 0 ; i < stockJson.size() ; i++){
            StockPrice stockPrice = new StockPrice();
            stockPrice.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockPrice.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockPrice.setMkp(Long.parseLong(stockJson.get(i).getAsJsonObject().get("mkp").getAsString()));
            stockPrice.setClpr(Long.parseLong(stockJson.get(i).getAsJsonObject().get("clpr").getAsString()));
            stockPrice.setHipr(Long.parseLong(stockJson.get(i).getAsJsonObject().get("hipr").getAsString()));
            stockPrice.setLopr(Long.parseLong(stockJson.get(i).getAsJsonObject().get("lopr").getAsString()));
            stockPrice.setTrqu(Long.parseLong(stockJson.get(i).getAsJsonObject().get("trqu").getAsString()));
            stockPrice.setTrPrc(Long.parseLong(stockJson.get(i).getAsJsonObject().get("trPrc").getAsString()));
            stockPrice.setMrktTotAmt(Long.parseLong(stockJson.get(i).getAsJsonObject().get("mrktTotAmt").getAsString()));
            String basDt = stockJson.get(i).getAsJsonObject().get("basDt").getAsString();
            LocalDate date = LocalDate.parse(basDt, DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockPrice.setBasDt(date);

            stockPrices.add(stockPrice);
        }
        stockPriceService.saveAll(stockPrices);
    }

    public void loadStockPriceByTerm(String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).plusDays(1);
        LocalDate date = start;
        while(date.isBefore(end)){
            loadStockPriceByBaseDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            date = date.plusDays(1);
        }
    }


}

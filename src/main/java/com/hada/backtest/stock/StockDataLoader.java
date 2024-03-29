package com.hada.backtest.stock;

import com.google.gson.JsonArray;
import com.hada.backtest.api.DataPortalApi;
import com.hada.backtest.stock.info.StockInfo;
import com.hada.backtest.stock.info.StockInfoService;
import com.hada.backtest.stock.price.StockPrice;
import com.hada.backtest.stock.price.StockPriceService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//            stockInfo.setMrktCtg(stockJson.get(i).getAsJsonObject().get("mrktCtg").getAsString());
            stockInfo.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
//            stockInfo.setIsinCd(stockJson.get(i).getAsJsonObject().get("isinCd").getAsString());

            stockInfoService.save(stockInfo);
        }
    }

    public void loadStockPriceByBaseDate(String baseDate) {
        JsonArray stockJson = dataPortalApi.getStock(baseDate.substring(0, 6));
        if(stockJson == null){
            return;
        }
        List<StockPrice> stockPrices = new ArrayList<>();
        Set<StockInfo> stockInfos = new HashSet<>();


        for(int i = 0 ; i < stockJson.size() ; i++){
            StockPrice stockPrice = new StockPrice();
            stockPrice.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockPrice.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockPrice.setClpr(Math.round(Double.parseDouble(stockJson.get(i).getAsJsonObject().get("clpr").getAsString())));

//            stockPrice.setMkp(Long.parseLong(stockJson.get(i).getAsJsonObject().get("mkp").getAsString()));
//            stockPrice.setHipr(Long.parseLong(stockJson.get(i).getAsJsonObject().get("hipr").getAsString()));
//            stockPrice.setLopr(Long.parseLong(stockJson.get(i).getAsJsonObject().get("lopr").getAsString()));
//            stockPrice.setTrqu(Long.parseLong(stockJson.get(i).getAsJsonObject().get("trqu").getAsString()));
//            stockPrice.setTrPrc(Long.parseLong(stockJson.get(i).getAsJsonObject().get("trPrc").getAsString()));
//            stockPrice.setMrktTotAmt(Long.parseLong(stockJson.get(i).getAsJsonObject().get("mrktTotAmt").getAsString()));

            String basDt = stockJson.get(i).getAsJsonObject().get("basDt").getAsString() + "01";
            LocalDate date = LocalDate.parse(basDt, DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockPrice.setBasDt(date);

            stockPrices.add(stockPrice);

            StockInfo stockInfo = new StockInfo();
            stockInfo.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockInfo.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockInfos.add(stockInfo);
        }

        stockPriceService.saveAll(stockPrices);
        stockInfoService.saveAll(stockInfos);
    }

    public void loadETFPriceByBaseDate(String baseDate) {
        JsonArray stockJson = dataPortalApi.getETF(baseDate.substring(0, 6));
        if(stockJson == null){
            return;
        }
        List<StockPrice> stockPrices = new ArrayList<>();
        Set<StockInfo> stockInfos = new HashSet<>();


        for(int i = 0 ; i < stockJson.size() ; i++){
            StockPrice stockPrice = new StockPrice();
            stockPrice.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockPrice.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockPrice.setClpr(Math.round(Double.parseDouble(stockJson.get(i).getAsJsonObject().get("clpr").getAsString())));

            String basDt = stockJson.get(i).getAsJsonObject().get("basDt").getAsString() + "01";
            LocalDate date = LocalDate.parse(basDt, DateTimeFormatter.ofPattern("yyyyMMdd"));
            stockPrice.setBasDt(date);
            stockPrices.add(stockPrice);

            StockInfo stockInfo = new StockInfo();
            stockInfo.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockInfo.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockInfos.add(stockInfo);
        }

        stockPriceService.saveAll(stockPrices);
        stockInfoService.saveAll(stockInfos);
    }

    public void loadStockPriceByTerm(String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).plusMonths(1);
        LocalDate date = start;
        while(date.isBefore(end)){
            loadStockPriceByBaseDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            date = date.plusMonths(1);
        }
    }

    public void loadETFPriceByTerm(String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")).plusMonths(1);
        LocalDate date = start;
        while(date.isBefore(end)){
            loadETFPriceByBaseDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            date = date.plusMonths(1);
        }
    }


}

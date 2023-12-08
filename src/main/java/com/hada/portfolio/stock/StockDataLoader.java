package com.hada.portfolio.stock;

import com.google.gson.JsonArray;
import com.hada.portfolio.api.DataPortalApi;
import com.hada.portfolio.stock.info.StockInfo;
import com.hada.portfolio.stock.info.StockInfoService;
import com.hada.portfolio.stock.price.StockPriceService;
import org.springframework.stereotype.Component;

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

    public void loadStockInfo(String baseDate) {
        JsonArray stockJson = dataPortalApi.getStock(baseDate);

        for(int i = 0 ; i < stockJson.size() ; i++){
            StockInfo stockInfo = new StockInfo();
            stockInfo.setItmsNm(stockJson.get(i).getAsJsonObject().get("itmsNm").getAsString());
            stockInfo.setMrktCtg(stockJson.get(i).getAsJsonObject().get("mrktCtg").getAsString());
            stockInfo.setSrtnCd(stockJson.get(i).getAsJsonObject().get("srtnCd").getAsString());
            stockInfo.setIsinCd(stockJson.get(i).getAsJsonObject().get("isinCd").getAsString());

            System.out.println(stockInfo.getItmsNm());
        }

    }
}

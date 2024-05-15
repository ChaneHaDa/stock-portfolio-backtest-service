package com.hada.backtest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hada.backtest.utils.JsonUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DataPortalApi {
    @Value("${data-portal-key}")
    private String dataPortalKey;

    @Value("${dataPortal.url}")
    private String dataPortalUrl;

    public String getStockJson(String baseDate) {
        String path = "json/stock/"+baseDate+".json";
        if(JsonUtility.getJsonFromFile(path) != null){
            return JsonUtility.getJsonFromFile(path);
        }

//        String apiUrl = dataPortalUrl+"/getStockPriceInfo";
//        String queryParams = "?serviceKey=" + dataPortalKey
//                + "&numOfRows=10000&resultType=json"
//                + "&basDt=" + baseDate;
//        String json = JsonUtility.getJsonFromUrl(apiUrl + queryParams);
//        return json;

        return null;
    }

    public String getETFJson(String baseDate) {
        String path = "json/etf/"+baseDate+".json";
        if(JsonUtility.getJsonFromFile(path) != null){
            return JsonUtility.getJsonFromFile(path);
        }

        return null;
    }

    public JsonArray parseJson(String json) {
        if(json == null){
            return null;
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        JsonArray itemsArray = jsonObject.getAsJsonObject("response")
                .getAsJsonObject("body")
                .getAsJsonObject("items")
                .getAsJsonArray("item");

        return itemsArray;
    }

    public JsonArray parseJsonfile(String json) {
        if(json == null){
            return null;
        }
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray itemsArray = jsonObject.entrySet().stream()
                .map(entry -> entry.getValue().getAsJsonObject())
                .collect(JsonArray::new, JsonArray::add, JsonArray::addAll);


        return itemsArray;
    }

    public JsonArray getStock(String baseDate) {
        String json = getStockJson(baseDate);
//        JsonArray itemsArray = parseJson(json);
        JsonArray itemsArray = parseJsonfile(json);
        return itemsArray;
    }

    public JsonArray getETF(String baseDate) {
        String json = getETFJson(baseDate);
//        JsonArray itemsArray = parseJson(json);
        JsonArray itemsArray = parseJsonfile(json);
        return itemsArray;
    }

}

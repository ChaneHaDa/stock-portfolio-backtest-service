package com.hada.portfolio.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hada.portfolio.utils.JsonUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DataPortalApi {
    @Value("${data-portal-key}")
    private String dataPortalKey;

    @Value("${dataPortal.url}")
    private String dataPortalUrl;

    public String getStockInfo(String baseDate) {
        String apiUrl = dataPortalUrl+"/getStockPriceInfo";
        String queryParams = "?serviceKey=" + dataPortalKey
                + "&numOfRows=10000&resultType=json"
                + "&basDt=" + baseDate;
        String json = JsonUtility.getJsonFromUrl(apiUrl + queryParams);
        return json;
    }

    public JsonArray parseJson(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        JsonArray itemsArray = jsonObject.getAsJsonObject("response")
                .getAsJsonObject("body")
                .getAsJsonObject("items")
                .getAsJsonArray("item");

        return itemsArray;
    }

}

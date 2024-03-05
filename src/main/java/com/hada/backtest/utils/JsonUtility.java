package com.hada.backtest.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class JsonUtility {
    public static String getJsonFromUrl(String urlstr) {
        BufferedReader br = null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));
            String result;
            String line;
            for (result = ""; (line = br.readLine()) != null; result = result + line) {
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static String getJsonFromFile(String filePath) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            if(resource.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                String result;
                String line;
                for (result = ""; (line = br.readLine()) != null; result = result + line) {
                }

                return result;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.hada.portfolio.utils;

import java.util.ArrayList;
import java.util.List;

public final class RorCalculator {

    public static double getRor(double buyPrice, double sellPrice){
        return (sellPrice - buyPrice) / buyPrice * 100;
    }

    public static List<Double> getRorList(List<Double> priceList){
        List<Double> rorList = new ArrayList<>();
        for(int i=1; i<priceList.size(); i++){
            rorList.add(getRor(priceList.get(i-1), priceList.get(i)));
        }
        return rorList;
    }

    public static double getLogRor(double buyPrice, double sellPrice){
        return Math.log(sellPrice / buyPrice);
    }
}

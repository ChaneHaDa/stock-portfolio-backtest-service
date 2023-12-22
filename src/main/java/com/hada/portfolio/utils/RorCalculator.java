package com.hada.portfolio.utils;

import java.util.ArrayList;
import java.util.List;

public final class RorCalculator {

    public static double getRor(double buyPrice, double sellPrice){
        return (sellPrice - buyPrice) / buyPrice * 100;
    }

    public static double getRor(long buyPrice, long sellPrice) {
        return getRor((double) buyPrice, (double) sellPrice);
    }

    public static List<Double> getRorList(List<Double> priceList){
        List<Double> rorList = new ArrayList<>();
        for(int i = 1; i < priceList.size(); i++){
            rorList.add(getRor(priceList.get(i - 1), priceList.get(i)));
        }
        return rorList;
    }

    public static List<Double> getRorList(List<Long> priceList, boolean isLong){
        List<Double> rorList = new ArrayList<>();
        for(int i = 1; i < priceList.size(); i++){
            rorList.add(getRor(priceList.get(i - 1), priceList.get(i)));
        }
        return rorList;
    }

    public static double getPortfolioRor(List<Double> rorList, List<Double> weightList){
        double portfolioRor = 0;
        for(int i = 0; i < rorList.size(); i++){
            portfolioRor += rorList.get(i) * weightList.get(i);
        }
        return portfolioRor;
    }

    public static double getLogRor(double buyPrice, double sellPrice){
        return Math.log(sellPrice / buyPrice);
    }
}

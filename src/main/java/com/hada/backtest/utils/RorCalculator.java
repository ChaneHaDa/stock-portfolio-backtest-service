package com.hada.backtest.utils;

import java.util.ArrayList;
import java.util.List;

public final class RorCalculator {

    public static double getRor(double buyPrice, double sellPrice){
        if(buyPrice == 0){
            return 0;
        }
        Double ror = (sellPrice - buyPrice) / buyPrice * 100;
        return Math.round(ror * 100) / 100.0;
    }

    public static double getRor(long buyPrice, long sellPrice) {
        return getRor((double) buyPrice, (double) sellPrice);
    }

    public static long getCashByRor(long cash, double ror){
        return (long) (cash * (1 + ror / 100));
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
        return Math.round(portfolioRor * 100) / 100.0;
    }

    public static List<Double> getPortfolioRorList(List<List<Double>> rorList, List<Double> weightList){
        List<Double> portfolioRorList = new ArrayList<>();
        for(int i = 0; i < rorList.get(0).size(); i++){
            double portfolioRor = 0;
            for(int j = 0; j < rorList.size(); j++){
                portfolioRor += rorList.get(j).get(i) * weightList.get(j);
            }
            portfolioRorList.add(Math.round(portfolioRor * 100) / 100.0);
        }
        return portfolioRorList;
    }

    public static List<Long> getCashByRorList(List<Double> rorList, long cash){
        List<Long> cashList = new ArrayList<>();
        cashList.add(cash);
        for(int i = 0; i < rorList.size(); i++){
            cashList.add((long) (cashList.get(i) * (1 + rorList.get(i) / 100)));
        }
        return cashList;
    }

    public static Double getTotalRor(List<Double> rorList) {
    	Double totalRor = 1.0;
    	for(int i = 0; i < rorList.size(); i++){
    		totalRor *= (1 + rorList.get(i) / 100);
        }

        return Math.round((totalRor - 1) * 10000) / 100.0;
    }

    public static List<Double> getWelfareRorList(double welfareRor, long term){
        List<Double> welfareRorList = new ArrayList<>();
        welfareRorList.add(welfareRor);
        for(int i = 1; i < term; i++){
            double calWelfare = (welfareRorList.get(i - 1) / 100 + 1) * (1 + welfareRor / 100) - 1;
            welfareRorList.add(Math.round((calWelfare * 100) * 100) / 100.0);
        }
        return welfareRorList;
    }
}

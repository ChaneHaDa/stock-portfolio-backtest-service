package com.hada.portfolio.stock.price;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StockPriceDTO {
    private String itmsNm;
    private String srtnCd;
    private Long mkp;
    private Long clpr;
    private Long hipr;
    private Long lopr;
    private Long trqu;
    private Long trPrc;
    private Long mrktTotAmt;
    private LocalDate basDt;

}

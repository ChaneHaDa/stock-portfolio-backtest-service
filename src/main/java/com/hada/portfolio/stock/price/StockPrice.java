package com.hada.portfolio.stock.price;

import com.hada.portfolio.stock.info.StockInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class StockPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 종목코드
    private String itmsNm;
    // 종목명
    private String srtnCd;
//    // 시가
//    private Long mkp;
    // 종가
    private Long clpr;
//    // 고가
//    private Long hipr;
//    // 저가
//    private Long lopr;
//    // 거래량
//    private Long trqu;
//    // 거래대금
//    private Long trPrc;
//    // 시가총액
//    private Long mrktTotAmt;
    // 기준일자
    private LocalDate basDt;

}

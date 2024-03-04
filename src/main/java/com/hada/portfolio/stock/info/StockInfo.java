package com.hada.portfolio.stock.info;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StockInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 종목코드
    @Column(unique = true)
    private String itmsNm;
    // 시장구분
//    private String mrktCtg;
    // 종목명
    private String srtnCd;
    // 고유번호
//    private String isinCd;

}

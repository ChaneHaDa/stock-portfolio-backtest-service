package com.hada.portfolio;

import com.hada.portfolio.stock.price.StockPrice;
import com.hada.portfolio.stock.price.StockPriceRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockPriceTest {
    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Test
    @Order(1)
    public void testSaveStockPrice() {
        StockPrice stockPrice1 = createSampleStockPrice("ABC", LocalDate.of(2023, 12, 1), 1000L, 1200L, 900L, 800L, 15000L, 300000L, 5000000L);
        StockPrice stockPrice2 = createSampleStockPrice("ABC", LocalDate.of(2023, 12, 2), 1500L, 1300L, 1100L, 1000L, 18000L, 400000L, 6000000L);
        StockPrice stockPrice3 = createSampleStockPrice("ABC", LocalDate.of(2023, 12, 3), 2000L, 1800L, 1500L, 1400L, 20000L, 450000L, 7000000L);
        StockPrice stockPrice4 = createSampleStockPrice("GHI", LocalDate.of(2023, 12, 4), 2500L, 2200L, 1900L, 1800L, 22000L, 500000L, 7500000L);
        StockPrice stockPrice5 = createSampleStockPrice("JKL", LocalDate.of(2023, 12, 1), 3000L, 2700L, 2300L, 2200L, 25000L, 550000L, 8000000L);


        assertThat(stockPrice1).isEqualTo(this.stockPriceRepository.save(stockPrice1));
        assertThat(stockPrice2).isEqualTo(this.stockPriceRepository.save(stockPrice2));
        assertThat(stockPrice3).isEqualTo(this.stockPriceRepository.save(stockPrice3));
        assertThat(stockPrice4).isEqualTo(this.stockPriceRepository.save(stockPrice4));
        assertThat(stockPrice5).isEqualTo(this.stockPriceRepository.save(stockPrice5));

    }

    @Test
    @Order(2)
    public void testFindByItmsNmAndBasDtBetween() {
        String itmsNm = "ABC"; // 종목명 설정
        LocalDate startDate = LocalDate.of(2023, 11, 1); // 시작 날짜 설정
        LocalDate endDate = LocalDate.of(2023, 12, 2); // 끝 날짜 설정

        List<StockPrice> stockPrices = this.stockPriceRepository.findByItmsNmAndBasDtBetween(itmsNm, startDate, endDate);

        assertEquals(2, stockPrices.size());

    }

    public static StockPrice createSampleStockPrice(String itmsNm, LocalDate basDt, Long mkp, Long clpr, Long hipr, Long lopr,
                                                    Long trqu, Long trPrc, Long mkrtTotAmt) {
        StockPrice stockPrice = new StockPrice();
        stockPrice.setItmsNm(itmsNm);
        stockPrice.setBasDt(basDt);
        stockPrice.setMkp(mkp);
        stockPrice.setClpr(clpr);
        stockPrice.setHipr(hipr);
        stockPrice.setLopr(lopr);
        stockPrice.setTrqu(trqu);
        stockPrice.setTrPrc(trPrc);
        stockPrice.setMrktTotAmt(mkrtTotAmt);

        return stockPrice;

    }

}



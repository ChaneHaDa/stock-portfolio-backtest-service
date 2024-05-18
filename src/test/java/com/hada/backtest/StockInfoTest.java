package com.hada.backtest;

import com.hada.backtest.jpa.repository.StockInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StockInfoTest {
    @Autowired
    private StockInfoRepository stockInfoRepository;

//    @Test
//    void testStockInfoSaveJpa() {
//        StockInfo s1 = new StockInfo();
//        s1.setItmsNm("900110");
//        s1.setMrktCtg("KOSDAQ");
//        s1.setSrtnCd("이스트아시아홀딩스");
//        s1.setIsinCd("HK0000057197");
//        if(stockInfoRepository.findByItmsNm(s1.getItmsNm()) == null) {
//            assertThat(s1).isEqualTo(this.stockInfoRepository.save(s1));
//        }
//
//        StockInfo s2 = new StockInfo();
//        s2.setItmsNm("900110");
//        s2.setMrktCtg("KOSDAQ");
//        s2.setSrtnCd("이스트아시아홀딩스");
//        s2.setIsinCd("HK0000057197");
//        if(stockInfoRepository.findByItmsNm(s2.getItmsNm()) == null) {
//            assertThat(s2).isEqualTo(this.stockInfoRepository.save(s2));
//        }
//
//        StockInfo s3 = new StockInfo();
//        s3.setItmsNm("900270");
//        s3.setMrktCtg("KOSDAQ");
//        s3.setSrtnCd("헝셩그룹");
//        s3.setIsinCd("HK0000214814");
//        if(stockInfoRepository.findByItmsNm(s3.getItmsNm()) == null) {
//            assertThat(s3).isEqualTo(this.stockInfoRepository.save(s3));
//        }
//
//        assertEquals("1", "1");
//    }
//
//    @Test
//    void testStockInfoFindAllJpa() {
//        List<StockInfo> all = this.stockInfoRepository.findAll();
//
//        assertEquals(all.size(), 2);
//
//        StockInfo s = all.get(0);
//        assertEquals("900110", s.getItmsNm());
//    }
//
//    @Test
//    void testStockInfoFindItmsNmJpa() {
//        StockInfo s = this.stockInfoRepository.findByItmsNm("900110");
//        assertEquals("이스트아시아홀딩스", s.getSrtnCd());
//    }
//
//    @Test
//    void testStockInfoFindSrtnCdJpa() {
//        StockInfo s = this.stockInfoRepository.findBySrtnCd("이스트아시아홀딩스");
//        assertEquals("900110", s.getItmsNm());
//    }
}

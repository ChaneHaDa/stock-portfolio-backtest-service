# stock-portfolio-backtest-service

# 프로젝트 설명
## 프로젝트명
증권 포트폴리오 백테스트 제공 서비스

## 프로젝트 주요 기능
* 복리, 수익률 계산기
* 증권 포트폴리오 백테스트 및 회원 저장 기능

## 배포
* docker-compose(nginx, spring boot, certbot), AWS(EC2)
* https://backtest-portfolio.site/
* 테스트 배포중

## 프로젝트 내용
KRX에 상장한 증권으로 구성된 포트폴리오를 과거 데이터로 백테스트를 제공하는 서비스

## 증권 데이터
해당 프로젝트에서 사용하는 데이터는 공공데이터 포탈에서 받아온 데이터입니다.
해당 링크의 데이터를 사용함
https://github.com/ChaneHaDa/krx-json-data

## Entity
### StockPrice
* 주식의 가격정보로 종가가 기준
* id, code, name, date, price

### StockInfo
* 주식의 정보로 종목코드, 종목명에 해당하는 데이터 저장
* id, code, name

### Portfolio
* 포트폴리오 저장을 위한 entity
* id, portfolioName, description, username(SiteUser)

### PortfolioItem
* 포트폴리오의 구성 종목, 비중
* id, portfolio(ManyToOne), code, name, weight

### Siteuser
* User를 저장
* id, username, password, authorities

## Package
### config
Security 설정등의 설정들
### controller
JPA와 관련이 없는 컨트롤러들
### dto
JPA와 관련이 없는 dto
### exception
예외처리를 위함
### service
JPA와 관련이 없는 서비스들
### jpa
portfolio, stock, user등 jpa와 관련된 파일들
### utils
각종 계산을 위함 유틸 파일

# 환경
## 개발언어 
* Spring Boot(JAVA 17)
* HTML, BootStrap

## 개발환경
* IntellJ
* wsl2(22.04.03 LTS)

# 추가 설명
## velog
[증권 포트폴리오 백테스트 개발 과정](https://velog.io/@chane_ha_da/series/%EC%A6%9D%EA%B6%8C-%ED%8F%AC%ED%8A%B8%ED%8F%B4%EB%A6%AC%EC%98%A4-%EB%B0%B1%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%84%9C%EB%B9%84%EC%8A%A4)

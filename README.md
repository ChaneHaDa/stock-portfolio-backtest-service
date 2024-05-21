# stock-portfolio-backtest-service

# 프로젝트 설명
## 프로젝트명
증권 포트폴리오 백테스트 제공 서비스

## 주요사항
* 24.05.18.을 기준으로 기존 프로젝트를 리팩토링
* 현재 새로운 기능을 추가중

## 배포
* docker-compose(nginx, spring boot, certbot), AWS(EC2)
* https://backtest-portfolio.site/
* 24.05.19. 부터 테스트 배포중
* ![image](https://github.com/ChaneHaDa/stock-portfolio-backtest-service/assets/140226331/776623ae-77c1-436f-91fd-b36801e1a73d)


## 프로젝트 주요 기능
* 코스피, 코스닥에 상장중인 종목으로 구성한 포트폴리오 구성 및 백테스트
* 구성한 포트폴리오의 저장 및 수정(회원)
* 복리, 수익률 계산기

## Entity
<img width="1200" alt="Untitled" src="https://github.com/ChaneHaDa/stock-portfolio-backtest-service/assets/140226331/7b371215-9122-4b9e-8201-35a164381b08">

## 증권 데이터
공공데이터 포탈에서 받아온 데이터입니다.

[데이터 및 파싱 GitHub](https://github.com/ChaneHaDa/krx-json-data)

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

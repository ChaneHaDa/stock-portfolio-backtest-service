# Stock Portfolio Backtest Service

## 프로젝트 개요

이 프로젝트는 사용자가 주식 포트폴리오를 구성하고 과거 데이터를 기반으로 백테스팅을 수행할 수 있는 웹 애플리케이션입니다. 또한, 수익률 계산 및 복리 계산 기능도 제공합니다.

## 주요 기능

*   **사용자 관리:** 회원가입 및 로그인 기능 (Spring Security 기반)
*   **포트폴리오 관리:**
    *   사용자별 포트폴리오 생성, 조회, 수정, 삭제
    *   포트폴리오 내 주식 종목 및 비중 관리
*   **주식 정보:** 주식 기본 정보 및 가격 데이터 관리 (JPA 기반)
*   **백테스팅:**
    *   사용자가 구성한 포트폴리오를 기반으로 과거 성과 시뮬레이션
    *   백테스팅 기간 설정 가능
*   **계산기:**
    *   단순 수익률(ROR) 계산
    *   복리 계산

## 기술 스택

*   **언어:** Java 17
*   **프레임워크:** Spring Boot 3.2.0
*   **빌드 도구:** Gradle
*   **데이터베이스:**
    *   H2 (개발용 인메모리 DB)
    *   MySQL (운영 환경 고려)
    *   Spring Data JPA
*   **템플릿 엔진:** Thymeleaf, Thymeleaf Layout Dialect
*   **보안:** Spring Security
*   **라이브러리:**
    *   Lombok
    *   Gson
    *   Spring Boot DevTools
    *   Spring Boot Validation
    *   Bootstrap (CSS)

## 설정 및 실행 방법

1.  **저장소 복제:**
    ```bash
    git clone <repository-url>
    cd stock-portfolio-backtest-service
    ```
2.  **빌드:**
    ```bash
    ./gradlew build
    ```
    (Windows: `gradlew.bat build`)
3.  **실행:**
    ```bash
    ./gradlew bootRun
    ```
    (Windows: `gradlew.bat bootRun`)

    애플리케이션은 기본적으로 `http://localhost:8080` 에서 실행됩니다.

## 데이터베이스 설정

*   **개발 환경:** 기본적으로 H2 인메모리 데이터베이스를 사용합니다 (`src/main/resources/application.properties`). 애플리케이션 실행 시 스키마가 생성되고 `src/main/resources/data.sql` 파일의 초기 데이터가 로드될 수 있습니다.
*   **운영 환경:** MySQL 사용을 원할 경우 `src/main/resources/application-prod.properties` 파일에 MySQL 연결 정보를 설정하고, 실행 시 `prod` 프로파일을 활성화해야 합니다.
    ```bash
    ./gradlew bootRun --spring.profiles.active=prod
    ```

## 프로젝트 구조

```
.
├── build.gradle              # Gradle 빌드 스크립트
├── gradlew                   # Gradle Wrapper (Linux/Mac)
├── gradlew.bat               # Gradle Wrapper (Windows)
├── README.md                 # 프로젝트 설명 파일
├── settings.gradle           # Gradle 설정
├── gradle/                   # Gradle Wrapper 관련 파일
└── src/
    ├── main/
    │   ├── java/com/hada/backtest/ # Java 소스 코드 루트
    │   │   ├── config/             # 설정 클래스 (e.g., SecurityConfig)
    │   │   ├── constant/           # 상수 정의 (e.g., UserRole)
    │   │   ├── controller/         # Spring MVC 컨트롤러 (웹 요청 처리)
    │   │   ├── dto/                # 데이터 전송 객체 (Data Transfer Objects)
    │   │   ├── exception/          # 예외 처리 관련 클래스
    │   │   ├── jpa/                # JPA 관련 패키지
    │   │   │   ├── controller/     # JPA 엔티티 관련 API 컨트롤러
    │   │   │   ├── dto/            # JPA 엔티티 관련 DTO
    │   │   │   ├── entity/         # JPA 엔티티 클래스
    │   │   │   ├── repository/     # Spring Data JPA 리포지토리 인터페이스
    │   │   │   └── service/        # JPA 엔티티 관련 서비스 로직
    │   │   ├── service/            # 핵심 비즈니스 로직 서비스
    │   │   └── utils/              # 유틸리티 클래스 (e.g., RorCalculator)
    │   └── resources/              # 리소스 파일
    │       ├── application.properties # 기본 설정 파일
    │       ├── application-dev.properties # 개발 환경 설정
    │       ├── application-prod.properties # 운영 환경 설정
    │       ├── data.sql            # 초기 데이터 스크립트 (H2용)
    │       ├── static/             # 정적 파일 (CSS, JS, 이미지 등)
    │       └── templates/          # Thymeleaf 템플릿 파일
    └── test/                     # 테스트 코드
        └── java/com/hada/backtest/ # 테스트 소스 코드 루트
```

## JPA 엔티티

*   **SiteUser:** 사용자 정보를 저장하는 엔티티 (ID, 비밀번호, 이메일 등)
*   **Portfolio:** 사용자가 생성한 포트폴리오 정보를 저장하는 엔티티 (이름, 설명, 사용자 연결)
*   **PortfolioItem:** 포트폴리오에 포함된 개별 주식 종목 및 비중 정보를 저장하는 엔티티 (포트폴리오 및 주식 정보 연결)
*   **StockInfo:** 주식 종목의 기본 정보(코드, 이름 등)를 저장하는 엔티티
*   **StockPrice:** 주식 종목의 일별 가격 정보(날짜, 시가, 고가, 저가, 종가 등)를 저장하는 엔티티
<img width="1200" alt="Untitled" src="https://github.com/ChaneHaDa/stock-portfolio-backtest-service/assets/140226331/7b371215-9122-4b9e-8201-35a164381b08">

## 배포
* docker-compose(nginx, spring boot, certbot), AWS(EC2)
* 24.05.19. ~ 25.04.01 배포, 새로운 구조로 리팩토링
  
![image](https://github.com/ChaneHaDa/stock-portfolio-backtest-service/assets/140226331/6a2785de-9aa5-4ca1-970c-ae92f8e3329f)

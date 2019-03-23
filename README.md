# 지자체 협약 지원 API.
`중소기업은행 지자체 협약 지원 정보 서비스 개발` 문제 선택

## 개발 프레임워크
`SpringBoot 2.1.3`

### 사용 라이브러리
`JPA` `H2` `lombok` `jbcrypt` `JWT`
`jackson-dataformat-csv`

### 디렉토리 구조 세부설명
- com.exam.demoApi
  - common : exception Handler 및 유틸성 클래스
  - controller :
    - DataController(/api/data/*) : CSV파일 DB에 저장하는 Cotroller
    - InfoController(/api/info/*) : 조회, 검색, 수정등의 기능수행 Cotroller
    - SecurityController(/api/auth/*) : 계정생성, 로그인, 토큰 발급 기능수행 Cotroller
  - domain : JPA Entity 클래스(Region, SupportInfo, User)
  - exception : 커스텀 예외처리 클래스 및 예외처리 코드
  - interceptor : Header에 Authorization 확인 및 검증 interceptor 클래스
  - mapper : JPA Entity 클래스와 Model간의 컨버팅 클래스
  - model : Model 클래스
  - repository : JPA repository 클래스
  - service : service 클래스
- resources : property 및 csv 파일
- test : 테스트 수행 클래스

## 문제해결
ㅇㅇㅇ

## 빌드 및 실행방법
```javascript
ㅇㅁㄹ
```
- ㅇㅇㅇㅇ
## Etc.

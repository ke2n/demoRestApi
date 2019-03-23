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
        - DataController(/api/data/*) : CSV파일 DB에 저장하는 Controller
        - InfoController(/api/info/*) : 조회, 검색, 수정등의 기능수행 Controller
        - SecurityController(/api/auth/*) : 계정생성, 로그인, 토큰 발급 기능수행 Controller
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
- `요구사항` - 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발
    - 서비스 운영 측면에서 CSV파일을 저장하는 기능은 batch성 업무로 판단되어 컨트롤러를 분리하였습니다. 실 운영시에는 해당 기능 수행에 대한 별도의 batch 테이블을 두어 일반적인 조회성 업무에 영향이 없어야 될것 같습니다.
   
   
- `요구사항` - 지원하는 지자체 목록 검색 API 개발
- `요구사항` - 지원하는 지자체명을 입력 받아 해당 지자체의 지원정보를 출력하는 API 개발
- `요구사항` - 지원하는 지자체 정보 수정 기능 API 개발
    - DB에 저장한 DATA들에 대한 기본적인 검색, 수정 서비스 기능을 추가하였습니다.
    - 지자체 지원정보 엔티티와 지원 지자체 엔티티에 대해서는 다대일 관계로 설정하였습니다.
    - 지원 지자체 엔티티의 ID는 CustomIdGenerator를 사용하여 "Region_"이 prefix로 붙고 숫자가 증가되는 ID로 설정하였습니다.
    - 목록 검색시 JPA N+1이슈가 발생하여 join fetch 전략을 사용하였습니다.
    
    
- `요구사항` - 지원한도 컬럼에서 지원금액으로 내림차순 정렬(지원금액이 동일하면 이차보전 평균 비
율이 적은 순서)하여 특정 개수만 출력하는 API 개발
- `요구사항` - 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명을 출력하는 API 개발
    - 지원한도 컬럼과 이차보전 컬럼의 텍스트에 있는 숫자를 Number Type으로 변환해주는 Util성 메서드를 개발하였습니다.
     - 지자체 지원정보 테이블내에 해당 값을 저장할 수 있는 컬럼을 추가 하였습니다(지원금액, 이차보전 평균값, 이차보전 최소값).
     - DATA가 저장되거나 수정되는 시점과 동시에 해당 컬럼에도 동시에 저장되게 하였습니다.
     - 추가된 컬럼을 요구사항에서 명기된 사항대로 정렬하여 값을 출력할 수 있도록 하였습니다.
     
     
- `요구사항` - signup 계정생성 API: 입력으로 ID, PW 받아 내부 DB 에 계정 저장하고 토큰생성하여 출력
- `요구사항` - signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰
을 발급
- `요구사항` - refresh 토큰 재발급 API: 기존에 발급받은 토큰을 Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급
    - 인터셉터를 사용하여 헤더에 Bearer Token이 존재하지 않으면 Exception을 발생 하도록 하였고, signup/signin 요청시 특정 secretKey로 서명된 JWT생성 되도록 하였습니다.
    - Bcrypt 해싱함수를 사용하여 DB에 인코딩된 패스워드가 저장되도록 하였습니다.
    
## 빌드 및 실행방법
```javascript
빌드 수행 
mvn package

서버 기동(9876 port)
java -jar target/demoApi-0.0.1-SNAPSHOT.jar
```
### 주요 URL
```
## 계정생성
curl -X POST \
  http://localhost:9876/api/auth/signup \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
    "username": "test@test.com",
    "password": "test1234"
}'


## 로그인
curl -X POST \
  http://localhost:9876/api/auth/signin \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
    "username": "test@test.com",
    "password": "test1234"
}'


## 토큰 재발급
curl -X POST \
  http://localhost:9876/api/auth/refresh \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3ZsYWRhQGdtYWlsLmNvbSIsImV4cCI6MTU1MzI2ODU2MSwiZGVzYyI6Iu2FjOyKpO2KuOyaqSBEZW1vQXBp7JeQ7IScIOuwnO2WiSJ9._URhfYHAK_PO4aa0jaF0OKimFfsK9IvytvqzEewABbE' \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
  
  
## CSV레코드를 데이터베이스에 저장
curl -X POST \
  http://localhost:9876/api/data/upload \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3ZsYWRhQGdtYWlsLmNvbSIsImV4cCI6MTU1MzI3MzA1NywiZGVzYyI6Iu2FjOyKpO2KuOyaqSBEZW1vQXBp7JeQ7IScIOuwnO2WiSJ9.sJY-9_Pm8ulNIBt1Go3gHXnbxkNpCSxBlv3YdoD1OZY' \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -F file=@/filePath/test.csv
  
  
## 지자체정보 목록출력
curl -X GET \
  http://localhost:9876/api/info/list \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3ZsYWRhQGdtYWlsLmNvbSIsImV4cCI6MTU1MzI3MzA1NywiZGVzYyI6Iu2FjOyKpO2KuOyaqSBEZW1vQXBp7JeQ7IScIOuwnO2WiSJ9.sJY-9_Pm8ulNIBt1Go3gHXnbxkNpCSxBlv3YdoD1OZY' \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
  
  
## 지자체정보 특정 지자체정보 검색
curl -X POST \
  http://localhost:9876/api/info/search \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3ZsYWRhQGdtYWlsLmNvbSIsImV4cCI6MTU1MzI3MzA1NywiZGVzYyI6Iu2FjOyKpO2KuOyaqSBEZW1vQXBp7JeQ7IScIOuwnO2WiSJ9.sJY-9_Pm8ulNIBt1Go3gHXnbxkNpCSxBlv3YdoD1OZY' \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
	"region": "강릉시"
}'


## 지자체정보 수정
curl -X POST \
  http://localhost:9876/api/info/edit \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3ZsYWRhQGdtYWlsLmNvbSIsImV4cCI6MTU1MzI3MzA1NywiZGVzYyI6Iu2FjOyKpO2KuOyaqSBEZW1vQXBp7JeQ7IScIOuwnO2WiSJ9.sJY-9_Pm8ulNIBt1Go3gHXnbxkNpCSxBlv3YdoD1OZY' \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache' \
  -d '{
  "region": "강릉시",
  "target": "대상 수정",
  "limit": "300억원이내",
  "rate": "4.005% 에서 100%",
  "usage": "운전 수정"
}'


## 지원금액으로 내림차순 정렬 출력(K개)
curl -X GET \
  http://localhost:9876/api/info/limits/10 \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoic3ZsYWRhQGdtYWlsLmNvbSIsImV4cCI6MTU1MzI3MzA1NywiZGVzYyI6Iu2FjOyKpO2KuOyaqSBEZW1vQXBp7JeQ7IScIOuwnO2WiSJ9.sJY-9_Pm8ulNIBt1Go3gHXnbxkNpCSxBlv3YdoD1OZY' \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
  
  
## 이차보전비율이 가장 작은 추천기관 출력
curl -X GET \
  http://localhost:9876/api/info/min-rate-institute \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
```

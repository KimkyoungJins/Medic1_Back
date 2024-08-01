# Medic

## 소개

## API 문서

postman 폴더 밑에 있는 postman.json 파일을 임포트하여 테스트하실 수 있습니다.

또는 다음의 링크로 문서를 바로 확인하실 수 있습니다.

[API 문서](https://documenter.getpostman.com/view/36650679/2sA3kdBJKC)

## 배포 방법

1. Gradle Tab -> Tasks -> build -> build(톱니바퀴 아이콘)을 더블클릭합니다.

2. EC2에 ssh 접속 후 `sh stop.sh'

3. root directory -> build -> libs -> medic-api-1.0.0.jar 파일을 EC2 서버에 업로드합니다.

4. EC2에 ssh 접속 후 `sh start.sh'

## EC2 접근 정보

- Host : 52.78.188.110
- Port : 22
- pem file path : root directory -> pem -> pem
- User Name : ubuntu

## DB 접근 정보

- Host : 52.78.188.110
- Port : 3306
- User Name : medic
- Password : medic123!

## Enums 정보

쿼리 파라미터 입력 시 다음 String을 입력 값으로 넘겨주어야합니다.

GenderType

- 여성
- 남성

SupplementType

- 관절솔루션_보스웰리아_7 ("관절솔루션 보스웰리아 7")
    - Recommended Product: 프리미엄 관절팔팔
- 굿바디_카르니틴_1000 ("굿바디 카르니틴 1000")
    - Recommended Product: 베러다운핏
- 세이헬스_녹차추출물 ("세이헬스 녹차추출물")
    - Recommended Product: 뺀다요
- 피부건강엔_N_실키어린_콜라겐 ("피부건강엔 N 실키어린 콜라겐")
    - Recommended Product: 뺀다요
- 바디픽_멀티비타민_포우먼 ("바디픽 멀티비타민 포우먼")
    - Recommended Product: 센트룸 프로바이오 면역케어
- 엑스트라_스트렝스_징코 ("엑스트라 스트렝스 징코")
    - Recommended Product: 헬시부트 올데이샷
- 아르기닌_파워_젤리스틱 ("아르기닌 파워 젤리스틱")
    - Recommended Product: 헬퓨 E데이펙
- 내츄럴플러스_알티지_오메가3 ("내츄럴플러스 알티지 오메가3")
    - Recommended Product: 헬퓨 E데이펙

TagType

- 관절_뼈건강
- 기억력_개선
- 혈당조절
- 체지방감소
- 피부건강
- 운동수행_능력
- 면역기능_개선
- 혈중_중성지방_개선

## 특정 API에 대한 쿼리파라미터 정보

**모든 상품 목록 조회**
- **URL**: `/api/products`
- **HTTP Method**: GET
- **Query Parameters**:
    - `tags` (List<TagType>, Optional): 상품 태그 목록
        - 예시: `tags=관절_뼈건강,기억력_개선`
    - `sort` (String, Optional): 정렬 기준 (review, highprice, lowprice, latest)
        - 예시: `sort=review`

**맞춤 상품 목록 조회 (로그인 필요)**
- **URL**: `/api/products/custom`
- **HTTP Method**: GET
- **Query Parameters**:
    - `sort` (String, Optional): 정렬 기준 (review, highprice, lowprice, latest)
        - 예시: `sort=review`

### 리뷰 목록 조회 API

**상품 리뷰 목록 조회**
- **URL**: `/api/products/{productId}/reviews`
- **HTTP Method**: GET
- **Path Parameter**:
    - `productId` (Long): 조회할 상품의 ID
- **Query Parameters**:
    - `sort` (String, Optional): 정렬 기준 (recommend, age_10s, age_20s, age_30s, age_40s, age_50s, age_60s, age_70s, latest)
        - 예시: `sort=recommend`
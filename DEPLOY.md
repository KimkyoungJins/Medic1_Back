## DEPLOY 설명
[노션 링크](https://spotless-protest-f11.notion.site/e7974a58cb664fffa931b749f2b50b02)에 기재된 요구사항을 구현하며 설정한 내용들입니다.

### 기술 스택
- JDK 17
- Spring Boot 3.3.2
- Gradle
- AWS EC2(서버 기동 및 MySQL 8.x 실행 중)

<br/>

### AWS EC2 서버 접속
pem 키는 프로젝트 내의 `aws-pem-key` 디렉토리에 존재
```shell
ssh -i {pem 키 위치}/medic-api.pem ubuntu@52.78.188.110
```

<br/>

### Postman API 호출 방법
1. 프로젝트 내의 `postman-collection` 디렉토리에 있는 JSON 파일 다운로드
2. Postman 실행 후, 다운로드한 JSON 파일을 import
3. 요청 주소를 localhost -> `52.78.188.110` 로 변경하여 호출

<br/>

### 프로젝트 빌드 및 배포 프로세스

1. 프로젝트 빌드
   - 프로젝트 루트에서 gradle clean/build
2. 프로젝트 배포
    ```bash
    # 프로젝트 루트에서 실행
    # scp 명령어를 이용하여 AWS EC2 서버로 jar 파일 전송
    scp -i {pem 키 위치}/medic-api.pem ./build/lib/medic-api-1.0.0.jar  ubuntu@52.78.188.110:/home/ubuntu
    ```

3. 프로젝트 실행
    ```bash
    # AWS EC2 접속
    ssh -i {pem 키 위치}/medic-api.pem ubuntu@52.78.188.110 
    
    # 이전에 실행중이던 스프링부트가 있을 경우 종료
    sh stop.sh
    
    # 스프링부트 프로젝트가 종료되었는지 확인
    # ps 확인란에서 medic-api-***.jar가 있는지를 체크
    sh health_check.sh
    
    # 스프링부트 프로젝트 실행
    sh start.sh
    ```

<br/>

### EC2 서버에서 스프링부트 기동 방법 

Spring Boot 기동 스크립트

```bash
# AWS EC2 접속 후 ~ 위치에서
sh start.sh
```

Spring Boot 정지 스크립트

```bash
# AWS EC2 접속 후 ~ 위치에서
sh stop.sh
```

Spring Boot 및 서버 디스크, 메모리 상태 조회 스크립트

```bash
# AWS EC2 접속 후 ~ 위치에서
sh health_check.sh
```

<br/>


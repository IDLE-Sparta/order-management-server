# 🔍 기본 정보
### 🚀 프로젝트 개요
[AI 검증 비즈니스 프로젝트]<br>내일배움캠프 KDT 심화_AI를 활용한 백엔드 아키텍처 심화 과정 2기<br> IDLE팀 1차 프로젝트

### 📅 프로젝트 기간 
2024.11.13-2024.11.18

### 👨‍👩‍👧‍👦 Our Team 
|팀원명|역할|
|:---:|:---:|
|강찬욱|회원 관리 및 인증/인가 기능 개발 및 AI 기반 질문 API 구현 |
|박소현|상품 및 리뷰 관리 API 구현 및 테스트 코드 작성, 서비스 배포|
|박용운|주문(등록, 변경, 취소, 전체 조회(개인/점주), 개별 조회(개인)), 결제(등록, 변경, 진행), 관리자 주문(조회, 변경, 취소) 기능 개발 |
|정현수| |

# 📖 프로젝트 소개

### ✨ 프로젝트 목적/상세
배달 및 포장 음식 주문 관리 플랫폼 개발을 위한 API 서버 개발 프로젝트.
광화문 근처에서 운영될 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼의 백엔드 API 개발

### 📂 API Document
프로젝트의 API 명세는 아래 링크에서 확인하실 수 있습니다. <br>
https://www.notion.so/teamsparta/API-3bc9469612954c23a09eaad935f7a4ab

### 📄 서비스 구성 및 실행방법
프로젝트의 서비스 구성 및 실행밥법은 아래 링크에서 확인하실 수 있습니다. <br>
https://github.com/IDLE-Sparta/order-management-server/wiki

---

# 📖 Project Technologies
### 📝 사용된 기술 및 소프트웨어

프레임워크 및 라이브러리<br>
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white"/> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/JPA-6DB33F?style=flat-square&logo=hibernate&logoColor=white"/> <img src="https://img.shields.io/badge/SpringDataJPA-6DB33F?style=flat-square&logo=spring&logoColor=white"/>

인증 및 보안<br>
<img src="https://img.shields.io/badge/JSONWebToken-000000?style=flat-square&logo=JsonWebToken&logoColor=white"/> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/>

프로그래밍 언어<br>
<img src="https://img.shields.io/badge/java-FF81F9?style=flat-square"/>

데이터베이스 및 클라우드<br>
<img src="https://img.shields.io/badge/PostgreSQL-4479A1?style=flat-square&logo=PostgreSQL&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=flat-square&logo=AmazonRDS&logoColor=white"/> <img src="https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white"/>

테스트 및 API 도구<br>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>
<img src="https://img.shields.io/badge/Swagger-6DB33F?style=flat-square&logo=Swagger&logoColor=white"/>

버전 관리 및 협업 도구<br>
<img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"/> <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/>

### 🛠 ERD
![image](https://github.com/user-attachments/assets/991ea981-7a82-4507-9150-d9205e518054)

### 🛠 프로젝트 아키텍처
![image](https://github.com/user-attachments/assets/f1536167-e562-4899-9858-6dd5b39319f1)

### How To Start

1. git clone 
    ```
   git clone https://github.com/sososo0/shopping-app.git
   ```

2. docker 설치 
    ```
   sudo apt-get update
   sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
   sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
   sudo apt-get update
   sudo apt-get install docker-ce
   sudo docker --version
   ```

3. docker-compose 설치
    ```
   sudo curl -L "https://github.com/docker/compose/releases/download/v2.12.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
   sudo chmod +x /usr/local/bin/docker-compose
   docker-compose --version
   ```

    > +) docker 관련 오류가 발생할 시
    > docker 그룹에 사용자 추가하기 
    > ```
    > sudo usermod -aG docker $USER
    > ```
    > 
    > 변경사항을 저장하기 위해 docker 로그아웃 후 다시 로그인

4. .env, schema.sql, swagger.yml 파일 만들기 

- 보안과 유지보수를 용이하게 하기 위해 .env 파일을 생성했습니다. 
- database에 테이블을 생성하기 위한 schema.sql을 생성합니다. 
- Swagger를 위한 swagger.yml을 생성합니다. 
- 프로젝트의 루트 디렉토리에 생성합니다.
    ```
    /order-management-server
    │
    ├── .env
    ├── schema.sql
    ├── swagger.yml
    ```

```
DB_NAME=    # DB 명 
DB_USER=    # 사용자 명 
DB_PW=      # 비밀번호
JWT_KEY=    # JWT Key
AI_KEY=     # AI Key
```

- template은 위와 같습니다. 

5. docker-compose를 실행합니다.

    ```
    docker-compose up -d --build
    ```

- Dockerfile이 빌드되고 SpringBoot Web Application과 PostgreSQL의 docker container가 생성되고 실행됩니다.

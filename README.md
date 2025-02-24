GitHub 리포지터리와 Velog 글을 모두 확인했어. 다음과 같은 내용을 기반으로 **README.md**를 작성할게:

---

## **🚀 MSA 기반 TODO List 애플리케이션**  

### **📚 프로젝트 개요**  
- **목적:** MSA 아키텍처 학습 및 서버 간 통신 연습  
- **기술 스택:**  
  - **Backend:** Spring Boot, Spring Cloud (Eureka, OpenFeign, Gateway), JPA, MySQL  
  - **API Gateway:** Spring Cloud Gateway  
  - **서비스 디스커버리:** Netflix Eureka  
  - **인증:** JWT (User Service에서 인증 서버 겸용)  

---

### **🧱 아키텍처 구성**  
1. **API Gateway**  
   - **역할:** 클라이언트 요청을 라우팅하고 인증을 검사  
   - **기능:** JWT 검증, 각 서비스로 요청 전달  

2. **Eureka Server**  
   - **역할:** 각 마이크로서비스의 등록 및 발견  
   - **기능:** Load Balancing 및 서비스 상태 관리  

3. **User Service (인증 서버 겸용)**  
   - **역할:** 사용자 인증 및 인가, JWT 발급 및 검증  
   - **기능:** 회원가입, 로그인, 로그아웃, 세션 관리  
   - **JWT 인증:** `SecurityContextHolder`에서 `userId` 가져와서 사용  

4. **Task Service**  
   - **역할:** 할 일(Task) 관리  
   - **기능:** CRUD 및 연결된 Tag 정보 조회  
   - **특징:** Task-Tag 연결 테이블 유지 (Tag의 `name`은 Tag Service에서 조회)  

5. **Tag Service**  
   - **역할:** Tag 관리  
   - **기능:** CRUD  
   - **특징:** Task와의 연결 정보는 Task Service에서만 유지  

---

### **⚙️ 주요 기능 및 흐름**  
1. **JWT 인증 흐름**  
   - **로그인 시:** User Service에서 JWT 발급  
   - **API Gateway:** 모든 요청에 대해 JWT 검증 후 각 서비스로 전달  
   - **각 서비스:** JWT에서 추출한 `userId`를 이용해 비즈니스 로직 처리  

2. **Task & Tag 연결 관리**  
   - **Task Service에만 연결 테이블 유지**  
   - **GET /tasks:** 연결된 Tag의 `name`을 Tag Service에서 조회 후 함께 반환  
   - **POST /tasks:** Tag 목록 선택 후 Task-Tag 연결 테이블에 추가  

---

### **🔗 주요 URL**  
- **Velog 설계 및 구조:** [MSA 기반 TODO List 애플리케이션 설계](https://velog.io/@alsqja2626/MSA-%EA%B8%B0%EB%B0%98-TODO-List-%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98-%EC%84%A4%EA%B3%84-%EB%B0%8F-%EA%B5%AC%EC%A1%B0)  
- **Velog 인증 서버:** [MSA 인증 서버](https://velog.io/@alsqja2626/MSA-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84)  

---

### **📂 디렉토리 구조**  
```plaintext
todo-msa/
├── eureka-server/        # 서비스 디스커버리 (Eureka)
├── api-gateway/          # API Gateway (Spring Cloud Gateway)
├── user-service/         # 사용자 인증 및 인가, JWT 발급 및 검증
├── task-service/         # 할 일(Task) 관리 및 Task-Tag 연결 테이블 유지
└── tag-service/          # Tag CRUD 관리 (연결 정보는 Task Service에서 유지)
```

---

### **🎨 ERD 및 관계**  
- **Task와 Tag는 다대다 관계**  
- **Task-Tag 연결 테이블**은 **Task Service에만 유지**  
- **Tag의 `name`은 Tag Service**에서 조회  
- **User와 Task는 1:N 관계**  

---

### **🔑 주요 설정 및 포인트**  
1. **JWT 인증 및 인가**  
   - **User Service**에서 **JWT 발급 및 검증**  
   - **API Gateway**에서 **JWT 검증 후 요청 전달**  
   - **각 서비스는 `userId`만 사용**하여 비즈니스 로직 처리  

2. **Task와 Tag 간의 연결 관리**  
   - **Task-Tag 연결 테이블은 Task Service에만 존재**  
   - **GET /tasks** 시 **Tag의 `name`**을 Tag Service에서 조회하여 포함  
   - **POST /tasks** 시 **Tag 목록**을 선택 후 **Task-Tag 연결**  

---

### **🛠️ 설치 및 실행 방법**  
```bash
# 1. GitHub에서 프로젝트 클론
git clone https://github.com/alsqja/todo-msa.git

# 2. 프로젝트 폴더로 이동
cd todo-msa

# 3. 각 서비스별 Docker Compose 실행 (Eureka Server, API Gateway, User Service, Task Service, Tag Service)
docker-compose up --build
```

---

### **🧪 테스트 방법**  
1. **Postman 또는 Insomnia**를 사용하여 테스트  
2. **인증 및 인가 테스트**  
   - `POST /auth/signup` → 회원가입  
   - `POST /auth/login` → 로그인 후 JWT 발급  
   - **JWT를 Authorization Header에 포함하여 요청**  
3. **Task와 Tag 연동 테스트**  
   - `POST /tasks` → Task 생성 시 `tagIds` 포함  
   - `GET /tasks` → Task 목록과 연결된 Tag 이름 함께 반환  

---

### **📖 참조 및 참고 자료**  
- **Velog 글**:  
  - [MSA 기반 TODO List 설계](https://velog.io/@alsqja2626/MSA-%EA%B8%B0%EB%B0%98-TODO-List-%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98-%EC%84%A4%EA%B3%84-%EB%B0%8F-%EA%B5%AC%EC%A1%B0)  
  - [MSA 인증 서버](https://velog.io/@alsqja2626/MSA-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84)  
- **Spring Cloud 공식 문서**  
- **Spring Security 공식 문서**  

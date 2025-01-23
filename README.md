# 운동 기록 관리 시스템 🏋️‍♂️

## 📖 프로젝트 소개
사용자가 개인 운동 기록을 날짜별로 체계적으로 관리하기 위한 웹 애플리케이션입니다.  
회원 가입, 로그인, 유산소 및 무산소 운동 기록 생성, 수정, 삭제와 같은 기능을 제공합니다.

---

## 📦 사용 방법
1. 리포지토리 클론: git clone https://github.com/myung13/Health.git << 명령어로 프로젝트를 로컬에 클론합니다.
2. MYSQL 설정: MYSQL을 설치하고 실행합니다. apllication.properties에 아이디 비번이 있습니다.
- CREATE DATABASE Health_db; 코드를 입력하고 실행합니다. (생성)
- 데이터베이스 연결 정보는 src/main/resources/application.properties 파일에서 설정할 수 있습니다.
3. IntelliJ 프로젝트 열고 -> Gradle 빌드 실행 -> 애플리케이션 실행 -> 브라우저 열고 http://localhost:8081 입력 후 기능을 확인합니다.
  
---

## 🚀 주요 기능

### 1️⃣ 사용자 관리
- **회원가입**: 사용자의 이름, 전화번호, 비밀번호를 입력받아 새로운 회원을 생성합니다.  
- **로그인**: Spring Security를 사용하여 전화번호와 비밀번호 기반 인증을 처리합니다.  

#### 관련 코드
- **Entity**: `Member`  
- **Repository**: `MemberRepository`  
- **Service**: `MemberService`

---

### 2️⃣ 운동 기록 관리

#### 🏃‍♂️ 유산소 운동
- **기록 생성**: 유산소 운동 타입, 이름, 시간, 칼로리를 기록합니다.  
- **기록 수정**: 유산소 운동 정보를 수정할 수 있습니다.  
- **기록 삭제**: 특정 유산소 운동 기록을 삭제합니다.  

#### 🏋️‍♀️ 무산소 운동
- **기록 생성**: 무산소 운동 타입, 이름, 무게, 반복 횟수, 세트를 기록합니다.  
- **기록 수정**: 무산소 운동 정보를 수정할 수 있습니다.  
- **기록 삭제**: 특정 무산소 운동 기록을 삭제합니다.  

#### 공통 기능
- 특정 날짜 및 주간 운동 기록 조회  
- 날짜와 운동 종류별 기록 조회  

#### 관련 코드
- **Entity**: `ExerciseLog`  
- **Repository**: `ExerciseLogRepository`  
- **Service**: `ExerciseLogService`

---

## 🛠️ 기술 스택

### Back-end  
<img src="https://github.com/user-attachments/assets/75c4bc4c-0867-42e7-b2d7-33da085be553" alt="Java" width="100"/>  
<img src="https://github.com/user-attachments/assets/77a6edd1-85f0-4677-92c9-a1553c1ae9e4" alt="Spring Boot" width="100"/>  
<img src="https://github.com/user-attachments/assets/cf762c34-60df-4206-b42e-f6c6cd0fc6f0" alt="Spring Security" width="100"/>

---

### Database  
<img src="https://github.com/user-attachments/assets/c1fd1d37-aa9f-4f50-bce9-baf06e1f8d81" alt="MySQL" width="100"/>

---

### View  
<img src="https://github.com/user-attachments/assets/37f80f79-ce5c-45e6-a555-f1f8ee82adfb" alt="Thymeleaf" width="100"/>


---

### Build Tool  
= gradle

---

## 🖼️ 주요 화면

### 회원가입 및 로그인 창
![회원가입 로그인창](https://github.com/user-attachments/assets/36bf7e49-fc8e-489c-bdde-d6f3d469fbaf)

### 운동 기록 입력 및 조회
![운동기록 입력, 조회](https://github.com/user-attachments/assets/301ede3e-40ea-4a25-a7f0-9b63a531682b)

### 운동 기록 수정 전/후
![운동 기록 수정 전, 후](https://github.com/user-attachments/assets/3b5b56f7-d543-4d9c-95de-28d3d7867254)

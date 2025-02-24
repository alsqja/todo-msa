# 1. Build Stage (Gradle 빌드)
FROM gradle:8.11.1-jdk17 AS builder

# 2. 작업 디렉터리 설정
WORKDIR /app

# 3. 소스 코드 복사
COPY . /app

# 4. Gradle 캐시 사용 및 의존성 설치
RUN gradle clean build -x test --no-daemon

# 5. 최종 이미지 구성 (OpenJDK 17-slim)
FROM openjdk:17-slim

WORKDIR /app

# 6. 빌드 결과물 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 7. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

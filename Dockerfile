FROM gradle:8.7.0-jdk17 AS build

WORKDIR /app

COPY . .
RUN chmod +x ./gradlew && ./gradlew build -x test

FROM openjdk:17

WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 실행 환경으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

ENV JWT_SECRET_KEY=${JWT_SECRET_KEY} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD}

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=prod"]
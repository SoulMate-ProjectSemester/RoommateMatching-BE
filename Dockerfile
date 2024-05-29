FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/soulmate-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 정적 리소스를 Docker 이미지에 포함
COPY src/main/resources/static /static

ENTRYPOINT ["java", "-jar", "/app.jar"]

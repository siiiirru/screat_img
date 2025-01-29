# OpenJDK 기반 이미지 사용
FROM openjdk:17-jdk-slim

# JAR 파일을 컨테이너에 복사
ARG JAR_FILE=build/libs/screat_img-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} docker-springboot.jar
COPY src/main/resources/keystore.p12 /config/keystore.p12
# 컨테이너 실행 시 JAR 파일 실행
ENTRYPOINT ["java", "-jar","-Djava.security.egd=file:/dev/./urandom","-Djavax.net.ssl.key-store=/config/keystore.p12","-Djavax.net.ssl.key-store-password=", "docker-springboot.jar"]

docker-compose down
docker-compose up -d mariadb
./gradlew build
docker-compose up springboot-app --build
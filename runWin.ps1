./gradlew build -Dquarkus.package.type=native
docker-compose build
docker-compose up -d
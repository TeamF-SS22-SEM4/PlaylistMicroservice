docker-compose down
docker-compose -f infrastructure/redis.yml down

docker-compose -f infrastructure/redis.yml up -d
./gradlew quarkusBuild
docker-compose build
docker-compose up -d
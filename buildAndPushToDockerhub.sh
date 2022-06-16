./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t smighty/playlist-service .
docker push smighty/playlist-service:latest
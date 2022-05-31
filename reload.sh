./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-playlist-microservice-jvm .
kubectl rollout restart deployment/playlist-app
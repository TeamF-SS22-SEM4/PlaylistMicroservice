kubectl delete deployments playlist-app
kubectl delete service playlist-service
./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-playlist-microservice-jvm .
kubectl apply -f ./test-deployment.yml
# TODO figure out what types are available here and which is best
kubectl expose deployment playlist-app --type=LoadBalancer --name=playlist-service

# optional, displays information after the cluster has been created
# kubectl describe deployment playlist-app
# kubectl describe service playlist-service

# after this script a hello world should be available at localhost:8080/purchase
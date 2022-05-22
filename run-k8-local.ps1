# redis stuff
kubectl delete -f ./kubernetes/deploy/redis-queue-config.yml
kubectl delete -f ./kubernetes/deploy/redis-queue.yml
kubectl delete service queue-service

kubectl apply -f ./kubernetes/deploy/redis-queue-config.yml
kubectl apply -f ./kubernetes/deploy/redis-queue.yml
kubectl expose pod redis-queue --type=LoadBalancer --name=queue-service


kubectl delete deployments playlist-app
kubectl delete service playlist-service
./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-playlist-microservice-jvm .
kubectl apply -f ./kubernetes/deploy/test-deployment.yml
# TODO figure out what types are available here and which is best
kubectl expose deployment playlist-app --type=LoadBalancer --name=playlist-service

# optional, displays information after the cluster has been created
# kubectl describe deployment playlist-app
# kubectl describe service playlist-service

# after this script a hello world should be available at localhost:8080/purchase
kubectl delete -f ./kubernetes/deploy/redis-queue-config.yml
kubectl delete -f ./kubernetes/deploy/redis-queue.yml
kubectl delete service queue-service

kubectl delete deployment,svc postgresql
kubectl delete pvc postgresql-pv-claim
kubectl delete pv postgresql-pv-volume

kubectl delete deployments playlist-app
kubectl delete service playlist-service

# kubectl delete -f .\kubernetes\secret\postgresql.secret.yml

# postgre-credentials
# kubectl apply -f .\kubernetes\secret\postgresql.secret.yml

# redis stuff
kubectl apply -f ./kubernetes/deploy/redis-queue-config.yml
kubectl apply -f ./kubernetes/deploy/redis-queue.yml
kubectl expose pod redis-queue --type=LoadBalancer --name=queue-service

# postgres
kubectl apply -f ./kubernetes/deploy/postgres-persistent-volume.yml
kubectl apply -f ./kubernetes/deploy/postgres-deployment.yml

./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-playlist-microservice-jvm .
kubectl apply -f ./kubernetes/deploy/test-deployment.yml
# TODO figure out what types are available here and which is best
kubectl expose deployment playlist-app --type=LoadBalancer --name=playlist-service

# optional, displays information after the cluster has been created
# kubectl describe deployment playlist-app
# kubectl describe service playlist-service

# after this script a hello world should be available at localhost:8080/purchase
kubectl delete -f ./kubernetes/deploy/redis-queue-config.yml
kubectl delete -f ./kubernetes/deploy/redis-queue.yml

kubectl delete deployment,svc postgresql
kubectl delete pvc postgresql-pv-claim
kubectl delete pv postgresql-pv-volume

kubectl delete deployments playlist-app

kubectl delete -f .\kubernetes\deploy\secret.yml

kubectl apply -f .\kubernetes\deploy\secret.yml

# redis stuff
kubectl apply -f ./kubernetes/deploy/redis-queue-config.yml
kubectl apply -f ./kubernetes/deploy/redis-queue.yml

# postgres
kubectl apply -f ./kubernetes/deploy/postgres-persistent-volume.yml
kubectl apply -f ./kubernetes/deploy/postgres-deployment.yml

./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t team-f-playlist-microservice-jvm .
kubectl apply -f ./kubernetes/deploy/playlist-deployment.yml
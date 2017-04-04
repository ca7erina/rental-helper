# Pull new code and re-build docker image
cd /home/vuongnq.09/code/rental-helper && git stash && git pull --rebase
cp /home/vuongnq.09/config/application.conf /home/vuongnq.09/code/rental-helper/conf/

# Build image
cd /home/vuongnq.09/code/rental-helper && sbt docker:publishLocal

# Tag image and push to Docker Hub
docker tag rental-helper:1.0-SNAPSHOT vn09/rental-helper:1.0-SNAPSHOT
docker push vn09/rental-helper:1.0-SNAPSHOT

# Pull new image and update the service
docker pull vn09/rental-helper:1.0-SNAPSHOT
docker service update --force rental-helper
docker service scale rental-helper=5
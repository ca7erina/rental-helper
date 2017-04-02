# Pull new code and re-build docker image
cd ~/code/rental-helper && git pull --rebase
cd ~/code/rental-helper && sbt docker:publishLocal

# Tag image and push to Docker Hub
docker tag rental-helper:1.0-SNAPSHOT vn09/rental-helper:1.0-SNAPSHOT
docker push vn09/rental-helper:1.0-SNAPSHOT

# Pull new image and update the service
docker pull vn09/rental-helper:1.0-SNAPSHOT
docker service update --force rental-helper
docker service scale rental-helper=4
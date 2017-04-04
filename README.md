# Rental Helper
This project is a group work of people at Trinity College Dublinto build web application that helps people find accommodation in group easily. For distribution of database, we use Mysql and Mysql Fabric.

# Status
[![Build Status](https://travis-ci.org/vn09/rental-helper.png)](https://travis-ci.org/vn09/rental-helper)
# How to run
1. Install `sbt` package:
   + Ubuntu: See [install-requirements.sh](scripts/install-requirements.sh)
   + Mac OS X: See [Mac OS X](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Mac.html)
2. Create database named `default` in your MySQL server.
3. Rename the file [application.conf.example](conf/application.conf.example) to [application.conf](conf/application.conf)
4. Specify your MySQL credentials in [application.conf](conf/application.conf)
4. Run
    ```
    sbt run
    ```
5. Open browser and go the address: http://localhost:9000

# Dockerize application
## Create docker image
1. Update [build.sbt](build.sbt) to include the information about maintainer, exposed port
    ```
    // --------  For docker stuff ----------
    maintainer := "vuongnq.09@gmail.com"
    dockerExposedPorts in Docker := Seq(9000, 9443)
    // -------------------------------------
    ```

2. Create the image for the application
    ```
    sbt docker:publishLocal
    ```
The created image showed in the docker image list
    ```
    docker images
    ```
    ![Docker images](public/images/docker_images.png?raw=true)

3. Tag the image and publish to the Docker hub
    ```
    docker tag rental-helper:1.0-SNAPSHOT vn09/rental-helper:1.0-SNAPSHOT
    docker push vn09/rental-helper:1.0-SNAPSHOT
    ```
with `vn09` is your [Docker Hub](https://hub.docker.com/) username.

## Building the cluster of node with Docker Swarm
1. Install docker 
    ```
    wget -qO- https://get.docker.com | sh
    sudo usermod -aG docker vuongnq.09
    ```
The later command to add the user into sudo mode for docker.

2. Create new cluster
    ```
    # Initiliase from manager
    docker swarm init --advertise-addr 10.132.0.5
    
    # Join from node
    docker swarm join --token SWMTKN-1-2b9tmc9id6lvyf5m8v4jevoy6ywl3x38o79hvwit0bxm385o47-5c6d2epkjqwadn1b7dk0m2wh5 10.132.0.5:2377
    ```
    
    with `10.132.0.5` is the IP of the manager node.

3. Check the cluster
    ```
    docker node ls
    ```
![Cluster of nodes](public/images/cluster_nodes.png?raw=true)

4. Deploy service
    ```
    docker service create --name rental-helper -p 80:9000 vn09/rental-helper:1.0-SNAPSHOT
    ```
  
    `-p 80:9000` to map the host's port `80` to the machine's port `9000`

5. Test
    ```
    curl 10.132.0.5
    ```
    with `10.132.0.5` is the machine's IP. 

6. Enjoy your Fabric

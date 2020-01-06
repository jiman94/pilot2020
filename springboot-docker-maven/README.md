# spring-boot-docker

# 도커 이미지 생성 & 컨테이너 실행

./gradlew clean build buildDocker

#### 이미지 확인(이미지아이디, 리파지토리명, 태그 확인)

docker images -a

#### 컨테이너 아이디 확인

docker ps -a

#### 모든 도커 컨테이너 삭제(remove all docker containers)
- 구동중인 모든 도커 컨테이너들을 중지시키고, 삭제한다.

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

#### 모든 도커 이미지 삭제(remove all docker images)

docker rmi $(docker images -q)

docker system prune -a 


### mysql Container 구동 
```bash 

docker run -d --name spring-boot-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=docker -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbpassword -d mysql:latest

```

### mysql Container --link 사용 하기  

```bash 
docker run --name spring-boot-docker --link spring-boot-mysql:mysql -p 8080:8080  -d man/spring-boot-docker

```

http://192.168.99.100:8080/accounts


Spring boot docker Sample


```
$ git clone https://github.com/pilot/spring-boot-docker.git
$ cd spring-boot-docker
$ mvn clean package docker:build
```

### if **No route to host** exception?
```
$ iptables -t filter -A DOCKER -d 172.17.0.0/16 -i docker0 -j ACCEPT
```


mvn package docker:build

mvn package && java -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar

mvn package && java -Dserver.port=9000 -jar target/spring-boot-docker-0.0.1-SNAPSHOT.jar 

docker run -p 8080:8080 -t wonwoo/spring-boot-docker

docker run -p 8081:8080 -t wonwoo/spring-boot-docker


---

###
docker pull mysql

docker run --detach --env MYSQL_ROOT_PASSWORD=0000 --env MYSQL_USER=grissom --env MYSQL_PASSWORD=grissom --env MYSQL_DATABASE=study --name local_mysql --publish 4306:3306 mysql:latest;

### Docker 컨테이너로 접속
docker exec -it local_mysql bash

### Docker 컨테이너의 mysql db로 로그인 

root@:/# mysql -u root -p

root@:/# use study;

### table 생성하기
create table employees (id bigint(20) not null auto_increment, name varchar(255) not null, primary key (id));

docker run -p 8080:8080 --name springboot-docker --link local_mysql:local_mysql -t springboot-docker:0.0.1


---

### 

docker images : 이미지를 보여준다.
docker ps -a : 모든 컨테이너 정보다.
docker rm $CONTAINER_ID : 컨테이너를 삭제한다.
docker start $CONTAINER_ID : 컨테이너를 시작한다.
docker stop $CONTAINER_ID : 컨테이너를 중지한다. 
docker logs $CONTAINER_ID : 로그를 확인한다.
docker top $CONTAINER_ID : 프로세서 정보를 확인한다.
docker inspect $CONTAINER_ID : 컨테이너의 모든 정보를 보여준다.(JSON)
docker port $CONTAINER_ID : 포트가 어디로 연결 되었있는지 보여준다.


docker run --rm --tty --interactive --net=host --volume=$PWD:/opt/acbuild --workdir=/opt/acbuild golang:1.5 ${@-./build}

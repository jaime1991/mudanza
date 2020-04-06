#creacion red en docker
docker network create springcloud 

#servicio mongodb
docker run -it -p 27017:27017 --network springcloud --name mongodb-service -d mongo:tag

#service dicovery
docker build -t image-eureka-service:v1 .
docker run -it -p 8761 --name eureka-service --network springcloud image-eureka-service:v1

#Api gateway
docker build -t image-gateway-service:v1 .
docker run -it -p 8080:8080 --name gateway-service --network springcloud image-gateway-service:v1

#servicio mudanza
docker build -t image-mudanza-service:v1 .
docker run -it -p 8001 --name mudanza-service --network springcloud image-mudanza-service:v1

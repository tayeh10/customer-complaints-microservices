## Customer Complaints Management System

### Use with Docker Development Environments

You can open this project in the Dev Environments feature of Docker Desktop version 4.12 or later.

[Open in Docker Dev Environments <img src="./images/open_in_new.svg" alt="Open in Docker Dev Environments" align="top"/>](https://open.docker.com/dashboard/dev-envs?url=https://github.com/tayeh10/customer-complaints-microservices)

### Spring backend and H2 in memory database

Project structure:
```
.
├── api-gateway
│   ├── Dockerfile
│   ...
├── complaint-service
│   └── Dockerfile
│   ...
├── config-server
│   └── Dockerfile
├── docker-compose.yaml
├── README.md
├── service-registry
│   └── Dockerfile
└──
```

[_docker_compose.yaml_](docker_compose.yaml)
```
services:
  complaints-system-craft-mock:
    build:
      context: ./complaints-system-craft-mock
    ports:
      - "8081:8081"

  complaint-service:
    build:
      context: ./complaint-service
    environment:
      - USER_SERVICE_URL=http://host.docker.internal:8081/users/
      - PURCHASE_SERVICE_URL=http://host.docker.internal:8081/purchases/
    ports:
      - "8082:8082"
    depends_on:
      - complaints-system-craft-mock
```
The compose file defines an application with two services `complaints-system-craft-mock` and `complaint-service`.
When deploying the application, docker compose maps port 8081 of the complaints-system-craft-mock service container to port 8081 of the host as specified in the file.
It's also maps port 8082 of the complaint-service service container to port 8082 of the host as specified in the file.
Make sure both ports: 8081 and 8082 on the host are not already being in use.

> ℹ️ **_NOTE_**
> The project use in memory db H2 which being defined on complaint-service service. So each run of the service need to test the flows with new api's run and new data.
> I wanted to do it scalable using Spring Cloud Eureka Server, but I encountered problem when running on docker.
> Docker returned connection refused error when assign clients to eureka server in the docker localhost.
> In my local machine it's working fine.
> So to avoid these failure on docker I commented the lines in pom.xml and application.properties of the service complaint-service.
> The project tested fine using docker when deploy with docker compose.

## Deploy with docker compose

> ℹ️ **_NOTE_**
> Before deploy with docker compose need to run mvn install for complaint-service so the jar complaint-service-0.0.1-SNAPSHOT.jar will be created under target directory.

```
Go to the directory where is the file docker_compose.yaml and run the command
$ docker-compose up --build -d
[+] Building 1.3s (13/13) FINISHED
 => [complaints-system-craft-mock internal] load build definition from Dockerfile                                                                                                                           0.1s
 => => transferring dockerfile: 224B                                                                                                                                                                        0.0s
 => [complaints-system-craft-mock internal] load .dockerignore                                                                                                                                              0.1s
 => => transferring context: 2B                                                                                                                                                                             0.0s
 => [complaint-service internal] load metadata for docker.io/library/eclipse-temurin:17                                                                                                                     0.0s
 => [complaint-service 1/3] FROM docker.io/library/eclipse-temurin:17                                                                                                                                       0.0s
 => [complaints-system-craft-mock internal] load build context                                                                                                                                              0.0s
 => => transferring context: 56B                                                                                                                                                                            0.0s
 => CACHED [complaint-service 2/3] WORKDIR /app                                                                                                                                                             0.0s
 => CACHED [complaints-system-craft-mock 3/3] COPY complaints-system-craft-mock.jar /app/complaints-system-craft-mock.jar                                                                                   0.0s
 => [complaints-system-craft-mock] exporting to image                                                                                                                                                       0.0s
 => => exporting layers                                                                                                                                                                                     0.0s
 => => writing image sha256:b341ec458795b21089f26ee95873ef7459694be9a654bcc38688fcd7e5363750                                                                                                                0.0s
 => => naming to docker.io/library/customer-complaints-microservices-complaints-system-craft-mock                                                                                                           0.0s
 => [complaint-service internal] load build definition from Dockerfile                                                                                                                                      0.1s
 => => transferring dockerfile: 213B                                                                                                                                                                        0.0s
 => [complaint-service internal] load .dockerignore                                                                                                                                                         0.1s
 => => transferring context: 2B                                                                                                                                                                             0.0s
 => [complaint-service internal] load build context                                                                                                                                                         0.0s
 => => transferring context: 93B                                                                                                                                                                            0.0s
 => CACHED [complaint-service 3/3] COPY target/complaint-service-0.0.1-SNAPSHOT.jar /app/complaint-service.jar                                                                                              0.0s
 => [complaint-service] exporting to image                                                                                                                                                                  0.0s
 => => exporting layers                                                                                                                                                                                     0.0s
 => => writing image sha256:9cf4ec354e8d9a775be546eb801f4b00d4f832e499f7029e1790dab69ed6c6f8                                                                                                                0.0s
 => => naming to docker.io/library/customer-complaints-microservices-complaint-service                                                                                                                      0.0s
time="2023-12-04T12:01:10+02:00" level=warning msg="Found orphan containers ([customer-complaints-microservices-api-gateway-1 customer-complaints-microservices-service-registry-1 customer-complaints-microservices-config-server-1 customer-complaints-microservices-complaint-system-craft-mock-1]) for this project. If you removed or renamed this service in your compose file, you can run this command with the --remove-orphans flag to clean it up."
[+] Running 2/2
 ✔ Container customer-complaints-microservices-complaints-system-craft-mock-1  Started                                                                                                                      1.4s
 ✔ Container customer-complaints-microservices-complaint-service-1             Started           Started
```

## Expected result

Listing containers must show three containers running and the port mapping as below:
```
PS C:\Users\TOSHIBA> docker ps
CONTAINER ID   IMAGE                                                            COMMAND                  CREATED       STATUS         PORTS                    NAMES
d388940188c8   customer-complaints-microservices-complaint-service              "java -jar complaint…"   3 hours ago   Up 2 minutes   0.0.0.0:8082->8082/tcp   customer-complaints-microservices-complaint-service-1
6f6bfb5f4bc6   customer-complaints-microservices-complaints-system-craft-mock   "java -jar complaint…"   6 hours ago   Up 2 minutes   0.0.0.0:8081->8081/tcp   customer-complaints-microservices-complaints-system-craft-mock-1
```

After the application starts, need to test first that mock service is working fine.
You can use Postman running mock rest API's.

> ℹ️ **_NOTE_**
> The user id's and purchses id's data which the mock returned located at the task pdf.
> I will give run examples for mock values located from the task PDF.

The mock expose two API's:
1. Get User By Id:
    http://localhost:8081/users/a93adc57-4d59-4a9d-85c6-b5d48d99101d
    You should get below result:
    ![page](images/mock_get_user.png)

2. Get Purchase By Id:
   http://localhost:8081/purchases/f256c996-6dcb-40cf-8dce-a11fa9bcab6b
   You should get below result:
   ![page](images/mock_get_purchase.png)

The second service which I developed using Spring Boot "complaint-service" expose two API's:

1. Create complaint - POST:

    http://localhost:8082/api/complaints


    Example Input:
    {
    "userId":"a93adc57-4d59-4a9d-85c6-b5d48d99101d",
    "subject":"The seller never sent my item!",
    "complaint":"I made a purchase and the item hasn’t shipped. It’s been over a week. Please help!",
    "purchaseId":"f256c996-6dcb-40cf-8dce-a11fa9bcab6b"
    }


    Example Output:
    {
    "id": "27a41053-4e5a-466e-a4a1-50d4e2e61427",
    "userId": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
    "subject": "The seller never sent my item!",
    "complaint": "I made a purchase and the item hasn’t shipped. It’s been over a week. Please help!",
    "purchaseId": "f256c996-6dcb-40cf-8dce-a11fa9bcab6b"
    }

![page](images/complaint_service_create_complaint.png)

2. Get complaint - Get:

    http://localhost:8082/api/complaints/2f523955-a542-4123-bed1-7ff82f50b1e6


    Example Output:
    {
    "complaint": {
        "id": "2f523955-a542-4123-bed1-7ff82f50b1e6",
        "userId": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
        "subject": "The seller never sent my item!",
        "complaint": "I made a purchase and the item hasn’t shipped. It’s been over a week. Please help!",
        "purchaseId": "f256c996-6dcb-40cf-8dce-a11fa9bcab6b"
    },
    "user": {
        "id": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
        "fullName": "John Doe",
        "emailAddress": "johndoe@test.com",
        "physicalAddress": "Test Lane 1 Los Angeles"
    },
    "purchase": {
        "id": "f256c996-6dcb-40cf-8dce-a11fa9bcab6b",
        "userId": "a93adc57-4d59-4a9d-85c6-b5d48d99101d",
        "productId": "4ac9da0b-66eb-415c-9153-a59ec0b3c3fe",
        "productName": "Book",
        "pricePaidAmount": 13.2,
        "priceCurrency": "USD",
        "discountPercent": 0.1,
        "merchantId": "71e234d2-dc65-41ff-bada-9d08d82fc6d1",
        "purchaseDate": "2020-11-21T00:00:00.000+00:00"
    }
}

![page](images/complaint_service_get_complaint.png)

Stop and remove the containers
```
$ docker compose down
[+] Running 3/3
 ✔ Container customer-complaints-microservices-complaint-service-1             Removed                                                                                                                      2.8s
 ✔ Container customer-complaints-microservices-complaints-system-craft-mock-1  Removed                                                                                                                      0.9s
 ✔ Network customer-complaints-microservices_default                           Removed
```
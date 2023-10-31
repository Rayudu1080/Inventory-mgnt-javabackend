FROM openjdk:17-jdk-slim

EXPOSE 8081

ADD target/sample-crud.jar sample-crud.jar

ENTRYPOINT ["java","-jar","/sample-crud.jar"]
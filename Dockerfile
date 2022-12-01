FROM openjdk:17-oracle
ADD api/target/schedule-api.jar schedule-api.jar
ENTRYPOINT ["java","-jar","schedule-api.jar"]
EXPOSE 8080
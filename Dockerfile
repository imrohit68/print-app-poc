FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/PrintAppPOC-0.0.1-SNAPSHOT.jar print-app-poc.jar
EXPOSE 443
CMD ["java","-jar","print-app-poc.jar"]

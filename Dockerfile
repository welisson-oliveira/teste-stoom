FROM openjdk:8
LABEL maintainer=welisson
WORKDIR /app/
COPY target/store-1.0.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
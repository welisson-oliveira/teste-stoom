FROM openjdk:8
LABEL maintainer=welisson
WORKDIR /app/
RUN mkdir /app/files
COPY target/store-1.0.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar", "--spring.profiles.active=docker"]
EXPOSE 8080
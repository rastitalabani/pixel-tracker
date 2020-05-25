FROM java:8-jdk-alpine
COPY ./target/pixel-tracker-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pixel-tracker-0.0.1-SNAPSHOT.jar"]

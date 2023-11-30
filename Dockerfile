FROM amazoncorretto:20-alpine-jdk
WORKDIR /app
COPY ./target/tcc.jar /app
EXPOSE 8080
CMD ["java","-jar", "tcc.jar"]
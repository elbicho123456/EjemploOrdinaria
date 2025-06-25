FROM amazoncorretto:17
WORKDIR /app
COPY target/MEG-0.0.1-SNAPSHOT.jar app.jar
COPY usuarios.json usuarios.json
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

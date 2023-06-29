FROM ibmcom/semeru-runtimes:17-jdk-alpine
VOLUME /tmp
COPY target/ToeicExam-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080

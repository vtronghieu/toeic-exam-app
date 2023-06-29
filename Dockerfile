FROM ibmcom/ibm-semeru-runtimes:17-jdk
VOLUME /tmp
COPY target/ToeicExam-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080

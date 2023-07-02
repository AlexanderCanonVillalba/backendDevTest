FROM openjdk:17-jdk-alpine
COPY target/devtest-0.0.1-SNAPSHOT.jar java-app.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","java-app.jar"]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar pruebatecnica.jar
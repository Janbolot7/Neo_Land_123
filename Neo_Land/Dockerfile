FROM openjdk:11
ARG FILE_NAME=target/Neobis-0.0.1-SNAPSHOT.jar
ADD ${FILE_NAME} Neobis.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Neobis.jar"]

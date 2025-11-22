

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/api-produits-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh wait-for-it.sh

# Le chmod se fait dans l'image Linux
RUN chmod +x wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["./wait-for-it.sh", "rabbitmq:5672", "--", "java", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-jar", "app.jar"]
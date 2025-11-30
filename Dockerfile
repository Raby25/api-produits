FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copier le JAR et le script wait-for-it
COPY target/*.jar api-produits-0.0.1-SNAPSHOT.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Entrypoint unique : attendre PostgreSQL et RabbitMQ
ENTRYPOINT ["sh", "-c", "./wait-for-it.sh produits-db:5432 --timeout=120 --strict -- ./wait-for-it.sh rabbitmq:5672 --timeout=60 --strict -- java -jar api-produits-0.0.1-SNAPSHOT.jar"]

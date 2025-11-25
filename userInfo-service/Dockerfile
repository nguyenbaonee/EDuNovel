FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Cài netcat để wait-for-it.sh dùng được
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

COPY target/userInfo-service-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8081

ENTRYPOINT ["/wait-for-it.sh", "neo4j:7687", "java", "-jar", "app.jar"]

#  iam-service:
#    build:
#      context: ./Iam-service
#      dockerfile: Dockerfile
#    container_name: iam-service
#    depends_on:
#      - iam-db
#      - userinfo-service
#    ports:
#      - "8082:8082"
#    networks:
#      - backend-network
#    environment:
#      APP_SERVICES_PROFILE: http://userinfo-service:8081
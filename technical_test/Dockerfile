# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace
COPY . .
RUN chmod +x mvnw && ./mvnw -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/target/technical_test-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

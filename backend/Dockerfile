# Etapa 1: Compilación
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Etapa 2: Ejecución (Cambiamos esta línea)
FROM amazoncorretto:17-alpine-jdk
COPY --from=build /target/*.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java", "-jar", "app.jar"]

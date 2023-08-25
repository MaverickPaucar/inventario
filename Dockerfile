FROM eclipse-temurin:17-jre-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} inventario.jar
ENTRYPOINT ["java", "-jar", "/inventario.jar"]
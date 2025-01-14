# Use a imagem base do OpenJDK
FROM openjdk:23-jdk-slim

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven para o container
COPY target/email-service-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação utiliza
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

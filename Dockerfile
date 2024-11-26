#FROM openjdk:17-jdk-slim
#COPY target/mywebsite-1.0-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app.jar"]

# Usa una imagen base oficial de Tomcat
FROM tomcat:9.0.80-jdk17

# Copia el archivo WAR al directorio webapps del Tomcat
COPY target/mywebsite-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/mywebsite.war

# Expón el puerto 8080 donde correrá Tomcat
EXPOSE 8080

# Configura el comando por defecto para iniciar Tomcat
CMD ["catalina.sh", "run"]

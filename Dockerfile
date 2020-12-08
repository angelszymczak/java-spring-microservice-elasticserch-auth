FROM java:8
WORKDIR '/app'
ADD target/scanner-0.0.1-SNAPSHOT.jar magneto.jar
EXPOSE 8086
COPY . .
ENTRYPOINT ["java", "-jar", "magneto.jar"]
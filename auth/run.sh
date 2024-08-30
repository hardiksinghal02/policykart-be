mvn clean
mvn clean install
docker build -t policykart-auth-service:latest .
docker run -p 8081:8081 policykart-auth-service:latest

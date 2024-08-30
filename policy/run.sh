mvn clean
mvn clean install
docker build -t policykart-policy-service:latest .
docker run -p 8080:8080 policykart-policy-service:latest

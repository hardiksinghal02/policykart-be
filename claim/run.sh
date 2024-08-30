mvn clean
mvn clean install
docker build -t policykart-claim-service:latest .
docker run -p 8082:8082 policykart-claim-service:latest

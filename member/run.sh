mvn clean
mvn clean install
docker build -t policykart-member-service:latest .
docker run -p 8083:8083 policykart-member-service:latest

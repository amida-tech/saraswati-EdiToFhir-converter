mvn clean package
docker build -t amidatech/saraswati-editofhir-converter .
docker run -p 8080:8080 -t amidatech/saraswati-editofhir-converter

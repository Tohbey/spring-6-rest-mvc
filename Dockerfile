FROM eclipse-temurin:21-jre-jammy
LABEL authors="oluwatobilobafafowora"
WORKDIR /app
COPY target/*.jar spring-6-rest-mvc.jar
COPY src/main/resources/csvData/beers.csv resources/csvData/beers.csv
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "spring-6-rest-mvc.jar"]
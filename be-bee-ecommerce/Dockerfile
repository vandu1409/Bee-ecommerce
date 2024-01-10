FROM maven:3.8.5

WORKDIR /app

COPY ./pom.xml .
RUN mvn dependency:resolve

COPY src ./src
RUN mvn package

EXPOSE 8080
CMD ["java", "-jar", "target/bee-ecommerce-1.0.1.jar"]

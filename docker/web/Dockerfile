FROM maven:3.6.3-jdk-8 as builder

WORKDIR /itetenosuke
# pom.xmlだけCOPYし、先に依存解決
COPY ./pom.xml /itetenosuke/
RUN mvn -B package; echo ""
COPY ./src/ /itetenosuke/src/
RUN mvn -B package

FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
RUN mkdir -p static/images && chmod -R 777 static/images
USER spring:spring

COPY --from=builder /itetenosuke/target/Itete-no-suke-1.0.0-SNAPSHOT.jar /
ENV CLASSPATH /Itete-no-suke-1.0.0-SNAPSHOT.jar

CMD [ "java", "-jar", "Itete-no-suke-1.0.0-SNAPSHOT.jar" ]

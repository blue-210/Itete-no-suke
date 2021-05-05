FROM gradle:7.0.0-jre11 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build bootjar

FROM openjdk:11-jre-slim
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*jar /app/Itete-no-suke-1.0.0-SNAPSHOT.jar
ENV CLASSPATH /Itete-no-suke-1.0.0-SNAPSHOT.jar

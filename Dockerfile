FROM openjdk:12-alpine3.9
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
WORKDIR home/spring/
COPY build/libs/readinglist-0.1.0.jar .
EXPOSE 8000
CMD ["java", "-jar", "readinglist-0.1.0.jar"]
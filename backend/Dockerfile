FROM --platform=linux/amd64 maven:3.8.4-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Include test env vars
ARG MONGO_URI
ARG SECRET_KEY
ARG FRONTEND_URL
ENV MONGO_URI=$MONGO_URI
ENV SECRET_KEY=$SECRET_KEY
ENV FRONTEND_URL=$FRONTEND_URL

# Run tests, then build WAR
RUN mvn clean test package

FROM --platform=linux/amd64 tomcat:9.0-jdk17-temurin

# Clean default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file into ROOT
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
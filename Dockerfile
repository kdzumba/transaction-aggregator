# ==============================
# Build stage
# ==============================
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /capitec-transaction-aggregator

# Copy project files
COPY pom.xml .
COPY src ./src

# Optional: copy migration scripts if you need them separately
COPY src/main/java/za/co/shyftit/capitectransactionaggregator/database/migrations /data/sql_migrations

# Build the project and create the WAR
RUN mvn clean package -DskipTests

# ==============================
# Run stage
# ==============================
FROM tomcat:10.1.5

# Remove default ROOT webapp
RUN rm -rf $CATALINA_HOME/webapps/ROOT

# Copy WAR as ROOT.war so it deploys at root context
COPY --from=build /capitec-transaction-aggregator/target/CapitecTransactionAggregator.war $CATALINA_HOME/webapps/ROOT.war

# Copy migrations
RUN mkdir -p /data/sql_migrations
COPY --from=build /data/sql_migrations /data/sql_migrations

# Set environment variable for Flyway
ENV FLYWAY_MIGRATIONS_PATH=/data/sql_migrations

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]

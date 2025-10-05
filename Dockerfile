# Build stage

FROM maven:3.9.3-eclipse-temurin-17 as build

COPY . /capitec-transaction-aggregator/
WORKDIR /capitec-transaction-aggregator/
COPY /src/main/java/za/co/shyftit/capitectransactionaggregator/database/migrations /src/main/java/za/co/shyftit/capitectransactionaggregator/database/migrations


RUN mvn clean package

# Run stage
FROM tomcat:10.1.5 as run
COPY --from=build /capitec-transaction-aggregator/target/CapitecTransactionAggregator.war "$CATALINA_HOME"/webapps
RUN mkdir -p /data/sql_migrations
COPY --from=build /src/main/java/za/co/shyftit/capitectransactionaggregator/database/migrations /data/sql_migrations

ENV FLYWAY_MIGRATIONS_PATH=/data/sql_migrations

WORKDIR "$CATALINA_HOME"/webapps

CMD ["catalina.sh", "run"]

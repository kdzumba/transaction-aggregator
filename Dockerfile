FROM maven:latest as build

COPY . /capitec-transaction-aggregator/

WORKDIR /capitec-transaction-aggregator/


RUN mvn clean package

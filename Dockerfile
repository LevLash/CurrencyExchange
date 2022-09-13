FROM openjdk:18
LABEL maintainer="currency_exchange.net"
ADD build/libs/Currency_exchange-0.0.1-SNAPSHOT.jar currency_exchange_docker.jar
ENTRYPOINT ["java", "-jar", "currency_exchange_docker.jar"]
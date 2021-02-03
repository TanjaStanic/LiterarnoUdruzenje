FROM anapsix/alpine-java
MAINTAINER Natasa Lukic <natasalukic2@gmail.com>
ADD target/payment-info-0.0.1-SNAPSHOT.jar payment-info.jar
ENTRYPOINT ["java", "-jar", "/payment-info.jar"]
EXPOSE 8444

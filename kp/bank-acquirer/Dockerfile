FROM anapsix/alpine-java
MAINTAINER Natasa Lukic <natasalukic2@gmail.com>
ADD target/bank-acquirer-0.0.1-SNAPSHOT.jar bank-acquirer.jar
ENTRYPOINT ["java", "-jar", "/bank-acquirer.jar"]
EXPOSE 8445

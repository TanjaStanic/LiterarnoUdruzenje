FROM anapsix/alpine-java
MAINTAINER Natasa Lukic <natasalukic2@gmail.com>
ADD target/bank-pcc-0.0.1-SNAPSHOT.jar bank-pcc.jar
ENTRYPOINT ["java", "-jar", "/bank-pcc.jar"]
EXPOSE 8446

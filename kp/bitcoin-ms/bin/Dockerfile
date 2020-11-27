FROM anapsix/alpine-java
MAINTAINER Natasa Lukic <natasalukic2@gmail.com>
ADD target/bitcoin-ms-0.0.1-SNAPSHOT.jar bitcoin-ms.jar
ENTRYPOINT ["java", "-jar", "/bitcoin-ms.jar"]
EXPOSE 8442

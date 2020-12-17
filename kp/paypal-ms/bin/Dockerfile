FROM anapsix/alpine-java
MAINTAINER Natasa Lukic <natasalukic2@gmail.com>
ADD target/paypal-ms-0.0.1-SNAPSHOT.jar paypal-ms.jar
ENTRYPOINT ["java", "-jar", "/paypal-ms.jar"]
EXPOSE 8443

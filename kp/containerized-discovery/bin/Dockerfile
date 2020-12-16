FROM anapsix/alpine-java
MAINTAINER Natasa Lukic <natasalukic2@gmail.com>
ADD target/containerized-discovery-0.0.1-SNAPSHOT.jar containerized-discovery.jar
ENTRYPOINT ["java", "-jar", "/containerized-discovery.jar"]
EXPOSE 8761

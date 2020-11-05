FROM java:8
COPY target/ServerController-0.0.1-SNAPSHOT.jar /webserver.jar
COPY pemvol/amd-cluster.pem /pemvol/amd-cluster.pem
EXPOSE 8080
ENTRYPOINT ["java","-jar","/webserver.jar"]

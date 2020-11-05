FROM java:8
COPY target/KafkaConsumer-0.0.1-SNAPSHOT.jar /consumer.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/consumer.jar"]  

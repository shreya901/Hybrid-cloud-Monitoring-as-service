FROM java:8
COPY target/kafkaStarter-0.0.1-SNAPSHOT.jar /producer.jar
COPY Stress/Stress.sh /Stress/Stress.sh
COPY Stress/kpi.sh /kpi.sh
COPY Stress/Entrypoint.sh /Entrypoint.sh

RUN ["chmod", "+x", "/Stress/Stress.sh"]
RUN ["chmod", "+x", "/kpi.sh"]
RUN ["chmod", "+x", "/Entrypoint.sh"]
EXPOSE 8085
ENTRYPOINT ["/Entrypoint.sh"]



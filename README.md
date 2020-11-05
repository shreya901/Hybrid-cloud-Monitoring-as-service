# Hybrid-cloud-Monitoring-as-service
Hybrid Cloud workload monitoring as a service

kafkaStarter - SpringBoot applicaton modeled as producer which generates workload within the container(PaaS layer) and writes data to topic respective to load creation(high,medium,low)


KafkaConsumer  - SpringBoot applicaton modeled as consumer which reads KPI metrics and writes to InfluxDB





VmAgent - SpringBoot applicaton that tracks workload in (VMs) the Iaas layer and writes to InfluxDB

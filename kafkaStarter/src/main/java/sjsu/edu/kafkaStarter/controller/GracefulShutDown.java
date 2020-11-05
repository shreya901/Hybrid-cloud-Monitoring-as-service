package sjsu.edu.kafkaStarter.controller;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import sjsu.edu.kafkaStarter.model.Create_Info;
import sjsu.edu.kafkaStarter.model.FaultyContainerInfo;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class GracefulShutDown implements  TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
	private static final Logger log = LoggerFactory.getLogger(GracefulShutDown.class);
	private volatile Connector connector;
	private static final String TOPIC_F="Faulty";
	
	@Autowired
	private KafkaTemplate<String,FaultyContainerInfo> kafkaFaultyTemplate;
	
	
	@Override
	public void customize(Connector connector) {
		this.connector=connector;
	}
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		this.connector.pause();
		try {
			ThreadPoolExecutor executor = (ThreadPoolExecutor) this.connector.getProtocolHandler().getExecutor();
			kafkaFaultyTemplate.send(TOPIC_F,Create_Info.faultyInfos());
			log.debug("Sending Faulty information before shutdown");
			executor.shutdown();
			if (!executor.awaitTermination(30, TimeUnit.SECONDS))
			{
				log.warn("Tomcat threadpool did not shutdown gracefully within 30 second"+
			"Forcefully shutting down");
		
			}
			
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		log.debug("Kill received");
		
	}

}

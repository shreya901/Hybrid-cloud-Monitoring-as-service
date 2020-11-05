package sjsu.edu.kafkaStarter.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import sjsu.edu.kafkaStarter.controller.GracefulShutDown;
import sjsu.edu.kafkaStarter.model.Create_Info;
import sjsu.edu.kafkaStarter.model.FaultyContainerInfo;
import sjsu.edu.kafkaStarter.model.Infos;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@PropertySource("classpath:/application.properties")
public class ProducerConfig1 {
	
	@Value(value = "${spring.kafka.producer.bootstrap-servers}")
    	private String bootstrapAddress;
	@Bean
    public ProducerFactory<String, FaultyContainerInfo> producerFaultyFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          JsonSerializer.class);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }	
	@Bean
    public KafkaTemplate<String, FaultyContainerInfo> kafkaFaultyTemplate() {
        return new KafkaTemplate<String, FaultyContainerInfo>(producerFaultyFactory());
    }
    
 
    @Bean
    public ProducerFactory<String, Infos> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          JsonSerializer.class);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }
 
    @Bean
    public KafkaTemplate<String, Infos> kafkaTemplate() {
        return new KafkaTemplate<String, Infos>(producerFactory());
    }
    
    @Bean
    public GracefulShutDown gracefulshutdown() {
    	return new GracefulShutDown();
    }
    @Bean
    public ConfigurableServletWebServerFactory webserverfactory( final GracefulShutDown gracefulshutdown) {
    	TomcatServletWebServerFactory factory =new TomcatServletWebServerFactory();
    	factory.addConnectorCustomizers(gracefulshutdown);
    	return factory;
    	
    }
    @Bean
	public Create_Info CrInf() {

		 
		 
		 Create_Info.SigarService();
		 return null;
	}
    
   
}
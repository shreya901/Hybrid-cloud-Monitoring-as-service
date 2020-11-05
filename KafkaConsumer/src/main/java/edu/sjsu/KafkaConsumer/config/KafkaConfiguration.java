package edu.sjsu.KafkaConsumer.config;


import edu.sjsu.KafkaConsumer.model.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.CommonClientConfigs;


import java.util.HashMap;
import java.util.Map;


import javax.annotation.PostConstruct;


@Configuration
@PropertySource("classpath:/application.properties")
@EnableKafka
public class KafkaConfiguration {
    @Value(value = "${spring.kafka.bootstrap-servers}")
	 private  String bootstrapAddress ;
	

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


    @Bean
    
    public ConsumerFactory<String, Infos> infoConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
       
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Infos.class,false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Infos> infoKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Infos> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(infoConsumerFactory());
        
        return factory;
    }
    
    @Bean
    public ConsumerFactory<String, FaultyContainerInfo> fcConsumerFactory(){
    	Map<String, Object> config = new HashMap<>();
        
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(FaultyContainerInfo.class,false));
    
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FaultyContainerInfo> faultyInfoKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FaultyContainerInfo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(fcConsumerFactory());
        
        return factory;
    }
    

}


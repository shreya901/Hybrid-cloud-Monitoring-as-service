package sjsu.edu.kafkaStarter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import sjsu.edu.kafkaStarter.model.Create_Info;
import sjsu.edu.kafkaStarter.model.Infos;

@RestController
public class ProducerController {
	
	@Autowired
	private KafkaTemplate<String, Infos> kafkaTemplate;
	private static final String TOPIC = "HighLoad";

	@Scheduled(fixedDelay=10000L)
	public void post()
	
	{
		
		
		try {
			
			kafkaTemplate.send(TOPIC,Create_Info.infos());
			//System.out.println(Create_Info.infos().toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

}

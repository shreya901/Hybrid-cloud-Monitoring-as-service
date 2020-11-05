package edu.sjsu.KafkaConsumer.controller;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import edu.sjsu.KafkaConsumer.dbConfig.DbUtility;
import edu.sjsu.KafkaConsumer.model.FaultyContainerInfo;
import lombok.extern.slf4j.Slf4j;
import edu.sjsu.KafkaConsumer.dbConfig.DbUtility;

@Service
@Slf4j
public class FaultyContainerReceiver {
	
private static DbUtility db=new DbUtility();
  @KafkaListener(topics = "Faulty", groupId = "group_json",
  containerFactory = "faultyInfoKafkaListenerFactory")
  @KafkaHandler
  public void consumeFaultyInfoJson(FaultyContainerInfo fc) {
  	
  	
	db.insertFC(fc);
  	log.debug("Inserted Faulty-Json Message: " + fc.toString());
  }

}

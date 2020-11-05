package edu.sjsu.KafkaConsumer.controller;
import edu.sjsu.KafkaConsumer.model.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import edu.sjsu.KafkaConsumer.dbConfig.DbUtility;
@Service
@Slf4j
public class InfoReceiver {
	private static DbUtility db=new DbUtility();

	@KafkaListener(topics = "MediumLoad", groupId = "group_json",containerFactory = "infoKafkaListenerFactory")
	@KafkaHandler
	public void consumeInfo(Infos info) {
		db.insert(info);
		log.debug("Inserted JSON Message: " + info.toString());
		
	}
	

}

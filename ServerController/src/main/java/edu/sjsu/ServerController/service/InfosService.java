package edu.sjsu.ServerController.service;

import org.springframework.stereotype.Service;
import java.util.List;
import edu.sjsu.ServerController.dbConfig.DbUtility;




@Service
public class InfosService {
	private static DbUtility dbU=new DbUtility(); 
	
	public <T> List<T> getInfos(String dbName, String hostName, String metricType) {
		
		return dbU.Query(dbName,hostName,metricType);		
        
    }

}

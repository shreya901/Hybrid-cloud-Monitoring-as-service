package edu.sjsu.AnalyzerPerMin.Controller;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.AnalyzerPerMin.Config.DbConnections;
import edu.sjsu.AnalyzerPerMin.Config.FluxQueryCpu;
import edu.sjsu.AnalyzerPerMin.Config.FluxQueryMemory;
import edu.sjsu.AnalyzerPerMin.NoDataException.NotEnoughData;
import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class Analyze {
	
	
	
	
	@Scheduled(fixedDelay=500000L)
	public void analyzeStats() 
	{
		log.debug("Inside analyzeStats");
		try {
			FluxQueryMemory.performQuery();
			FluxQueryCpu.performQuery();
			
		}
		catch(Exception e)
		{
			log.debug("Shutdown Initiated");
			log.error(e.toString());
			System.gc();
			ShutDownController.shutdownContext();
		}
		
	}
	
}

package edu.sjsu.VmAnalyzer.Controller;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.VmAnalyzer.Config.FluxQueryCpu;
import edu.sjsu.VmAnalyzer.Config.FluxQueryMemory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class Analyze {
	


	
	@Scheduled(fixedDelay=500000L)
	public void analyzeStats() 
	{
		log.debug("Inside analyzeStats");
		try {
			FluxQueryCpu.performQuery();
			FluxQueryMemory.performQuery();
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

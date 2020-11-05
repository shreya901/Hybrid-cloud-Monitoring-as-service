package edu.sjsu.VmAgent.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.sjsu.VmAgent.Entity.ContainerInfo;
import edu.sjsu.VmAgent.Entity.CreateContainerInfo;
import edu.sjsu.VmAgent.Entity.CreateInfo;
import edu.sjsu.VmAgent.Entity.Infos;
import edu.sjsu.VmAgent.dbConfig.DbUtility;
import edu.sjsu.VmAgent.dbConfig.DbUtilityKpi;

@RestController

public class AgentController{
	
	private static DbUtility di = new DbUtility();
	private static DbUtilityKpi diKpi = new DbUtilityKpi();
	
	
	@Autowired
	private CreateContainerInfo ci;
	//private CreateInfo cinf;

	
	
	
	
	@Scheduled(fixedDelay=10000L)
	public void post()
	{

		di.insert(ci.createContInfoList());
	
		diKpi.insert(CreateInfo.infos());
		
		

	
		
	 
	}
}

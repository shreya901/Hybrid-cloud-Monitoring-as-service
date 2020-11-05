package edu.sjsu.VmAgent.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.sjsu.VmAgent.Entity.CreateContainerInfo;
import edu.sjsu.VmAgent.Entity.CreateInfo;

@Configuration
public class AppConfig {
	@Bean
	public CreateContainerInfo CiBean() {

		return new CreateContainerInfo();
	}
	@Bean
	public CreateInfo CrInf() {

		
		CreateInfo.SigarService();
		 return null;
	}
	
	
}

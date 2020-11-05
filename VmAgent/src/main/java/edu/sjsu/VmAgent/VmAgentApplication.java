package edu.sjsu.VmAgent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
@SpringBootApplication


public class VmAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmAgentApplication.class, args);
	

	}


}
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled" ,  matchIfMissing= true)
class SchedulingConfiguration{
	
}



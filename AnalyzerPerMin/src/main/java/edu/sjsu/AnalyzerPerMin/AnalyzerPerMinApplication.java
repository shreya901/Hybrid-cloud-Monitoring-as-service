package edu.sjsu.AnalyzerPerMin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import edu.sjsu.AnalyzerPerMin.Config.DbConnections;
import edu.sjsu.AnalyzerPerMin.Config.FluxQueryCpu;
import edu.sjsu.AnalyzerPerMin.Config.FluxQueryMemory;
import edu.sjsu.AnalyzerPerMin.Controller.ShutDownController;


@SpringBootApplication
public class AnalyzerPerMinApplication {

	public static void main(String[] args) {
		DbConnections.DbUtility();
		FluxQueryCpu.setRange();
		FluxQueryMemory.setRange();
		
		
		ConfigurableApplicationContext ctx =SpringApplication.run(AnalyzerPerMinApplication.class, args);
		ShutDownController s= new ShutDownController();
		s.setApplicationContext(ctx);
		
		
		
	}

}
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled" ,  matchIfMissing= true)
class SchedulingConfiguration{
	
}
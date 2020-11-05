package edu.sjsu.VmAnalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import edu.sjsu.VmAnalyzer.Config.DbConnections;
import edu.sjsu.VmAnalyzer.Config.FluxQueryCpu;
import edu.sjsu.VmAnalyzer.Config.FluxQueryMemory;
import edu.sjsu.VmAnalyzer.Controller.ShutDownController;

@SpringBootApplication
public class VmAnalyzerApplication {

	public static void main(String[] args) {
		DbConnections.DbUtility();

		FluxQueryMemory.setRange();
		FluxQueryCpu.setRange();
		ConfigurableApplicationContext ctx =SpringApplication.run(VmAnalyzerApplication.class, args);
		ShutDownController s= new ShutDownController();
		s.setApplicationContext(ctx);

	}

}
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled" ,  matchIfMissing= true)
class SchedulingConfiguration{
	
}
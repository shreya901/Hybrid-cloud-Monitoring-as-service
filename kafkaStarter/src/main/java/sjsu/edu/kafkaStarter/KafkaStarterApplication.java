package sjsu.edu.kafkaStarter;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

//@ComponentScan(basePackageClasses = sjsu.edu.kafkaStarter.controller.demo.class)
public class KafkaStarterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx =SpringApplication.run(KafkaStarterApplication.class, args);
//		int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator() {
//            @Override
//            public int getExitCode() {
//                // no errors
//                return 0;
//            }
//        });
		//int exitCode = SpringApplication.exit(ctx, () -> 0);


	}
	

}
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled" ,  matchIfMissing= true)
class SchedulingConfiguration{
	
}

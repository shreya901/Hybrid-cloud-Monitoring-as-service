package sjsu.edu.kafkaStarter.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetPidKpi {
	private static final String command="./kpi.sh";
	private static ProcessBuilder processBuilder = new ProcessBuilder(); 
	
	
	
	public static float[] getPid()  {
		float val []=new float[2];

		 processBuilder.command("bash","-c",command);
		 try {
			 	

				Process process = processBuilder.start();

				//StringBuilder output = new StringBuilder();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));

				
			
				String line;

				for (int i=0;i<2;i++){
					line = reader.readLine();
					if (line!=null){
						val[i]=Float.parseFloat(line.toString());
						
					}
					
					
				}
			
				int exitVal = process.waitFor();
				if (exitVal == 0) {
					log.debug("Success");
					

				
					reader.close();
					process.destroy();
					
					return val;
					
					
				} else {
					log.error("Failure");
					log.error(String.valueOf(exitVal));
					
					reader.close();
					process.destroy();
					
				}
				


			}catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		return val;
		 
		

	 }
}
		
		
		




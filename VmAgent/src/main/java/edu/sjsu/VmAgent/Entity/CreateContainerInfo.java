package edu.sjsu.VmAgent.Entity;
import edu.sjsu.VmAgent.Entity.ContainerInfo;
import edu.sjsu.VmAgent.Entity.CreateInfo;
import edu.sjsu.VmAgent.dbConfig.DbUtility;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
public class CreateContainerInfo {
private static ProcessBuilder processBuilder = new ProcessBuilder();
private static final String command1="docker ps  -a | grep \"k8s_producer-high\" |  wc -l";
private static final String commandType1="High Load";
private static final  String command2="docker ps  -a | grep \"k8s_producer-med\" |  wc -l";
private static final  String commandType2="Medium Load";
private static final String command3="docker ps  -a | grep \"k8s_producer-low\" |  wc -l";
private static final String commandType3="Low Load";

public static List <ContainerInfo> ContainerInfoList = new ArrayList<>(3);



public List<ContainerInfo> createContInfoList() {
	 
	if( ContainerInfoList.size() < 3) {
		ContainerInfoList.add(createContInfo(command1,commandType1));
		ContainerInfoList.add(createContInfo(command2,commandType2));
		ContainerInfoList.add(createContInfo(command3,commandType3));
	}else {
		ContainerInfoList.set(0,createContInfo(command1,commandType1));
		ContainerInfoList.set(1,createContInfo(command2,commandType2));
		ContainerInfoList.set(2,createContInfo(command3,commandType3));
		
	}
	return ContainerInfoList;

	

	
}
public  ContainerInfo createContInfo(String command,String commandType) {
	

	ContainerInfo ci = new ContainerInfo();
	 
	 processBuilder.command("bash","-c",command);
	 try {
		 	

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
		
			int exitVal = process.waitFor();
			if (exitVal == 0) {
				log.debug("Success");

				ci.setContainerCount(Integer.parseInt(output.toString().trim()));
				Date now = new Date();
				ci.setUTC(now.getTime());
				ci.setContainerType(commandType);
				ci.setFqdn(CreateInfo.FQDN);
				ci.setHostname(CreateInfo.HostName);
				reader.close();
				process.destroy();
				
				return ci;
				
				
			} else {
				log.error("Failure");
				log.error(String.valueOf(exitVal));
				
				reader.close();
				process.destroy();
				return null;
				
			}
			


		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 
	return null;

 } 
	
	
	
}


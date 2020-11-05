package edu.sjsu.ServerController.service;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.JSchException;


import edu.sjsu.ServerController.ssh_connect.SshKubernetsMaster;

import java.io.IOException;
import java.util.List;
@Service
public class KubernetesGetallNodesService {
	private static SshKubernetsMaster sKubMas= new SshKubernetsMaster();
	public String getallnodes() throws JSchException, InterruptedException, IOException {
		return sKubMas.GetAllNodes();

        
    }
	public String getallpods() throws JSchException, InterruptedException, IOException {
		
		return sKubMas.GetAllPods();
	}
	public String getallrecentpods() throws JSchException, InterruptedException, IOException {
		
		return sKubMas.getPodsRecent();
	}
	public String getallrecentfailedpods() throws JSchException, InterruptedException, IOException {
		
		return sKubMas.getPodsFailedRecent();
	}

}

package edu.sjsu.ServerController.ssh_connect;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.*;
public class SshKubernetsMaster {
	private static final String  MASTER_IP="3.90.111.39";
	
	public String GetAllNodes() throws JSchException, InterruptedException, IOException {
	
	JSch jsch=new JSch();
	jsch.addIdentity("/Users/shreya90/Downloads/amd-cluster.pem");
	java.util.Properties config = new java.util.Properties(); 
    config.put("StrictHostKeyChecking", "no");
   
    
//	JSch.setConfig("StrictHostKeyChecking", "no");

	Session session=jsch.getSession("ubuntu", MASTER_IP, 22);
	session.setConfig(config);
	session.connect();

	
	String command = "kubectl get no -o go-template='{{range .items}}{{.metadata.name}}{{\"\\n\"}}{{end}}'";
	Channel channel = session.openChannel("exec");
	InputStream input = channel.getInputStream();
	

	((ChannelExec)channel).setCommand(command);
	((ChannelExec)channel).setErrStream(System.err);
	channel.connect();

	
	
	byte[] tmp = new byte[1024];
	StringBuilder strBuilder = new StringBuilder();
	while (true) {
	    while (input.available() > 0) {
	        int i = input.read(tmp, 0, 1024);
	        if (i < 0) break;
	        
	        strBuilder.append(new String(tmp, 0, i));
	    }
	    if (channel.isClosed()){
	    	System.out.println("exit-status: " + channel.getExitStatus());
	        break;
	    }
	    Thread.sleep(1000);
	}

	channel.disconnect();
	session.disconnect();
	return strBuilder.toString(); 

 }
	
	
	public String GetAllPods() throws JSchException, InterruptedException, IOException {
		
		JSch jsch=new JSch();
		jsch.addIdentity("/Users/shreya90/Downloads/amd-cluster.pem");
		java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");
	   
	    
//		JSch.setConfig("StrictHostKeyChecking", "no");

		Session session=jsch.getSession("ubuntu", MASTER_IP, 22);
		session.setConfig(config);
		session.connect();

		
		String command = " kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}} {{.spec.nodeName}} {{.metadata.creationTimestamp}}{{\"\\t\"}}{{.status.phase}}{{\"\\n\"}}{{end}}'| awk '{if ($4 ~ /Running/) {print $1 \"\\t\" $2}}'";
		Channel channel = session.openChannel("exec");
		InputStream input = channel.getInputStream();
		

		((ChannelExec)channel).setCommand(command);
		((ChannelExec)channel).setErrStream(System.err);
		channel.connect();

		
		
		byte[] tmp = new byte[1024];
		StringBuilder strBuilder = new StringBuilder();
		while (true) {
		    while (input.available() > 0) {
		        int i = input.read(tmp, 0, 1024);
		        if (i < 0) break;
		        
		        strBuilder.append(new String(tmp, 0, i));
		    }
		    if (channel.isClosed()){
		    	System.out.println("exit-status: " + channel.getExitStatus());
		        break;
		    }
		    Thread.sleep(1000);
		}

		channel.disconnect();
		session.disconnect();
	
		return strBuilder.toString(); 

	 }
	
	public String getPodsRecent() throws JSchException, InterruptedException, IOException {
		
		JSch jsch=new JSch();
		jsch.addIdentity("/Users/shreya90/Downloads/amd-cluster.pem");
		java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");
	   
	    
//		JSch.setConfig("StrictHostKeyChecking", "no");

		Session session=jsch.getSession("ubuntu", MASTER_IP, 22);
		session.setConfig(config);
		session.connect();

		
		String command = "kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}} {{.spec.nodeName}} {{.metadata.creationTimestamp}}{{\"\\t\"}}{{.status.phase}}{{\"\\n\"}}{{end}}' | awk '$3 >= \"'$(date -d'now-12 hour' -Ins --utc | sed 's/+0000/Z/')'\" { if ($4 ~ /Running/) print $1 \"\\t\"    $2 }'";
		Channel channel = session.openChannel("exec");
		InputStream input = channel.getInputStream();
		

		((ChannelExec)channel).setCommand(command);
		((ChannelExec)channel).setErrStream(System.err);
		channel.connect();

		
		
		byte[] tmp = new byte[1024];
		StringBuilder strBuilder = new StringBuilder();
		while (true) {
		    while (input.available() > 0) {
		        int i = input.read(tmp, 0, 1024);
		        if (i < 0) break;
		        
		        strBuilder.append(new String(tmp, 0, i));
		    }
		    if (channel.isClosed()){
		    	System.out.println("exit-status: " + channel.getExitStatus());
		        break;
		    }
		    Thread.sleep(1000);
		}

		channel.disconnect();
		session.disconnect();
	
		return strBuilder.toString(); 

	 }


	public String getPodsFailedRecent() throws JSchException, IOException, InterruptedException {
		JSch jsch=new JSch();
		jsch.addIdentity("/Users/shreya90/Downloads/amd-cluster.pem");
		java.util.Properties config = new java.util.Properties(); 
	    config.put("StrictHostKeyChecking", "no");
	   
	    
//		JSch.setConfig("StrictHostKeyChecking", "no");

		Session session=jsch.getSession("ubuntu", MASTER_IP, 22);
		session.setConfig(config);
		session.connect();

		
		String command = "kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}} {{.spec.nodeName}} {{.metadata.creationTimestamp}}{{\"\\t\"}}{{.status.phase}}{{\"\\n\"}}{{end}}' | awk '$3 >= \"'$(date -d'now-15 hour' -Ins --utc | sed 's/+0000/Z/')'\" { if ($4 ~ /Failed/) print $1 \"\\t\"    $2 }'";
		//String command="kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{\"\\t\"}} {{.spec.nodeName}}{{\"\\t\"}} {{.metadata.creationTimestamp}}{{\"\\t\"}}{{.status.phase}}{{\"\\n\"}}{{end}}'| awk '{if ($4 ~ !/Running/) {print $1 \"\\t\" $2}}'";
		Channel channel = session.openChannel("exec");
		InputStream input = channel.getInputStream();
		

		((ChannelExec)channel).setCommand(command);
		((ChannelExec)channel).setErrStream(System.err);
		channel.connect();

		
		
		byte[] tmp = new byte[1024];
		StringBuilder strBuilder = new StringBuilder();
		while (true) {
		    while (input.available() > 0) {
		        int i = input.read(tmp, 0, 1024);
		        if (i < 0) break;
		        
		        strBuilder.append(new String(tmp, 0, i));
		    }
		    if (channel.isClosed()){
		    	System.out.println("exit-status: " + channel.getExitStatus());
		        break;
		    }
		    Thread.sleep(1000);
		}

		channel.disconnect();
		session.disconnect();
	
		return strBuilder.toString(); 
	}
	
} 

package edu.sjsu.VmAgent.Entity;

import java.util.Date;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import com.google.common.base.Throwables;
import kamon.sigar.SigarProvisioner;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class CreateInfo {
	private static Sigar sigar ;
	static String FQDN = null;
	static String HostName = null;
	

    public static void SigarService() {
        Sigar sigar = null;
        try {
            SigarProvisioner.provision();
            sigar = new Sigar();
            CreateInfo.FQDN = sigar.getFQDN();
       
            CreateInfo.HostName = sigar.getNetInfo().getHostName();
       
         // call it to make sure the library was loaded
          
       
            log.debug("sigar loaded successfully");
         
        } catch (Throwable t) {
            log.error("failed to load sigar", t);
            throw Throwables.propagate(t);
        }
        CreateInfo.sigar = sigar;
		
    }

    public static Infos infos() {

        Infos infos = new Infos();
        try {
        	
            infos.setPid(sigar.getPid());
            infos.setFqdn(sigar.getFQDN());

            infos.setHostname(sigar.getNetInfo().getHostName());

            Date now = new Date();
            infos.setUTC(now.getTime());
           
            

            // Add cpu
            infos.getCpu().setSys(sigar.getThreadCpu().getSys());
            infos.getCpu().setTotal(sigar.getThreadCpu().getTotal());
            infos.getCpu().setUser(sigar.getThreadCpu().getUser());

            // Add mem
            infos.getMem().setTotal(sigar.getMem().getTotal());
            infos.getMem().setUsed(sigar.getMem().getUsed());
            infos.getMem().setFree(sigar.getMem().getFree());            
           
            //Add Cpu percentage
           CpuPerc cp=sigar.getCpuPerc();
           double combined; 
           double total; 
           double idle;  
           double percentUsed; 
         
       
           combined = cp.getCombined(); 
           idle = cp.getIdle(); 
           total = idle + combined; 
           percentUsed = ((double)combined/total)*100;  
           infos.setCpuPercentage(percentUsed);
      

           infos.setMemoryPercentage(sigar.getMem().getUsedPercent());
          

        } catch (SigarException e) {
            throw Throwables.propagate(e);
        }
        
        return infos;
        
    }
}


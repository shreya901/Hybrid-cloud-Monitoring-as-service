package sjsu.edu.kafkaStarter.model;

import kamon.sigar.SigarProvisioner;
import lombok.extern.slf4j.Slf4j;
import sjsu.edu.kafkaStarter.controller.GetPidKpi;
import sjsu.edu.kafkaStarter.model.Infos;
import org.hyperic.sigar.ptql.ProcessFinder;
import org.hyperic.sigar.Humidor;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.Uptime;

import java.time.Instant;
import java.util.Date;

import javax.management.MBeanServer;

import org.hyperic.sigar.CpuPerc;
import java.lang.management.*;


import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import com.google.common.base.Throwables;

@Slf4j
public class Create_Info {


	private static Sigar sigar ;
	private static float val[];

	

    public static void SigarService() {
        Sigar sigar = null;
        try {
            SigarProvisioner.provision();
            sigar = new Sigar();
            

       
         // call it to make sure the library was loaded
          
         
            log.debug("sigar loaded successfully");
         
         
        } catch (Throwable t) {
            log.error("failed to load sigar", t);
            throw Throwables.propagate(t);
        }
        Create_Info.sigar = sigar;
     
    }

    public static Infos infos() {

        Infos infos = new Infos();
       
        try {

        	
        	infos.setPid(sigar.getPid());
            infos.setFqdn(sigar.getFQDN());
            infos.setHostname(sigar.getNetInfo().getHostName());

            Date now = new Date();
            infos.setUTC(now.getTime());
            infos.setTopicName("HighLoad");
            


            // Add cpu
            infos.getCpu().setSys(sigar.getThreadCpu().getSys());
            infos.getCpu().setTotal(sigar.getThreadCpu().getTotal());
            infos.getCpu().setUser(sigar.getThreadCpu().getUser());

            // Add mem
            infos.getMem().setTotal(sigar.getMem().getTotal());
            infos.getMem().setUsed(sigar.getMem().getUsed());
            infos.getMem().setFree(sigar.getMem().getFree());            
           
            //Add Cpu percentage
//           CpuPerc cp=sigar.getCpuPerc();
//           double combined; 
//           double total; 
//           double idle;  
//           double percentUsed; 
//         
//       
//           combined = cp.getCombined(); 
//           idle = cp.getIdle(); 
//           total = idle + combined; 
//           percentUsed = ((double)combined/total)*100; 
            //sigar.getMem().getUsedPercent()
           val=GetPidKpi.getPid();
           infos.setCpuPercentage(val[0]);

           infos.setMemoryPercentage(val[1]);
          

        } catch (SigarException e) {
            throw Throwables.propagate(e);
        }

        return infos;
        
    }
    
    public static FaultyContainerInfo faultyInfos()
    {
    	FaultyContainerInfo fc = new FaultyContainerInfo();
    	try {
			fc.setFqdn(sigar.getFQDN());
		} catch (SigarException e) {
			
			e.printStackTrace();
		}
    	Date now = new Date();
    	fc.setUTC(now.getTime());
		return fc;
    }
}
package sjsu.edu.kafkaStarter.model;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Humidor;
import org.hyperic.sigar.SigarProxy;

public class CpuReader
{
    static Humidor h;
    public CpuReader()
    {
        h = Humidor.getInstance();
    }
    public double cpuUtilization() throws SigarException 
    {
        //Returns CPU utilization as truncated two-decimal percent  
        SigarProxy sp = h.getSigar(); 
        CpuPerc cp = sp.getCpuPerc(); 
        double combined; 
        double total; 
        double idle;  
        double percentUsed; 
        int truncate = 0;  
        //get CPU times
        combined = cp.getCombined(); 
        idle = cp.getIdle(); 
        total = idle + combined; 
        //determine percent and truncate
        percentUsed = ((double)combined/total)*100;  
        truncate = (int)(percentUsed*100.0);
        percentUsed = (double)truncate/100; 
        return(percentUsed);
    }
}
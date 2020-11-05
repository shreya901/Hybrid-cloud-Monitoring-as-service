package sjsu.edu.kafkaStarter.model;
import java.util.ArrayList;
import java.util.List;


import lombok.Data;
@Data

public class Infos {
	 	private long pid;
	    private String fqdn;
	    private String hostname;
	    private double UTC;
	    private double cpuPercentage;
	    private double memoryPercentage;
	    private String topicName;
	    private Cpu cpu = new Cpu();
	    private Mem mem = new Mem();
	    
	    
	    private double testcpu;
	    private double testmem;

//	    private List<Interface> interfaces = new ArrayList<>(2);

	    @Data
	    public static class Cpu {
	        private long sys;
	        private long total;
	        private long user;
	    }

	    @Data
	    public static class Mem {
	        private long total;
	        private long used;
	        private long free;
	    }


//	    @Data
//	    public static class Interface {
//	        private String name;
//	        private String type;
//	        private String address;
//	    }
	    
}

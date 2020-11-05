package edu.sjsu.AnalyzerPerMin.model;
import java.time.Instant;

import com.influxdb.annotations.*;

import lombok.Data;
@Measurement(name = "CpuPerMinTopic")
@Data
public class CpuAvgMinTopic {
	
	
    

        @Column(name = "LoadType")
        private String loadType;

        @Column (name = "AvgCpuUsagePerMin")
        private double avgCpuUsagePerMin;

        @Column(name ="UTC")
        private Instant utc_time;
    

}

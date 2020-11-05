#!/bin/bash
  

arr=$(ps ax | grep -v "V" |grep -v "X"| grep -v "T" | grep -v "grep" | awk  '{print $1}')
cpu=0
memory=0
#echo $arr
         
          
for pid in $arr;do
        
                
                c=$(top -b -n 2 -d 0.2 -p $pid | tail -1 | awk '{print $9}')
                m=$(top -b -n 2 -d 0.2 -p $pid | tail -1 | awk '{print $10}')
                if [ $c != "%CPU" ]; then
                 
                    cpu=$(echo "$cpu+$c" | bc |  awk '{ printf("%.4f\n",$1) '})
                fi 
                
                if [ $m != "%MEM" ]; then
                
                    memory=$(echo "$memory+$m" | bc |  awk '{ printf("%.4f\n",$1) '})   
                fi
      
done
echo $cpu
echo $memory 
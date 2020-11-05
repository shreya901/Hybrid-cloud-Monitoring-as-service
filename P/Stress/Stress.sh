#!/bin/bash

while true
do

    #cpu=$(shuf -i 50-80 -n 1)
    memory=$(shuf -i 400-800 -n 1)M
    time=$(shuf -i 3-5 -n 1)m
    sleeptime=$(shuf -i 5-15 -n 1)m
    
    sleep $sleeptime
    stress-ng -m 1 --vm-bytes $memory --timeout $time  
    
    
    


done
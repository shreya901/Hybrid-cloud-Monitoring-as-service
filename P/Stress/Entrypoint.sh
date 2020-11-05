#!/bin/bash
apt-get update
apt-get -y upgrade
apt-get install stress-ng
apt-get install bc

java -jar /producer.jar &
/bin/bash   /Stress/Stress.sh
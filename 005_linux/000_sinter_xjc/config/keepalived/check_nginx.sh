#!/bin/bash
if [ "$(ps -ef | grep "nginx: master process"| grep -v grep )" == "" ]
then
 /opt/ng/nginx/sbin/nginx
 sleep 5
 if [ "$(ps -ef | grep "nginx: master process"| grep -v grep )" == "" ]
 then
 killall keepalived
 fi
fi

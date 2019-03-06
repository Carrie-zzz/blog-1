echo $PATH

#zookeeper
/opt/zookeeper-3.4.6/bin/zkServer.sh start

sleep 10

#kafka
/opt/kafka_2.10-0.10.2.0/bin/kafka-server-start.sh -daemon /opt/kafka_2.10-0.10.2.0/config/server.properties

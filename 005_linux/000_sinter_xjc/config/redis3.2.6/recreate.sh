pkill redis-server
sleep 5
rm -rf /data/redis/db/*
rm -rf /data/redis/log/*
/opt/redis3.2.6/bin/redis-server /opt/redis3.2.6/conf/redis-6379.conf
/opt/redis3.2.6/bin/redis-server /opt/redis3.2.6/conf/redis-6389.conf
/opt/redis3.2.6/bin/redis-server /opt/redis3.2.6/conf/redis-6399.conf

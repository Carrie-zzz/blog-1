# /bin/base
logs_path="/opt/ng/log/"

log_name="error.log"
pid_path="/opt/ng/nginx.pid"
mv ${logs_path}${log_name} ${logs_path}${log_name}_$(date --date="LAST   WEEK" +"%Y-%m-%d").log
kill -USR1 `cat ${pid_path}`

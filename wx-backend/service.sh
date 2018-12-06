#!/bin/bash

case "$1" in

    start)
        nohup java -jar target/wx-backend-1.0.0.jar -Dfile.encoding=utf-8 nohup.log 2>&1 &
        echo $! > pid.pid
        echo "===== service start ====="
        ;;


    stop)
        echo "===== service stop ====="
	PID=$(cat pid.pid)
        kill -9 $PID
	echo "service stoped"
        ;;


    restart)
        $0 stop
        sleep 2
        echo "===== service restart ====="
        $0 start
	;;
    
    update)
	$0 stop
	sleep 2
	echo "===== mvn clean ====="
	mvn clean
	echo "===== git pull ====="
	git pull
	echo "===== mvn package ====="
	mvn package
	$0 start
	;;    

    esac
    exit 0


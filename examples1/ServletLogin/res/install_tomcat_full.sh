#!/bin/bash
# For More Info http://clivern.com/how-to-install-apache-tomcat-8-on-ubuntu-16-04

sudo apt-get update
sudo apt-get install default-jdk
sudo apt-get install unzip
cd /opt
curl -O http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.15/bin/apache-tomcat-8.5.15.zip
sudo unzip apache-tomcat-8.5.15.zip
sudo mv apache-tomcat-8.5.15 tomcat

sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat

sudo chgrp -R tomcat /opt/tomcat
sudo chown -R tomcat /opt/tomcat
sudo chmod -R 755 /opt/tomcat

sudo echo "[Unit]
Description=Apache Tomcat Web Server
After=network.target
[Service]
Type=forking
Environment=JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre
Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid
Environment=CATALINA_HOME=/opt/tomcat
Environment=CATALINA_BASE=/opt/tomcat
Environment='CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'
Environment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'
ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh
User=tomcat
Group=tomcat
UMask=0007
RestartSec=15
Restart=always
[Install]
WantedBy=multi-user.target" > /etc/systemd/system/tomcat.service

sudo ufw allow 8080
sudo systemctl daemon-reload
sudo systemctl start tomcat
sudo systemctl status tomcat

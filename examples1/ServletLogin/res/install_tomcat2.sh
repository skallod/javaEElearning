echo '
[Unit]
Description=Apache Tomcat Web Application Container
After=network.target

[Service]
Type=forking
'>/etc/systemd/system/tomcat.service

JAVA_HOME=`update-alternatives --query java | grep 'Value: ' $TEMP | grep -o '/.*/jre' | xargs dirname`
echo JAVA_HOME=$JAVA_HOME
echo Environment=JAVA_HOME=$JAVA_HOME>>/etc/systemd/system/tomcat.service

echo '
Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid
Environment=CATALINA_HOME=/opt/tomcat
Environment=CATALINA_BASE=/opt/tomcat
Environment='CATALINA_OPTS=-Xms256M -Xmx512M -server -XX:+UseParallelGC'
Environment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'
ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh
User=tomcat
Group=tomcat
UMask=0007
RestartSec=15
Restart=always
[Install]
WantedBy=multi-user.target
'>>/etc/systemd/system/tomcat.service

ufw allow 8080
systemctl daemon-reload
systemctl start tomcat
systemctl status tomcat

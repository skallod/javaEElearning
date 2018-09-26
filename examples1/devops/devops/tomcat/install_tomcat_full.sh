sudo apt-get update
sudo apt-get install default-jdk
sudo apt-get install unzip
cd /tmp
#TODO is version of tomcat actual ?
curl -O http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.31/bin/apache-tomcat-8.5.31.tar.gz
sudo mkdir /opt/tomcat
sudo tar xzvf apache-tomcat-8*tar.gz -C /opt/tomcat --strip-components=1
cd /opt/tomcat
sudo chgrp -R tomcat /opt/tomcat
sudo chmod -R g+r conf
sudo chmod g+x conf
sudo chown -R tomcat webapps/ work/ temp/ logs/ conf/
#alternative commands
#sudo chown -R tomcat /opt/tomcat
#sudo chmod -R 755 /opt/tomcat

# create service

echo '
[Unit]
Description=Apache Tomcat Web Application Container
After=network.target

[Service]
Type=forking
'>/etc/systemd/system/tomcat.service

##can set manual Environment=JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre
JAVA_HOME=`update-alternatives --query java | grep 'Value: ' $TEMP | grep -o '/.*/jre' | xargs dirname`
echo JAVA_HOME=$JAVA_HOME
echo Environment=JAVA_HOME=$JAVA_HOME>>/etc/systemd/system/tomcat.service

echo '
Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid
Environment=CATALINA_HOME=/opt/tomcat
Environment=CATALINA_BASE=/opt/tomcat
Environment="CATALINA_OPTS=-Xms256M -Xmx350M -server -XX:+UseG1GC"
Environment="JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom"
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

##ufw article https://wiki.ubuntu.com/UncomplicatedFirewall
ufw deny 8080 #connect should be via nginx
##ufw allow 8080
systemctl daemon-reload
systemctl start tomcat
systemctl status tomcat

# remove default apps

rm -r ROOT
rm -r docs
rm -r examples
rm -r host-manager
rm -r manager


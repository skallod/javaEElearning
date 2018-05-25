sudo apt-get update
sudo apt-get install default-jdk
sudo apt-get install unzip
cd /tmp
curl -O http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.31/bin/apache-tomcat-8.5.31.tar.gz
sudo mkdir /opt/tomcat
sudo tar xzvf apache-tomcat-8*tar.gz -C /opt/tomcat --strip-components=1
cd /opt/tomcat
sudo chgrp -R tomcat /opt/tomcat
sudo chmod -R g+r conf
sudo chmod g+x conf
sudo chown -R tomcat webapps/ work/ temp/ logs/
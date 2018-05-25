cd /opt
sudo wget https://services.gradle.org/distributions/gradle-4.0-bin.zip
sudo unzip gradle-4.0-bin.zip
sudo mv gradle-4.0 gradle
sudo rm gradle-4.0-bin.zip
export PATH=$PATH:/opt/gradle/bin
gradle -v

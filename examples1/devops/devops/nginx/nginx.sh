##udemy 3-18 6-32
apt-get install nginx
##/etc/nginx
##./sites_available may have simbolic link to ./sites_enabled
##default user www-data
##service nginx restart
##nginx -t : check config file
##service nginx reload : reload config file
##ps -ef --forest | grep nginx
cd /var/www
sudo mkdir main
cp /tmp/devops/nginx/main /etc/nginx/sites-available
rm /etc/nginx/sites-enabled/default
ln -s /etc/nginx/sites-available/main /etc/nginx/sites-enabled/main
##sudo nginx -t -check nginx config
service nginx reload

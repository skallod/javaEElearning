## command line args

-Dspring.profiles.active=devpg

## postgres

create user r2user password 'r2pass';
create schema BOOKSTORE AUTHORIZATION r2user;
#grant select on BOOKSTORE.book_id_seq to r2user;

## h2-console

java -jar h2-1.4.196.jar

## h2

create user r2user password 'r2pass' admin;
create schema BOOKSTORE AUTHORIZATION r2user;
#set schema BOOKSTORE;
#create role STORE_RW_ROLE;
#grant STORE_RW_ROLE to storeuser;

## nginx

./config/dev/nginx.conf

## angular

./../admin-site

## git interesting

https://github.com/3PillarGlobal/engineering-playbook
https://github.com/Apress/spring-boot-2-recipes

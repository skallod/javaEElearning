## command line args

-Dspring.profiles.active=devpg

## postgres

create user r2user password 'r2pass';
create schema SPRING_STORE AUTHORIZATION r2user;
grant select on spring_store.book_id_seq to r2user;

## h2-console

java -jar h2-1.4.196.jar

## h2

--create user MY_READWRITEUSER password 'MY_READWRITEUSER';
create user storeuser password 'storepass' admin;
create schema SPRING_STORE AUTHORIZATION storeuser;
set schema SPRING_STORE;
--create role MY_READ_ROLE;
create role STORE_RW_ROLE;
grant STORE_RW_ROLE to storeuser;
--grant MY_RW_ROLE to MY_READWRITEUSER;

## git interesting

https://github.com/3PillarGlobal/engineering-playbook
https://github.com/Apress/spring-boot-2-recipes

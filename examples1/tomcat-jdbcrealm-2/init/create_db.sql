#Ref: https://tomcat.apache.org/tomcat-3.3-doc/JDBCRealm-howto.html
CREATE DATABASE dbrealm encoding 'utf8';
\c dbrealm;

create table users(  user_name varchar(15) not null primary key,  user_pass varchar(15) not null);
create table roles(  role_name varchar(15) not null primary key);
create table user_roles(  user_name varchar(15) not null references users
,  role_name varchar(15) not null references roles,  primary key( user_name, role_name ));

#Populate tables with user data
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO users (user_name,user_pass) VALUES ('tomcat','password');
INSERT INTO user_roles(user_name,role_name)VALUES('tomcat','admin');
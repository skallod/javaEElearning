--Ref: https://tomcat.apache.org/tomcat-3.3-doc/JDBCRealm-howto.html
--CREATE DATABASE dbservice encoding 'utf8';
--\c dbservice;
create table accounts(
    account_uid text not null primary key,
    account_name text not null,
    account_email text unique not null,
    account_pass bytea not null
    );
create table roles(
    role_id smallint not null primary key,
    role_name varchar(15) not null
    );
create table user_roles(
    account_uid text not null references accounts
    ,  role_id smallint not null references roles
    ,  primary key( account_uid, role_id ));

--Populate tables with user data
INSERT INTO roles (role_id, role_name) VALUES (1,'ADMIN');
--INSERT INTO accounts (
--    account_uid,
--    account_name,
--    account_email,
--    account_pass)
--    VALUES ("3214ws-65756fg",'tomcat',E'\\xDEADBEEF');
--INSERT INTO user_roles(user_name,role_name)VALUES('tomcat','admin');
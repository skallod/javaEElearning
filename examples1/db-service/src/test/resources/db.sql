--Ref: https://tomcat.apache.org/tomcat-3.3-doc/JDBCRealm-howto.html
--CREATE DATABASE dbservice encoding 'utf8';
--\c dbservice;
create table accounts(
    account_uid varchar not null primary key,
    account_name varchar not null,
    account_email varchar unique not null,
    account_pass bytea not null
    );
create table roles(
    role_id smallint not null primary key,
    role_name varchar(15) not null
    );
create table user_roles(
    account_uid varchar not null references accounts
    ,  role_id smallint not null references roles
    ,  primary key( account_uid, role_id ));

--Populate tables with user data
INSERT INTO roles (role_id, role_name) VALUES (1,'ADMIN');
INSERT INTO roles (role_id, role_name) VALUES (1,'USER');

--insert into accounts(account_uid,account_name,account_email,account_pass) values('6508d24e-e1bc-431b-b996-b12b251fe74c', 'tt','tt','\xbf323e6dc49b2917e85077b29490a6ccb340a479e1b06c943b98e6acedb55a01');
--INSERT INTO accounts (
--    account_uid,
--    account_name,
--    account_email,
--    account_pass)
--    VALUES ("3214ws-65756fg",'tomcat',E'\\xDEADBEEF');
--INSERT INTO user_roles(user_name,role_name)VALUES('tomcat','admin');
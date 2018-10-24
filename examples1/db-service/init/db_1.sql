CREATE DATABASE dbservice encoding 'utf8';
\c dbservice;
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
create table version(
    version_id smallint not null primary key
    );
INSERT INTO version (version_id) VALUES (1);
--insert into accounts(account_uid,account_name,account_email,account_pass) values('6508d24e-e1bc-431b-b996-b12b251fe74c', 'tt','tt','\xbf323e6dc49b2917e85077b29490a6ccb340a479e1b06c943b98e6acedb55a01');

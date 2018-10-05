create table if not exists accounts(
    account_uid varchar not null primary key,
    account_name varchar not null,
    account_email varchar unique not null,
    account_pass bytea not null
    );
--create table if not exists roles(
--    role_id smallint not null primary key,
--    role_name varchar(15) not null
--    );
create table if not exists user_roles(
    account_uid varchar not null references accounts
    ,  role_id smallint not null --references roles
    ,  primary key( account_uid, role_id ));

--INSERT INTO roles (role_id, role_name) VALUES (1,'ADMIN');
--INSERT INTO roles (role_id, role_name) VALUES (2,'USER');

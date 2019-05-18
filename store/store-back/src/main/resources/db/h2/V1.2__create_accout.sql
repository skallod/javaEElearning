create table if not exists bookstore.user (id int8 not null, email varchar(255), first_name varchar(255)
    , last_name varchar(255), password varchar(255), role int4, user_name varchar(255), primary key (id));
create sequence if not exists user_id_seq start 1 increment 50;

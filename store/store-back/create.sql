create table spring_store.book (id int8 not null, active boolean not null, author varchar(255), category varchar(255), description text, format varchar(255), in_stock_number int4 not null, isbn varchar(255), language varchar(255), list_price float8 not null, number_of_pages int4 not null, our_price float8 not null, publication_date varchar(255), publisher varchar(255), shipping_weight float8 not null, title varchar(255), primary key (id))
create table spring_store.user (id int8 not null, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), role int4, user_name varchar(255), primary key (id))
create sequence book_id_seq start 1 increment 50
create sequence user_id_seq start 1 increment 50

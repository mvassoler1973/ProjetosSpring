create table products(
	id bigint not null auto_increment,
	created datetime,
	updated datetime,
    name varchar(100) not null,
    description varchar(100) not null,
    price numeric(14,2),not null
    constraint products_pk primary key (id)
)
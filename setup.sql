create table accounts (
	id bigserial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null,
	salt varchar(255) not null,
	token varchar(255)
);

create table accounts (
	id bigserial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null
)

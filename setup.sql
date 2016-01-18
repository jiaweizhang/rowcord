create table accounts (
	id bigserial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null,
	timestamp timestamp default current_timestamp
);

create table auth (
	email varchar(255) not null,
	role varchar(255) not null,
	unique (email, role)
);
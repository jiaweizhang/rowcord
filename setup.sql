create table accounts (
	id bigserial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null,
	timestamp timestamp default current_timestamp,
	firstname varchar(255),
	middlename varchar(255),
	lastname varchar(255),
	dob date
);

create table auth (
	email varchar(255) not null,
	role varchar(255) not null,
	unique (email, role)
);


create table accounts (
	id bigserial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null,
	profile int not null,
	verify int not null,
	timestamp timestamp default current_timestamp,
	firstname varchar(255),
	middlename varchar(255),
	lastname varchar(255),
	dob date
);

create table groups (
	email varchar(255) not null,
	groupname varchar(255) not null,
	admin int,
	coach int,
	joindate date not null,
	unique (email, groupname)
);

create table groupapplications (
	email varchar(255) not null,
	groupname varchar(255) not null,
	applydate date not null,
	unique (email, groupname)
);

create table groupdetails (
	groupname varchar(255) primary key not null,
	description varchar(2000) not null,
	createdate date not null
);

create table subgroups (
	email varchar(255) not null,
	groupname varchar(255) not null,
	subgroupname varchar(255) not null,
	auth varchar(255) not null,
	type varchar(255) not null,
	joindate date not null
);
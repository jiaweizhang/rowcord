-- noinspection SqlNoDataSourceInspectionForFile

drop table subgroups;
drop table groupapplications;
drop table groupdetails;
drop table groups;
drop table users;

create table users (
	user_id serial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null,
	timestamp timestamp default current_timestamp
);

INSERT INTO users (email, passhash) VALUES ('admin@admin.com', '1000:b80b084e592ead78a94324c8adf58466b14265b2df4fdd1e:17010407d54439f13d7b4058bef6ee5e8d528c8a41999b43');

/*
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
);*/

drop table groupmembers;
drop table groups;
drop table users;

create table users (
	user_id serial primary key not null,
	email varchar(255) unique not null,
	passhash varchar(255) not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  verified boolean not null,
	joindate timestamp default current_timestamp
);

INSERT INTO users (email, passhash, first_name, last_name, verified)
VALUES ('admin@admin.com', '1000:b80b084e592ead78a94324c8adf58466b14265b2df4fdd1e:17010407d54439f13d7b4058bef6ee5e8d528c8a41999b43', 'Jiawei', 'Zhang', true);

create table groups (
  group_id serial primary key not null,
  group_name varchar(255) unique not null,
  description varchar(2000) not null,
  public_bool boolean not null,
  createdate timestamp default current_timestamp
);

create table groupmembers (
  group_id int not null references groups(group_id) on delete cascade,
  user_id int not null references users(user_id) on delete cascade,
  admin_bool boolean not null,
  coach_bool boolean not null,
  joindate timestamp default current_timestamp,
  unique (group_id, user_id)
);


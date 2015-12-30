create database spring DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci
drop table if exists user;
create table user(
	id int(11) auto_increment,
	name varchar(128) unique not null,
	password varchar(256) not null,
	birthday varchar(256),
	salt varchar(128),
	locked int(2),
	createTime timestamp,
	primary key(id)
)DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci engine=InnoDB;

drop table if exists roles;
create table roles(
	id int(11) auto_increment,
	name varchar(128) unique not null,
	createTime timestamp,
	priority varchar(1),
	primary key(id)
)DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci engine=InnoDB;

drop table if exists rolesuser;
create table rolesuser(
	id int(11) auto_increment,
	userid int(11) ,
	rolesid int(11),
	PRIMARY KEY (id) 
)DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci engine=InnoDB;
drop table if exists voya_ai_trip;
drop table if exists voya_ai_guide;
drop table if exists voya_ai_attraction;
drop table if exists voya_ai_city;
drop table if exists voya_ai_province;
drop table if exists voya_ai_country;
create table voya_ai_country (
 id bigint auto_increment primary key, name varchar(100) not null unique, code varchar(20) unique,
 sort int default 0, status char(1) default '0', del_flag char(1) default '0',
 create_by varchar(64), create_time timestamp default current_timestamp,
 update_by varchar(64), update_time timestamp default current_timestamp, remark varchar(500)
);
create table voya_ai_province (
 id bigint auto_increment primary key, country_id bigint not null, name varchar(100) not null, code varchar(20),
 sort int default 0, status char(1) default '0', del_flag char(1) default '0',
 create_by varchar(64), create_time timestamp default current_timestamp,
 update_by varchar(64), update_time timestamp default current_timestamp, remark varchar(500),
 unique(country_id,name)
);
create table voya_ai_city (
 id bigint auto_increment primary key, province_id bigint not null, name varchar(100) not null,
 cover_image varchar(500), description text, latitude decimal(10,7), longitude decimal(10,7),
 sort int default 0, status char(1) default '0', view_count bigint default 0, del_flag char(1) default '0',
 create_by varchar(64), create_time timestamp default current_timestamp,
 update_by varchar(64), update_time timestamp default current_timestamp, remark varchar(500),
 unique(province_id,name)
);
create table voya_ai_attraction (id bigint auto_increment primary key, city_id bigint, del_flag char(1));
create table voya_ai_guide (id bigint auto_increment primary key, city_id bigint, del_flag char(1));
create table voya_ai_trip (id bigint auto_increment primary key, city_id bigint, del_flag char(1));

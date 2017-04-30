create table api_responses(
request_id varchar(50) primary key,
latitude double not null,
longitude double not null,
city varchar(100) not null,
state varchar(100) not null,
api varchar(100) not null,
json varchar(50000) not null,
request_time datetime not null);

create table api_keys(
api_name varchar(50) primary key,
api_key varchar(100) not null);

create table users(
username varchar(500) not null primary key,
password varbinary(500) not null,
salt varbinary(500) not null,
name varchar(500) not null,
default_city varchar(500),
default_latitude double,
default_longitude double,
default_state varchar(500),
default_country varchar(500),
default_zipcode varchar(500));

create table favorite_locations(
location_id varchar(100) primary key,
username varchar(500) not null,
latitude double not null,
longitude double not null,
city varchar(500) not null,
state varchar(500) not null,
country varchar(500) not null,
zipcode varchar(500) not null);

create table api_subscription(
subscription_id varchar(100) primary key,
username varchar(100) not null,
api_name varchar(200) not null,
api_key varchar(500));

create table geo_lite(
location_id varchar(100) primary key,
country varchar(100) not null,
region varchar(500),
city varchar(500) not null,
postal_code varchar(100),
latitude double not null,
longitude double not null,
metro_code varchar(100),
area_code varchar(100));

ALTER TABLE geo_lite ADD INDEX city (city);

LOAD DATA LOCAL INFILE 'D:/study-material/umb-classes/4-spring-2017/cs683/geo-database/GeoLiteCity_20170404/GeoLiteCity-Location.csv'
INTO TABLE geo_lite
FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;
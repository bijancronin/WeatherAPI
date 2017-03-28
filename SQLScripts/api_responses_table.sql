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
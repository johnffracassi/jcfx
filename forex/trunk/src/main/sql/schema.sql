drop table if exists data_source;
drop table if exists tick;

create table tick (
    object_id int(11) AUTO_INCREMENT NOT NULL primary key,
    data_source varchar(16),
    instrument char(6),
    `date` date,
    `time` time,
    buy double,
    sell double
);

create table instrument (
    instrument char(6) not null primary key,
    `primary` char(3),
    secondary char(3)
);

create table currency (
    currency char(3) not null primary key,
    country varchar(64)
);

create table data_source (
    object_id int(11) auto_increment not null primary key,
    description varchar(64),
    url varchar(400)
);

drop table if exists period_data;
create table period_data(
    object_id int(11) AUTO_INCREMENT NOT NULL primary key,
    data_source varchar(16), 
    instrument varchar(16), 
    date_time timestamp, 
    `date` date,
    `time` time, 
    period varchar(8), 
    open_buy double, 
    open_sell double, 
    high_buy double, 
    high_sell double, 
    low_buy double, 
    low_sell double,
    close_buy double, 
    close_sell double
);
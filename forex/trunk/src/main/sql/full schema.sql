drop table if exists tick_data;
drop table if exists period_data;
drop table if exists job;


// download job table
create table job (
    job_id int(11) auto_increment not null primary key,
    data_source_id varchar(16) not null,
    `date` date not null,
    `time` time not null,
    instrument_id char(6) not null,
    status varchar(16) not null
) engine = myisam;
create index idx_status on job (status, date, time);
create unique index idx_unique_job on job (data_source_id, date, time, instrument_id);


// tick price for an instrument
create table tick_data ( 
    job_id          int(12),
    data_source_id  varchar(10) not null,
    instrument_id 	char(6) not null,
    date_time       timestamp not null,
    `time`          time not null,
    milliseconds    int not null,
    buy             double not null,
    sell            double not null,
    buy_volume      double null,
    sell_volume     double null,
    primary key (data_source_id, instrument_id, date_time, milliseconds)
) engine = myisam;
create index idx_search_time on tick_data(data_source_id, instrument_id, time, date_time);
create index idx_tick_job on tick_data(job_id);


// period (ohlc) data for an instrument
create table period_data ( 
    period_id      	int(11) auto_increment not null primary key,
    job_id          int(11),
    data_source_id 	varchar(16) not null,
    instrument_id	char(6) not null,
    date_time       timestamp not null,
    `time`          time not null,
    period       	varchar(6) not null,
    open_buy     	double not null,
    open_sell    	double null,
    high_buy     	double not null,
    high_sell    	double null,
    low_buy      	double not null,
    low_sell     	double null,
    close_buy    	double not null,
    close_sell   	double null,
    buy_volume      double null,
    sell_volume     double null,
    tick_count      int(11) default 0 not null
) engine = myisam;
create unique index idx_unique_period_date on period_data(data_source_id, instrument_id, date_time, period);
create index idx_unique_period_time on period_data(data_source_id, instrument_id, time, date_time, period);
create index idx_period_job on period_data(job_id);


drop table period_data;
CREATE TABLE period_data ( 
	`id`           	int(11) AUTO_INCREMENT NOT NULL primary key,
	`data_source`  	varchar(25),
	`currency_pair`	char(6) NOT NULL,
    date_time datetime,
	`date`         	date,
	`time`         	time,
	`period`       	int(11),
	`open_buy`     	double,
	`open_sell`    	double,
	`high_buy`     	double,
	`high_sell`    	double,
	`low_buy`      	double,
	`low_sell`     	double,
	`close_buy`    	double,
	`close_sell`   	double 
);

create or replace view v_opening_hr as (
select date_time, open_buy p0,
    (select open_buy from period_data where time(date_time) = time('9:05') and date(date_time) = date(a.date_time)) p10,
    (select open_buy from period_data where time(date_time) = time('9:10') and date(date_time) = date(a.date_time)) p15,
    (select open_buy from period_data where time(date_time) = time('9:15') and date(date_time) = date(a.date_time)) p20,
    (select open_buy from period_data where time(date_time) = time('9:25') and date(date_time) = date(a.date_time)) p30,
    (select open_buy from period_data where time(date_time) = time('9:35') and date(date_time) = date(a.date_time)) p40,
    (select open_buy from period_data where time(date_time) = time('9:55') and date(date_time) = date(a.date_time)) p60
from period_data a
where time(date_time) = time('08:55'));

select date(date_time), p10-p0 d10, p15-p0 d15, p20-p0 d20, p30-p0 d30, p40-p0 d40, p60-p0 d60
from v_opening_hr;

select * from prices;
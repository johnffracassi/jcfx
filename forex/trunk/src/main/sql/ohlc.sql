set @x=0;
select 
    @x := @x+1 idx,
    date, 
    time(concat(hour(time),':',10*(minute(time) div 10))) time, 
    count(*) qty, 
    avg(buy) avg, 
    buy o,
    max(buy) h,
    min(buy) l, 
    (std(buy)*10000) stdev, 
    round((max(buy)-min(buy))*10000) spread
from tick_data a 
where instrument_id = 'EURUSD'
group by hour(time), minute(time) div 10, date
order by idx asc;

// date range for specific instruments data
select min(date_time) start, max(date_time) end, datediff(max(date_time),min(date_time)) days from tick_data where instrument_id = 'GBPUSD';


select distinct time(concat(hour(time),':',10*(minute(time) div 10))) time
from tick_data where instrument_id = 'GBPUSD'
and date_time >= timestamp('2009-03-26 00:00:00')
and date_time < timestamp('2009-03-27 00:00:00')
group by instrument_id, date_time, hour(time);//, minute(time) div 15;

select distinct date from tick_data;
select distinct instrument_id from tick_data;

select instrument_id, date, time(concat(hour(time),':',10*(minute(time) div 10))) time, count(*) qty, avg(buy) avg, max(buy) h, 
    min(buy) l, (std(buy)*10000) stdev, round((max(buy)-min(buy))*10000) spread
from tick_data where instrument_id = 'EURUSD'
and date = date('2009-02-04')
group by instrument_id, date, hour(time);//, minute(time) div 15;

select *, (buy-sell) spread
from tick
where instrument = 'EURUSD'
and date = date('2009-01-15');

select instrument_id, hour(time), avg(buy)
from tick_data
group by instrument_id, hour(time);

select * from period_data
where data_source = 'Dukascopy';

delete from period_data;

delete
from tick_data
where buy < 0.01 or sell < 0.01;

select count(*) from tick_data;
select count(*) from period_data;
select count(*) from job;

update job set status = 'Pending' where instrument_id != 'GBPUSD' and status = 'New';
update job set status = 'New' where instrument_id = 'GBPUSD' and status = 'Pending';

select status, count(*) qty, (count(*)/20) mins
from job group by status with rollup;


select date, min(time)
from tick_data
where dayofweek(date) = 1
group by date;



select 
    time(concat(hour(time),':',5*(minute(time) div 5))) time, 
    count(*) qty, 
    avg(buy) avg, 
    max(buy) h,
    min(buy) l, 
    (std(buy)*10000) stdev, 
    round((max(buy)-min(buy))*10000) spread
from tick_data 
where data_source_id = 'Dukascopy' and instrument_id = 'EURUSD'
group by hour(time), minute(time) div 5;

select time(concat(hour(date_time),':',5*(minute(date_time) div 5))) time, count(*) qty, avg(buy) avg, max(buy) h,
min(buy) l, (std(buy)*10000) stdev, round((max(buy)-min(buy))*10000) spread 
from tick_data 
where data_source_id = 'Dukascopy' 
and instrument_id = 'GBPUSD' 
and date_time >= timestamp('2009-03-26 00:00:00') 
and date_time < timestamp('2009-03-27 00:00:00') 
group by hour(date_time), minute(date_time) div 5;

select *
from period_data
where period = 60;
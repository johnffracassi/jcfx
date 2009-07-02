select date_time, buy, sell
from tick_data a
where data_source_id = 'GAIN' 
and instrument_id = 'GBPUSD'
and a.date_time in (
  select max(date_time) from tick_data where data_source_id = 'GAIN' and instrument_id = 'GBPUSD' 
  and time >= time('21:05') and time < time('23:07') group by date(date_time));

select date_time, buy, sell from (
    select date_time, buy, sell from tick_data where data_source_id = 'GAIN' and instrument_id = 'GBPUSD' 
    and time >= time('21:05') and time < time('23:07') 
    order by date_time desc) a
group by date(date_time);

select max(date_time) from tick_data where data_source_id = 'GAIN' and instrument_id = 'GBPUSD' 
and time >= time('21:05') and time < time('23:07') 
group by date(date_time);

call ohlc_tick('GAIN', 'EURUSD', time('21:08'), time('22:02'));

select max(date(date_time)) start_date, min(date(date_time)) end_date, datediff(max(date(date_time)), min(date(date_time))) days
from tick_data where data_source_id = 'GAIN' and instrument_id = 'GBPUSD';

select time(concat(hour(date_time),':',5*(minute(date_time) div 5))) time, count(*) qty, avg(buy) avg, max(buy) h,
min(buy) l, (std(buy)*10000) stdev, round((max(buy)-min(buy))*10000) spread from tick_data where data_source_id = 'GAIN' and instrument_id = 'GBPUSD'
and date_time >= timestamp('2009-03-12 00:00') and date_time < timestamp('2009-03-13 00:00') group by hour(date_time), minute(date_time) div 5;

select *
from tick_data
where data_source_id = 'GAIN' and instrument_id = 'USDCHF'
and date_time >= timestamp('2009-03-24 00:00')
and date_time < timestamp('2009-03-25 00:00');

select date_time, buy, sell, buy_volume, sell_volume 
from tick_data 
where data_source_id = 'GAIN' and instrument_id = 'GBPUSD' 
and date_time >= ? 
and date_time < ?



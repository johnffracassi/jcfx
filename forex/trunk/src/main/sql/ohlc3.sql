select min(date_time), max(date_time), datediff(max(date_time), min(date_time))
from tick_data
where data_source_id = 'Dukascopy' and instrument_id = 'GBPUSD';

select date(date_time) date, buy, sell, min(buy), max(buy), min(sell), max(sell) 
from tick_data
where data_source_id = 'Dukascopy' and instrument_id = 'GBPUSD'
and time >= time('18:05')
and time <  time('20:40');

select date(date_time) date, open_buy, open_sell, max(high_buy), max(high_sell)
from period_data
where data_source_id = 'Dukascopy' 
and instrument_id = 'GBPUSD' 
and period = 60
--and date_time >= timestamp('2009-03-19')
--and date_time <  timestamp('2009-03-20')
and time >= time('18:05')
and time <  time('20:40')
group by date(date_time)
order by date_time desc;

create or replace view v_period as (
select min(date_time) start_time, max(date_time) end_time, instrument_id, open_buy open_buy, max(high_sell) high_sell, min(low_sell) low_sell, 
    (select open_sell from period_data where data_source_id = 'Dukascopy' and instrument_id = 'GBPUSD' and period = 60 and date_time = max(a.date_time)) close_sell,
    open_sell open_sell, max(high_buy) high_buy, min(low_buy) low_buy,
    (select open_buy from period_data where data_source_id = 'Dukascopy' and instrument_id = 'GBPUSD' and period = 60 and date_time = max(a.date_time)) close_buy
from period_data a
where data_source_id = 'Dukascopy' and instrument_id = 'GBPUSD' and period = 60
and (time >= time('18:06') and time <= time('19:30'))
group by date(date_time)
);

call ohlc(time('19:06'), time('19:33'), 'GAIN', 'EURUSD');

select * from tick_data
where data_source_id = 'GAIN' and instrument_id = 'GBPUSD'
and time = time('18:06');

select *
from period_data
where (date_time = timestamp('2009-03-19 18:06') or date_time = timestamp('2009-03-19 19:30'))
and data_source_id = 'Dukascopy'
and instrument_id = 'GBPUSD'
and period = 60;

select count(*) from period_data;
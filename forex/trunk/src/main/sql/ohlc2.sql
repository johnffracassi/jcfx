select date(date_time) date, open_buy, open_sell, high_buy, high_sell, low_buy, low_sell, close_buy, close_sell, buy_volume, sell_volume 
from period_data 
where data_source_id = 'Dukascopy'
and instrument_id = 'GBPUSD'
and time = time('3:15')
and period = 300;

select *
from tick_data
where data_source_id = 'Dukascopy'
and instrument_id = 'GBPUSD'
and date_time >= timestamp('2008-05-08 03:15:00')
and date_time < timestamp('2008-05-08 03:20:00');

update tick_data set buy = sell, sell = buy;

select data_source_id, instrument_id, min(date_time), max(date_time)
from tick_data
group by data_source_id, instrument_id;

select * from job;
delete from job;
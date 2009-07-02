create or replace view v_candles as (
select data_source_id, instrument_id, period, date_time, open_buy, high_buy, low_buy, close_buy, 
    if(open_buy > close_buy, 'Bear', 'Bull') type,
    round((((high_buy-low_buy) / open_buy) * 100) * 100) relative_height,
    round((high_buy - low_buy)*10000) height, 
    round((high_buy - if(open_buy > close_buy, open_buy, close_buy))*10000) head,
    round((abs(open_buy - close_buy))*10000) body,
    round((if(open_buy < close_buy, open_buy, close_buy) - low_buy)*10000) tail
from period_data);

create or replace view v_candle_class as (
select data_source_id, instrument_id, period, date_time, open_buy, high_buy, low_buy, close_buy, height,
    cast(concat(if(type='Bear','R','L'), round(head/height*9), round(body/height*9), round(tail/height*9)) as char(4)) class
from v_candles);

select tail, count(tail) cnt
from v_candles
where data_source_id = 'Forexite' and instrument_id = 'AUDUSD' and period = '900'
and date_time > timestamp('2008-01-01')
group by tail
order by cnt desc, tail asc;

select *
from v_candle_class
where data_source_id = 'Forexite' and instrument_id = 'AUDUSD' and period = '900'
and date_time > timestamp('2008-01-01')
order by date_time asc;

select class, count(*) cnt
from v_candle_class
where data_source_id = 'Forexite' and instrument_id = 'AUDUSD' and period = '900'
and date_time > timestamp('2007-01-01')
and height >= 10
group by class
order by cnt desc;
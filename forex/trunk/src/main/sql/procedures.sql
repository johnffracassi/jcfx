delimiter $$

drop procedure if exists sma $$
drop procedure if exists ohlc $$
drop procedure if exists get_price $$
drop procedure if exists get_high_low $$
drop procedure if exists ohlc_tick $$

create procedure ohlc_tick(p_data_source_id varchar(16), p_instrument_id char(6), p_start_time time, p_end_time time)
begin
  create temporary table if not exists tmp_o (
    date_time timestamp, buy double, sell double
  );
  create temporary table if not exists tmp_h (
    date date, buy double, sell double
  );
  create temporary table if not exists tmp_l (
    date date, buy double, sell double
  );
  create temporary table if not exists tmp_c (
    date_time timestamp, buy double, sell double
  );

  insert into tmp_o (date_time, buy, sell)
    select date_time, buy, sell
      from tick_data where data_source_id = p_data_source_id and instrument_id = p_instrument_id
      and time >= p_start_time and time < p_end_time
      group by date(date_time);

  insert into tmp_c (date_time, buy, sell)
    select date_time, buy, sell from (
      select date_time, buy, sell from tick_data where data_source_id = p_data_source_id and instrument_id = p_instrument_id
      and time >= p_start_time and time < p_end_time
      order by date_time desc) a
    group by date(date_time);

  insert into tmp_h (date, buy, sell)
  select date(date_time) date, max(buy) buy, max(sell) sell
    from tick_data where  data_source_id = p_data_source_id and instrument_id = p_instrument_id
    and time >= p_start_time and time < p_end_time group by date(date_time);

  insert into tmp_l (date, buy, sell)
  select date(date_time) date, min(buy) buy, min(sell) sell
    from tick_data where  data_source_id = p_data_source_id and instrument_id = p_instrument_id
    and time >= p_start_time and time < p_end_time group by date(date_time);

  select o.date_time, c.date_time, 
         o.buy open_buy, o.sell open_sell,
         h.buy high_buy, h.sell high_sell,
         l.buy low_buy, l.sell low_sell,
         c.buy close_buy, c.sell close_sell 
    from tmp_o o, tmp_h h, tmp_l l, tmp_c c
    where date(o.date_time) = h.date and date(o.date_time) = l.date and date(o.date_time) = date(c.date_time)
    order by o.date_time desc;

  drop table tmp_o;
  drop table tmp_h;
  drop table tmp_l;
  drop table tmp_c;


end;

$$

create procedure get_high_low(p_data_source_id varchar(16), p_instrument_id char(6), p_start timestamp, p_end timestamp, out p_high_buy double, out p_low_buy double, out p_high_sell double, out p_low_sell double)
begin
  select max(buy), min(buy), max(sell), min(sell)
    into p_high_buy, p_low_buy, p_high_sell, p_low_sell
    from tick_data
    where  data_source_id = p_data_source_id and instrument_id = p_instrument_id
    and date_time >= p_start
    and date_time < p_end;
end;

$$

create procedure get_price(p_data_source_id varchar(16), p_instrument_id char(6), inout p_date_time timestamp, out p_buy double, out p_sell double)
begin
  select date_time, buy, sell
    into p_date_time, p_buy, p_sell
    from tick_data
    where data_source_id = p_data_source_id and instrument_id = p_instrument_id
    and date_time >= p_date_time 
    and date_time < date_add(p_date_time, interval 5 minute)
    group by instrument_id;
end;

$$

create procedure ohlc(p_open time, p_close time, p_datasource varchar(16), p_instrument char(6))
begin
  create temporary table if not exists temp_ohlc(
    start_time timestamp, end_time timestamp, instrument_id char(6),
    open_buy double, high_sell double, low_sell double, close_sell double,
    open_sell double, high_buy double, low_buy double, close_buy double
  );

  select min(date_time) start_time, max(date_time) end_time, instrument_id, open_buy open_buy, max(high_sell) high_sell, min(low_sell) low_sell, 
   (select open_sell from period_data where data_source_id = p_datasource and instrument_id = p_instrument and period = 60 and date_time = max(a.date_time)) close_sell,
   open_sell open_sell, max(high_buy) high_buy, min(low_buy) low_buy,
   (select open_buy from period_data where data_source_id = p_datasource and instrument_id = p_instrument and period = 60 and date_time = max(a.date_time)) close_buy
  from period_data a
  where data_source_id = p_datasource and instrument_id = p_instrument and period = 60
  and (time >= time(p_open) and time <= time(p_close))
  group by date(date_time);

  select * from temp_ohlc;
  drop table temp_ohlc;

end;

$$

create procedure sma(p_start_date timestamp, p_end_date timestamp, p_instrument_id char(6), p_period int, p_slide int)
begin
    -- SMA name
    set @name = concat('SMA(',p_period,'m)');

    -- set the initial window bounds
    set @periodEndTime = p_start_date;
    set @periodStartTime = date_sub(@periodEndTime, interval p_period minute);

    -- temp storage for the sma results
    create temporary table if not exists temp_sma (
      name varchar(16), instrument_id char(6), date_time timestamp, sample_size int, buy double, sell double, buy_vol double, sell_vol double
    );

    -- get the SMA value, then slide the window, then repeat
    while(timestamp(@periodEndTime) < p_end_date) do
      insert into temp_sma
        select @name name, p_instrument_id instrument_id,
            timestamp(@periodEndTime) date_time, count(*) sample_size, avg(buy) buy, avg(sell) sell,
            avg(buy_volume) buy_vol, avg(sell_volume) sell_vol
          from tick_data
          where instrument_id = p_instrument_id
          and date_time >= @periodStartTime
          and date_time < @periodEndTime;

      set @periodEndTime = date_add(@periodEndTime, interval p_slide second);
      set @periodStartTime = date_sub(@periodEndTime, interval p_period minute);
    end while;

    -- return the data in a cursor
    select * from temp_sma order by date desc;
    drop table temp_sma;

end;

$$

delimiter ;

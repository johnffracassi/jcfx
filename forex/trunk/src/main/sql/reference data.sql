drop table if exists data_source;
drop table if exists instrument;
drop table if exists data_source_instrument;
drop table if exists commodity_type;
drop table if exists commodity;
drop table if exists news_source;

// reference data table - source of pricing data
create table data_source ( 
    data_source_id varchar(16) not null primary key,
    description	varchar(64) not null,
    time_offset int not null,
    url varchar(400)
) engine = myisam;
insert into data_source values('Alpari', 'Alpari UK', +2, null);
insert into data_source values('GAIN', 'GAIN Capital', -4, null);
insert into data_source values('Dukascopy', 'Dukascopy (Swiss)', 0, null);
insert into data_source values('FXCM', 'FXCM', -5, null);
insert into data_source values('Forexite', 'Forexite', +1, null);


create table news_source (
    news_source_id varchar(32) not null primary key,
    description varchar(128) not null,
    time_offset int not null,
    url varchar(400)
) engine = myisam;
insert into news_source values('ActionForex', 'Action Forex', '0', 'http://www.actionforex.com/');

// reference data
create table commodity_type (
    commodity_type_id char(1) not null primary key,
    description varchar(64)
) engine = myisam;
insert into commodity_type values('C', 'Currency');
insert into commodity_type values('M', 'Metal');
insert into commodity_type values('S', 'Stock');
insert into commodity_type values('I', 'Stock Index');


// single currency / metal / stock / index
create table commodity (
    commodity_id char(6) not null primary key,
    commodity_type_id char(1) not null,
    name varchar(64)
) engine = myisam;
insert into commodity values('AUD', 'C', 'Australian Dollar');
insert into commodity values('EUR', 'C', 'Euro');
insert into commodity values('USD', 'C', 'US Dollar');
insert into commodity values('GBP', 'C', 'Pound Sterling');
insert into commodity values('CAD', 'C', 'Canadian Dollar');
insert into commodity values('NZD', 'C', 'New Zealand');
insert into commodity values('JPY', 'C', 'Japanese Yen');
insert into commodity values('SGD', 'C', 'Singapore Dollar');
insert into commodity values('CHF', 'C', 'Swiss Franc');
insert into commodity values('XAU', 'M', 'Gold');
insert into commodity values('XAG', 'M', 'Silver');


// product pair
create table instrument (
    instrument_id char(6) not null primary key,
    primary_product_id char(6) not null,
    secondary_product_id char(6) not null,
    name varchar(64)
) engine = myisam;
insert into instrument values('AUDJPY', 'AUD', 'JPY', 'AUDJPY');
insert into instrument values('AUDNZD', 'AUD', 'NZD', 'AUDNZD');
insert into instrument values('AUDUSD', 'AUD', 'USD', 'AUDUSD');
insert into instrument values('CHFJPY', 'CHF', 'JPY', 'CHFJPY');
insert into instrument values('EURAUD', 'EUR', 'AUD', 'EURAUD');
insert into instrument values('EURCAD', 'EUR', 'CAD', 'EURCAD');
insert into instrument values('EURCHF', 'EUR', 'CHF', 'EURCHF');
insert into instrument values('EURGBP', 'EUR', 'GBP', 'EURGBP');
insert into instrument values('EURJPY', 'EUR', 'JPY', 'EURJPY');
insert into instrument values('EURUSD', 'EUR', 'USD', 'EURUSD');
insert into instrument values('GBPCHF', 'GBP', 'CHF', 'GBPCHF');
insert into instrument values('GBPJPY', 'GBP', 'JPY', 'GBPJPY');
insert into instrument values('GBPUSD', 'GBP', 'USD', 'GBPUSD');
insert into instrument values('NZDJPY', 'NZD', 'JPY', 'NZDJPY');
insert into instrument values('NZDUSD', 'NZD', 'USD', 'NZDUSD');
insert into instrument values('USDCAD', 'USD', 'CAD', 'USDCAD');
insert into instrument values('USDCHF', 'USD', 'CHF', 'USDCHF');
insert into instrument values('USDJPY', 'USD', 'JPY', 'USDJPY');
insert into instrument values('XAGUSD', 'XAG', 'USD', 'Silver');
insert into instrument values('XAUUSD', 'XAU', 'USD', 'Gold');


// instruments for each data source
create table data_source_instrument (
    data_source_id varchar(16) not null,
    instrument_id char(6) not null,
    primary key (data_source_id, instrument_id)
) engine = myisam;
delete from data_source_instrument;
insert into data_source_instrument(data_source_id, instrument_id)
    select distinct data_source_id, instrument_id
    from period_data order by instrument_id asc;



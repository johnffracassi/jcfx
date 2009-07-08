// Batting scoresheet
create or replace view iv_result_batting as (
select match_id, partnership_id, a.inns_id, e.ordinal inns_ordinal, striker_id, ball_ordinal, runs, boundary, noball, wide, wicket_type
from t_batsman_innings e, iv_match_inns d, iv_bat_balls a left outer join t_extra b on a.extra_id = b.object_id 
        left outer join t_wicket c on a.wicket_id = c.object_id
where d.inns_id = a.inns_id and e.object_id = d.inns_id
order by inns_ordinal asc, ball_ordinal asc);

// Bowling scoresheet
create or replace view iv_result_bowling as (
select match_id, bowler, over_id, over_ordinal, ball_ordinal, runs, boundary_type boundary, noball, wide, wicket_type
from iv_bowl_balls a left outer join t_extra b on a.extra_id = b.object_id 
        left outer join t_wicket c on a.wicket_id = c.object_id
order by over_ordinal, ball_ordinal);

// Player performances
create or replace view dv_result_summary as (
select mr.match_id, batsman, nrr.score, nrr.bf, nrr.sr, nrr.rc, nrr.bb, nrr.er, nrr.net, nrr
from iv_net_rr nrr, iv_net_match_rnrr mr, t_match m, iv_match_to_season m2s
where nrr.match_id = mr.match_id
and m.object_id = m2s.match_id
and m.object_id = mr.match_id);

// Match details
create or replace view iv_result_details as (
select a.object_id match_id, b.description season, a.description rnd, date(a.start_date_item) date, time(a.start_date_item) time, 
match_home_team_id us, (select sum(runs) from iv_result_batting z where z.match_id = a.object_id) us_score, (select count(*) from iv_result_batting x where x.match_id = a.object_id) us_bf, 
match_away_team_id them, (select sum(runs) from iv_result_bowling y where y.match_id = a.object_id) them_score, (select count(*) from iv_result_bowling w where w.match_id = a.object_id) them_bf
from t_match a, t_season b, iv_match_to_season c
where a.object_id = c.match_id and c.season_id = b.season_id);

create or replace view dv_result_details as (
select *, (us_score/us_bf)*100 bat_sr, (them_score/them_bf)*100 bowl_sr, ((us_score/us_bf)-(them_score/them_bf))*100 nrr
from iv_result_details);



// Test views
select * from dv_result_details where match_id = 54;
select * from dv_result_summary where match_id = 54 order by nrr desc;
select * from iv_result_batting where match_id = 54;
select * from iv_result_bowling where match_id = 54;
select * from dv_net_match_rnrr where match_id = 54;
-- partnership records
create or replace view iv_partnership_to_match as (
select ps.object_id partnership_id, match_innings_id match_id
from t_partnership ps, t_batsman_innings_collection bic, t_match_innings mi
where ps.batsman_innings_collection_p_0 = bic.object_id
and mi.match_innings_batting_id = bic.object_id);

create or replace view iv_innings_to_partnership as (
select distinct inns_id, partnership_id from iv_bat_balls);

create or replace view iv_partnerships as (
select match_id, p.object_id partnership_id, b.ball_striker_id b1, b.ball_non_striker_id b2, p.ordinal pos, sum(b.runs_for_batsman) runs, count(*) bf
from t_ball b, t_partnership p, iv_partnership_to_match p2m
where b.partnership_balls_id = p.object_id and p2m.partnership_id = p.object_id
group by partnership_balls_id
order by runs desc, bf asc);

// partnerships averages based on ordinal of partnership
select pos, count(*) inns, sum(runs) runs, sum(bf) bf, avg(runs) avg, (sum(runs) / sum(bf) * 100) sr, max(runs) hs, min(runs) ls
from iv_partnerships
where (b1='jeff' or b2='jeff')
-- where (b1='jeff' and b2='mick') or (b2 = 'jeff' and b1='mick')
group by pos;

select pos, inns, runs, bf, avg, sr, hs, ls from iv_individual_ps_by_pos where b1='jeff' or b2='jeff';
select b1, b2, pos, inns, runs, bf, avg, sr, hs, ls from iv_individual_ps_by_pos;

// partnership combinations
select if(b1<b2,b1,b2) batsman1, if(b1<b2,b2,b1) batsman2, count(*) inns, sum(runs) runs, sum(bf) bf, 
    format(avg(runs),2) avg, format((sum(runs)/sum(bf)*100),2) sr, max(runs) hs, min(runs) ls, count(if(runs<0,1,null)) neg, count(if(runs>=30 and runs<50,1,null)) 30s, count(if(runs>=50,1,null)) 50s
from iv_partnerships
group by batsman1, batsman2
having count(*) >= 3
order by runs desc;
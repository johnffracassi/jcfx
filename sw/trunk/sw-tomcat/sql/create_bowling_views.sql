create or replace view iv_bowl_balls as (
select m.object_id match_id, b.ball_bowler_id bowler, o.object_id over_id, o.ordinal over_ordinal, b.ordinal ball_ordinal, b.runs_for_team runs, ball_extra_id extra_id, ball_wicket_id wicket_id, b.boundary_type
from t_match m, t_match_innings mi, t_bowler_innings_collection bic, t_bowler_innings bi, t_over_collection oc, t_over o, t_ball_collection bc, t_ball b
where m.object_id = mi.match_innings_id
and mi.match_innings_bowling_id = bic.object_id
and bic.object_id = bi.bowler_innings_collection_in_0
and bi.bowler_innings_overs_id = oc.object_id
and oc.object_id = o.over_collection_overs_id
and o.over_balls_id = bc.object_id
and bc.object_id = b.ball_collection_balls_id);


create or replace view iv_bowl_overs as (
select match_id, bowler, over_ordinal, sum(runs) runs, count(*) balls, 
    count(wicket_id) wickets, count(extra_id) extras,
    count(if(wicket_type = 'BOWLED',1,null)) bwld, 
    count(if(wicket_type = 'CAUGHT',1,null)) cgt, 
    count(if(wicket_type = 'STUMPED',1,null)) st, 
    count(if(wicket_type = 'RUN_OUT',1,null)) ro, 
    count(if(wicket_type = 'LBW',1,null)) lbw, 
    count(if(wicket_type = 'MANKAD',1,null)) m,
    count(if(runs >= 7,1,null)) sevens,
    count(if(runs = 0,1,null)) dots,
    count(if(e.wide > 0,1,null)) wd,
    count(if(e.noball > 0,1,null)) nb
from iv_bowl_balls bb left outer join t_wicket w on bb.wicket_id = w.object_id left outer join t_extra e on bb.extra_id = e.object_id
group by over_id);


create or replace view iv_bowl_dismissals as (
select bowler, sum(runs) runs, count(*) balls, count(wicket_id) wickets, 
    count(if(wicket_type = 'BOWLED',1,null)) bwld, 
    count(if(wicket_type = 'CAUGHT',1,null)) cgt, 
    count(if(wicket_type = 'STUMPED',1,null)) st, 
    count(if(wicket_type = 'RUN_OUT',1,null)) ro, 
    count(if(wicket_type = 'LBW',1,null)) lbw, 
    count(if(wicket_type = 'MANKAD',1,null)) m
from iv_bowl_balls bb left outer join t_wicket w on bb.wicket_id = w.object_id
group by bowler);


create or replace view iv_bowl_match as (
select match_id, bowler, sum(runs) runs, sum(balls) balls, sum(wickets) wickets, sum(extras) extras, (sum(runs) / sum(balls) * 100) er
from iv_bowl_overs
group by match_id, bowler);

select count(distinct match_id) matches, bowler, sum(runs) runs, sum(balls) balls, 
    sum(wickets) wickets, 
    sum(extras) extras, 
    (sum(runs) / sum(balls) * 100) er, (sum(balls) / sum(wickets)) sr
from iv_bowl_overs, t_wicket w
group by match_id, bowler;

create or replace view iv_net_rr as (
select bat.match_id, bat.batsman, (bat.score - bwl.runs) net, bat.score score, bat.balls bf, (bat.score / bat.balls * 100) sr, bwl.runs rc, bwl.balls bb, (bwl.runs / bwl.balls * 100) er, ((bat.score / bat.balls * 100) - (bwl.runs / bwl.balls * 100)) nrr
from iv_bowl_match bwl, iv_bat_match bat
where bwl.match_id = bat.match_id 
and bwl.bowler = bat.batsman);

create or replace view iv_net_match_rnrr as (
select match_id, (sum(score) / sum(bf) * 100) sr, (sum(rc) / sum(bb) * 100) er, ((sum(score) / sum(bf) * 100) - (sum(rc) / sum(bb) * 100)) rnrr, sum(score) score, sum(bf) bf, sum(rc) rc, sum(bb) bb
from iv_net_rr
group by match_id);
select * from iv_net_match_rnrr;

create or replace view dv_net_match_rnrr as (
select batsman, 
    nrr.sr batting, nrr.er bowling, nrr.nrr player_netrr, 
    (mr.rnrr) match_netrr, (nrr.nrr - mr.rnrr) rnrr, 
    m.object_id match_id, m2s.season_id, m.match_away_team_id opposition, m.start_date_item date
from iv_net_rr nrr, iv_net_match_rnrr mr, t_match m, iv_match_to_season m2s
where nrr.match_id = mr.match_id
and m.object_id = m2s.match_id
and m.object_id = mr.match_id
order by (nrr.nrr - mr.rnrr) desc);


select batsman, matches, rnrr
from dv_rnrr_season 
where season_id = 9 and matches > 1
order by rnrr desc;

select a.*, b.matches, ((ir/ibf - irc/ibb) * 100) inrr, ((tr/tbf - trc/tbb) * 100) tnrr, (((ir/ibf - irc/ibb) * 100) - ((tr/tbf - trc/tbb) * 100)) rnrr
from iv_rnrr_interim2 a, iv_matches_played b
where a.batsman = b.batsman and b.season_id = a.season_id;




create or replace view iv_match_to_season as (
select m.object_id match_id, s.season_id, s.description
from t_season s, t_match m
where s.season_matches_id = m.match_collection_matches_id);

// top/bottom 20 performances of the season
select batsman, batting, bowling, player_netrr, match_netrr, rnrr, match_id, opposition, date, if(date > DATE_ADD(curdate(), INTERVAL -7 DAY),'Y','N') is_new
from dv_net_match_rnrr 
where season_id in (9) 
order by rnrr desc limit 20;

select * from dv_net_match_rnrr where season_id in (9) order by rnrr asc limit 20;

create or replace view iv_bowl_team_scores as (
select match_id, sum(runs) score, count(*) balls
from iv_bowl_balls
group by match_id);
select * from iv_bowl_team_scores;

select * from t_player;



////////////////////////////////////////////////////////////////////////
// bowling profile
////////////////////////////////////////////////////////////////////////
create or replace view dv_profile_bowl as (
select bowler, ifnull(b.season_id,"Total") season_id, b.description season, count(*) overs, 
    sum(balls) bb, sum(runs) rc, (sum(runs)/count(*)) rpo, (sum(runs)/sum(balls)*100) er, min(runs) best, max(runs) worst,
    sum(wickets) wickets, sum(bwld) bwld, sum(cgt) cgt, sum(ro) ro, sum(st) st, sum(m) m,
    sum(nb) nb, sum(wd) wd, (sum(nb)+sum(wd)) ext, sum(sevens) sevens, sum(dots) dots,
    count(if(runs<=0,1,null)) lt0, count(if(runs>=10,1,null)) gt10
from iv_bowl_overs a, iv_match_to_season b
where a.match_id = b.match_id
group by bowler, b.season_id with rollup);

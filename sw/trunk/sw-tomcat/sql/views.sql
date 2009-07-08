// every ball for a batsman
create or replace view iv_bat_balls as (
select bi.object_id inns_id, b.partnership_balls_id partnership_id, b.ball_striker_id striker_id, b.ball_non_striker_id non_striker_id, b.ordinal ball_ordinal, runs_for_batsman runs, boundary_type as boundary, ball_extra_id as extra_id, ball_wicket_id as wicket_id
from t_batsman_innings bi, t_ball b, t_ball_collection bc
where b.ball_collection_balls_id = bc.object_id
and bc.object_id = bi.batsman_innings_balls_id);

// every innings for a batsman
create or replace view iv_bat_inns as (
select mi.match_id, bb.inns_id, bi.ordinal position, bi.batsman_innings_player_id batsman, sum(bb.runs) score, count(*) balls, count(extra_id) extras, count(wicket_id) wickets
from iv_bat_balls bb, t_batsman_innings bi, iv_match_inns mi
where bb.inns_id = bi.object_id
and mi.inns_id = bb.inns_id
group by inns_id);

// every match for a batsman (aggregated from iv_bat_inns)
create or replace view iv_bat_match as (
select match_id, batsman, sum(score) score, sum(balls) balls, sum(extras) extras, sum(wickets) wickets
from iv_bat_inns
group by match_id, batsman);

// match details
create or replace view iv_matches as (
select m.object_id match_id, description rnd, start_date_item date, match_away_team_id opp
from t_match m, t_match_innings mi, t_batsman_innings_collection bic
where m.object_id = mi.match_innings_id
and bic.object_id = mi.match_innings_batting_id);

// link view - inningsId to matchId
create or replace view iv_match_inns as (
select m.object_id match_id, bi.object_id inns_id
from t_match m, t_match_innings mi, t_batsman_innings_collection bic, t_batsman_innings bi
where mi.match_innings_id = m.object_id
and bic.object_id = mi.match_innings_batting_id
and bi.batsman_innings_collection_i_0 = bic.object_id);

// steamboat willies batting score for each match
create or replace view dv_top_team_score as (
select match_id, sum(score) score, start_date_item date, match_away_team_id opposition
from iv_bat_inns i, t_match m
where i.match_id = m.object_id
group by match_id);

// displayable batsman scores for every innings
create or replace view dv_top_batsman_score as (
select batsman, position, score, balls, start_date_item date, match_away_team_id opposition, i.match_id
from iv_bat_inns i, t_match m
where i.match_id = m.object_id
order by start_date_item asc);

// cumulative batting career
create or replace view iv_cum_batting as (
select batsman, (select count(*) from dv_top_batsman_score z where a.batsman = z.batsman and z.date <= a.date) inns,
    a.opposition, a.date, score, balls,     
    (select sum(score) from dv_top_batsman_score x where a.batsman = x.batsman and x.date <= a.date) cum_score,
    (select sum(balls) from dv_top_batsman_score y where a.batsman = y.batsman and y.date <= a.date) cum_bf
from dv_top_batsman_score a
order by date asc);

// scores by batting position
create or replace view dv_scores_by_position as (
select position, sum(score) runs, count(*) inns, avg(score) avg, max(score) hs, min(score) ls
from iv_bat_inns 
group by position);

create or replace view iv_batting_detailed as (
select m2s.season_id, mi.match_id, bb.inns_id innings_id, bi.ordinal position, bi.batsman_innings_player_id batsman, sum(bb.runs) score, count(*) balls, 
    ps.runs ps_runs, ps.bf ps_bf, if(ps.b1 = bi.batsman_innings_player_id, ps.b2, ps.b1) ps_partner,
    count(extra_id) extras, 
    (select count(*) from iv_bat_balls b, t_extra e where b.inns_id = innings_id and b.extra_id=e.object_id and e.noball > 0) nb,
    (select count(*) from iv_bat_balls b, t_extra e where b.inns_id = innings_id and b.extra_id=e.object_id and e.wide > 0) wd,
    count(wicket_id) wickets, 
    (select count(*) from iv_bat_balls b, t_wicket w where b.inns_id = innings_id and b.wicket_id=w.object_id and w.wicket_type = 'CAUGHT') cgt,
    (select count(*) from iv_bat_balls b, t_wicket w where b.inns_id = innings_id and b.wicket_id=w.object_id and w.wicket_type = 'BOWLED') bwld,
    (select count(*) from iv_bat_balls b, t_wicket w where b.inns_id = innings_id and b.wicket_id=w.object_id and w.wicket_type = 'STUMPED') st,
    (select count(*) from iv_bat_balls b, t_wicket w where b.inns_id = innings_id and b.wicket_id=w.object_id and w.wicket_type = 'RUN_OUT') ro,
    (select count(*) from iv_bat_balls b, t_wicket w where b.inns_id = innings_id and b.wicket_id=w.object_id and w.wicket_type = 'LBW') lbw,
    count(if(bb.runs >= 9, 1, null)) r9,
    count(if(bb.runs = 8, 1, null)) r8,
    count(if(bb.runs = 7, 1, null)) r7,
    count(if(bb.runs = 6, 1, null)) r6,
    count(if(bb.runs = 5, 1, null)) r5,
    count(if(bb.runs = 4, 1, null)) r4,
    count(if(bb.runs = 3, 1, null)) r3,
    count(if(bb.runs = 2, 1, null)) r2,
    count(if(bb.runs = 1, 1, null)) r1,
    count(if(bb.runs = 0, 1, null)) r0
from iv_bat_balls bb, t_batsman_innings bi, iv_match_inns mi, iv_partnerships ps, iv_innings_to_partnership i2p, iv_match_to_season m2s
where bb.inns_id = bi.object_id
and mi.inns_id = bb.inns_id
and bi.object_id = i2p.inns_id
and i2p.partnership_id = ps.partnership_id
and m2s.match_id = mi.match_id
group by innings_id);


create or replace view dv_profile_bat as (
select batsman, a.season_id, b.description season,
    count(distinct match_id) matches, count(*) inns, sum(score) runs, sum(balls) bf, 
    avg(score) avg, (sum(score)/sum(balls)*100) sr,
    max(score) hs, min(score) ls, count(if(score<0,1,null)) lt0, count(if(score>=15 and score<25,1,null)) gt15, count(if(score>25,1,null)) gt25, 
    sum(ps_runs) ps_runs, sum(ps_bf) ps_bf, avg(ps_runs) ps_avg, (sum(ps_runs)/sum(ps_bf)*100) ps_sr, max(ps_runs) ps_hs,
    min(ps_runs) ps_ls, count(if(ps_runs>30,1,null)) ps_gt30, 
    sum(wickets) wick, sum(bwld) bwld, sum(cgt) cgt, sum(ro) ro, sum(st) st, sum(r0) dots, 
    (sum(r7)+sum(r8)+sum(r9)) sevens, sum(wd) wd, sum(nb) nb, (sum(wd)+sum(nb)) ext
from iv_batting_detailed a, t_season b
where a.season_id = b.season_id
group by batsman, season_id with rollup);


create or replace view iv_batting_detailed_agg as (
select batsman, count(distinct match_id) matches, count(*) inns, sum(score) runs, sum(balls) bf, sum(ps_runs) ps_runs,
    max(score) hs, min(score) ls, max(ps_runs) hps, min(ps_runs) lps, avg(score) avg, (sum(score)/sum(balls)*100) sr,
    sum(extras) ex, sum(nb) nb, sum(wd) wd, sum(wickets) w, sum(bwld) bwld, sum(cgt) cgt, sum(lbw) lbw, sum(ro) ro, sum(st) st,
    sum(r9) r9, sum(r8) r8, sum(r7) r7, sum(r6) r6, sum(r5) r5, sum(r4) r4, sum(r3) r3, sum(r2) r2, sum(r1) r1, sum(r0) r0
from iv_batting_detailed
group by batsman);

// how many times has the back net been reached
create or replace view dv_back_net as (
select batsman, inns, runs, bf, 
    (r9+r8+r7+r6+r5) back_net, (r4+r3+r2+r1) other, r0 dots,
    ((r9+r8+r7+r6+r5)/bf) back_net_perc, ((r4+r3+r2+r1)/bf) other_perc, (r0/bf) dots_perc
from iv_batting_detailed_agg 
where inns >=5 
order by back_net desc, other desc, dots asc);


// innings histogram
create or replace view dv_inns_histo as (
select 
    batsman,
    count(if(score<-10,1,null)) m,
    count(if(score>=-10 and score<-5,1,null)) m10,
    count(if(score>=-5 and score<0,1,null)) m5,
    count(if(score>=0 and score<5,1,null)) p0,
    count(if(score>=5 and score<10,1,null)) p5,
    count(if(score>=10 and score<15,1,null)) p10,
    count(if(score>=15 and score<20,1,null)) p15,
    count(if(score>=20 and score<25,1,null)) p20,
    count(if(score>=25 and score<30,1,null)) p25,
    count(if(score>=30 and score<35,1,null)) p30,
    count(if(score>=35 and score<40,1,null)) p35,
    count(if(score>=40,1,null)) p40
from iv_bat_inns group by batsman);


// most dots/wickets/7s in a match (for the team)
create or replace view dv_match_summary as (
select b.match_id, sum(runs) runs, count(runs) bf, 
        count(if(runs=0,1,null)) dots, count(if(runs=-5,1,null)) wick,
        count(if(runs>=7,1,null)) sevens, count(if(runs<=0,1,null)) no_score, 
        c.match_away_team_id opposition, c.start_date_item date
from iv_bat_balls a, iv_match_inns b, t_match c
where b.inns_id = a.inns_id and b.match_id = c.object_id
group by b.match_id);


// most dot balls in a partnership
create or replace view dv_ps_summary as (
select b.match_id, striker_id, non_striker_id, sum(runs) runs, count(runs) bf, 
        count(if(runs=0,1,null)) dots, count(if(runs=-5,1,null)) wick,
        count(if(runs>=7,1,null)) sevens, count(if(runs<=0,1,null)) no_score,inns_id,
        c.match_away_team_id opposition, c.start_date_item date
from iv_bat_balls a, iv_partnership_to_match b, t_match c
where a.partnership_id = b.partnership_id and b.match_id = c.object_id
group by a.partnership_id);

select * from dv_ps_summary where no_score >= 13 order by no_score desc;
select * from dv_ps_summary where dots >= 9 order by dots desc;
select * from dv_ps_summary where sevens >= 3 order by sevens desc;
select * from dv_ps_summary where wick >= 7 order by wick desc;


// season rnrr
create or replace view iv_rnrr_interim as (
select a.match_id, c.season_id, c.description season, batsman, a.score ind_score, a.bf ind_bf, a.rc ind_rc, a.bb ind_bb, b.score team_score, b.bf team_bf, b.rc team_rc, b.bb team_bb
from iv_net_rr a, iv_net_match_rnrr b, iv_match_to_season c
where a.match_id = b.match_id and c.match_id = a.match_id);

create or replace view iv_rnrr_interim2 as (
select season_id, season, batsman, sum(ind_score) ir, sum(ind_bf) ibf, sum(ind_rc) irc, sum(ind_bb) ibb, 
sum(team_score) tr, sum(team_bf) tbf, sum(team_rc) trc, sum(team_bb) tbb
from iv_rnrr_interim
group by season, batsman);

create or replace view iv_rnrr_interim3 as (
select batsman, sum(ind_score) ir, sum(ind_bf) ibf, sum(ind_rc) irc, sum(ind_bb) ibb, 
sum(team_score) tr, sum(team_bf) tbf, sum(team_rc) trc, sum(team_bb) tbb
from iv_rnrr_interim
group by batsman);

create or replace view dv_rnrr_season as (
select a.*, b.matches, ((ir/ibf - irc/ibb) * 100) inrr, ((tr/tbf - trc/tbb) * 100) tnrr, (((ir/ibf - irc/ibb) * 100) - ((tr/tbf - trc/tbb) * 100)) rnrr
from iv_rnrr_interim2 a, iv_matches_played b
where a.batsman = b.batsman);

create or replace view iv_rnrr_career as (
select *, ((ir/ibf - irc/ibb) * 100) inrr, ((tr/tbf - trc/tbb) * 100) tnrr, (((ir/ibf - irc/ibb) * 100) - ((tr/tbf - trc/tbb) * 100)) rnrr
from iv_rnrr_interim3);

create or replace view iv_matches_played as (
select batsman, season_id, count(distinct a.match_id) matches 
from iv_bat_match a, iv_match_to_season b 
where a.match_id = b.match_id 
group by season_id, batsman);


// career RNRR
create or replace view dv_rnrr_career as (
select a.batsman, sum(matches) matches, ir, ibf, irc, ibb, inrr, tr, tbf, trc, tbb, tnrr, rnrr
from iv_rnrr_career a, iv_matches_played b 
where a.batsman = b.batsman and b.matches >= 3
group by a.batsman
order by rnrr desc);


// damage factor
create or replace view dv_damage as (
select batsman, matches, 
ir, ibf, irc, ibb, inrr, 
tr, tbf, trc, tbb, tnrr, rnrr, 
(select count(*)+1 from dv_rnrr_career b where a.rnrr < b.rnrr) rank,
(tr-ir) dr, tbf-ibf dbf, trc-irc drc, tbb-ibb dbb, (((tr-ir)/(tbf-ibf) - (trc-irc)/(tbb-ibb))*100) tnrr2
from dv_rnrr_career a);

create or replace view dv_profile as (
select batsman player, display_name, first_name, surname, origin, count(*) matches, min(date) first_match, max(date) last_match, 
    if(min(date) < date('2006-09-25'),'Y','N') original,
    (select count(*) from iv_matches_played b where i.batsman = b.batsman) seasons,
    if((select count(*) from iv_matches_played c where c.batsman = i.batsman and season_id = (select max(season_id) from t_season))=1,'Y','N') current
from t_player p, iv_bat_inns i, iv_matches m
where p.username = i.batsman and i.match_id = m.match_id
group by batsman);

// batsman in each position, ordered by average
create or replace view iv_bat_in_pos as (
select position pos, batsman, count(*) inns, sum(score) runs, avg(score) avg, max(score) hs, min(score) ls 
from iv_bat_inns group by position, batsman 
having inns > 2
order by pos asc, avg desc);


create or replace view dv_bat_in_pos as (
select a.pos, a.batsman, a.inns, a.runs, a.avg avg, a.hs, a.ls, 
(select count(*) from iv_bat_in_pos b where b.pos = a.pos and b.avg > a.avg) + 1 rank
from iv_bat_in_pos a);
select * from dv_bat_in_pos where rank <= 3;
select * from dv_bat_in_pos;


// All match results, sorted by winning margin
create or replace view dv_match_results as (
select bat.match_id, bat.score us, bwl.score them, opposition, date(date) date, hour(date) hr, minute(date) min, if(bat.score > bwl.score, 'Won', 
        if(bat.score < bwl.score, 'Lost', 'Tied')) result, (bat.score - bwl.score) margin
from dv_top_team_score bat, iv_bowl_team_scores bwl
where bat.match_id = bwl.match_id 
order by margin desc);

// W/T/L record by game time
create or replace view dv_match_time as (
select cast((concat(hour(date), ":",  lpad(minute(date),2,'0'))) as char) game_time, 
    avg(bat.score) us, 
    avg(bwl.score) them, 
    count(*) p,
    count(if(bat.score > bwl.score, 1, null)) w, 
    count(if(bat.score = bwl.score, 1, null)) t,  
    count(if(bat.score < bwl.score, 1, null)) l, 
    format(avg(bat.score - bwl.score),1) margin
from dv_top_team_score bat, iv_bowl_team_scores bwl
where bat.match_id = bwl.match_id
group by game_time
order by game_time asc);


// batsman in each position, ordered by average
create or replace view iv_bat_in_pos as (
select position pos, batsman, count(*) inns, sum(score) runs, avg(score) avg, max(score) hs, min(score) ls 
from iv_bat_inns group by position, batsman 
having inns > 2
order by pos asc, avg desc);

create or replace view dv_bat_in_pos as (
select a.pos, a.batsman, a.inns, a.runs, a.avg avg, a.hs, a.ls, 
(select count(*) from iv_bat_in_pos b where b.pos = a.pos and b.avg > a.avg) + 1 rank
from iv_bat_in_pos a);


// All match results, sorted by winning margin
create or replace view dv_match_results as (
select bat.match_id, bat.score us, bwl.score them, opposition, date(date) date, hour(date) hr, minute(date) min, if(bat.score > bwl.score, 'Won', 
        if(bat.score < bwl.score, 'Lost', 'Tied')) result, (bat.score - bwl.score) margin
from dv_top_team_score bat, iv_bowl_team_scores bwl
where bat.match_id = bwl.match_id 
order by margin desc);


// W/T/L record by game time
create or replace view dv_match_time as (
select cast((concat(hour(date), ":",  lpad(minute(date),2,'0'))) as char) game_time, 
    avg(bat.score) us, 
    avg(bwl.score) them, 
    count(*) p,
    count(if(bat.score > bwl.score, 1, null)) w, 
    count(if(bat.score = bwl.score, 1, null)) t,  
    count(if(bat.score < bwl.score, 1, null)) l, 
    format(avg(bat.score - bwl.score),1) margin
from dv_top_team_score bat, iv_bowl_team_scores bwl
where bat.match_id = bwl.match_id
group by game_time
order by game_time asc);


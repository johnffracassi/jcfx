// =============================================================================
// test views
// =============================================================================
select count(*) from t_ball;
select * from iv_bat_balls;
select * from iv_bat_inns order by score desc, balls asc;
select * from iv_matches;
select * from iv_match_inns;


select match_id,us,them,opposition,date,result,margin from dv_match_results order by margin desc limit 10;
select match_id,us,them,opposition,date,result,margin from dv_match_results order by margin asc limit 10;
select match_id,us,them,opposition,date,result,margin from dv_match_results order by abs(margin) asc limit 10;


select * from dv_match_time;


// match results by season
select s.description, 
    count(*) p,
    count(if(bat.score > bwl.score, 1, null)) w, 
    count(if(bat.score = bwl.score, 1, null)) t,  
    count(if(bat.score < bwl.score, 1, null)) l, 
    format(avg(bat.score),1) us, 
    format(avg(bwl.score),1) them, 
    format(avg(bat.score - bwl.score),1) margin
from dv_top_team_score bat, iv_bowl_team_scores bwl, iv_match_to_season m2s, t_season s
where bat.match_id = bwl.match_id
and bwl.match_id = m2s.match_id
and s.season_id = m2s.season_id
group by s.season_id
order by s.season_id asc;

// profile records
select * from dv_top_batsman_score where 1=1 and batsman='jeff' order by date desc;
select match_id, if(b1='jeff',b2,b1) partner, pos, runs, bf from iv_partnerships where b1='jeff' or b2='jeff';
select count(*) inns, sum(score) score, sum(balls) bf, avg(score) avg, opposition 
from dv_top_batsman_score where batsman='jeff' group by opposition order by score desc;

// best/worst individual innings
select * from dv_top_batsman_score order by score desc, balls asc limit 40;
select * from dv_top_batsman_score order by score asc, balls desc limit 40;

// an individuals batting career
select * from iv_batting_detailed_agg;

// who's been asked to bat again?
select batsman, (inns - matches) bat_again, matches from iv_batting_detailed_agg order by bat_again desc;

// runs scored by everyone by position in batting order
select * from dv_scores_by_position order by position asc;

select * from dv_bat_in_pos where rank <= 3;
select * from dv_bat_in_pos;

select * from t_player;

select * from iv_bowl_balls order by runs desc;
select * from iv_bowl_overs order by runs desc;
select * from iv_bowl_match order by er desc;

select season_id, nrr.*
from iv_net_rr nrr, iv_match_to_season ms
where nrr.match_id = ms.match_id
and season_id in ('9')
order by nrr desc;

select 'uk' origin, sum(bc.inns) inns, sum(runs) runs, sum(bf) balls, (sum(runs) / sum(inns)) avg, (sum(runs) / sum(bf) * 100) sr
from iv_batting_detailed_agg bc, t_player p
where p.username = bc.batsman
and origin in ('england', 'scotland', 'wales')
union
select origin, sum(bc.inns) inns, sum(runs) runs, sum(bf) balls, (sum(runs) / sum(inns)) avg, (sum(runs) / sum(bf) * 100) sr
from iv_batting_detailed_agg bc, t_player p
where p.username = bc.batsman
and origin in ('au', 'nz')
group by origin
union
select 'other' origin, sum(bc.inns) inns, sum(runs) runs, sum(bf) balls, (sum(runs) / sum(inns)) avg, (sum(runs) / sum(bf) * 100) sr
from iv_batting_detailed_agg bc, t_player p
where p.username = bc.batsman
and origin not in ('au', 'nz', 'england', 'scotland', 'wales');

select origin, sum(bc.inns) inns, sum(runs) runs, sum(bf) balls, (sum(runs) / sum(inns)) avg, (sum(runs) / sum(bf) * 100) sr
from iv_batting_detailed_agg bc, t_player p
where p.username = bc.batsman
and origin in ('england', 'scotland', 'wales')
group by origin;

select count(*) matches, match_away_team_id opp from t_match group by opp order by opp asc;
select * from t_competition;
select * from t_season;

select * from t_match;
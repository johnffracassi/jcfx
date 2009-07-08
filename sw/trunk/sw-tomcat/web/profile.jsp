<%@ include file="inc/pre.jspf" %>


<%@page import="com.siebentag.sw.web.BattingChart"%>
<%@page import="org.apache.taglibs.standard.tag.common.sql.ResultImpl"%><c:set var="playerId" value="${param['playerId']}" />

<%-- ****************************************************************
** Player profile
********************************************************************* --%>
<sql:query var="profile" dataSource="jdbc/sw1">
	select * from t_player where username = '${playerId}'
</sql:query>
<c:forEach var="row" items="${profile.rows}" varStatus="status">
	<table xmlns:Math="http://www.oracle.com/XSL/Transform/java/java.lang.Math" class="stats">
	  <caption class="stats">
	  	<img src="images/${row.origin}.gif" class="flag">
	  	<span style="font-size: 18pt; padding-left: 18px; padding-right: 18px; padding-top: 6px; padding-bottom: 6px;">${row.display_name}</span>
	  	<img src="images/${row.origin}.gif">
	  </caption>
	
	   <tr>
	      <td>
	         <table class="profile" style="width: 100%;">
	            <tr>
	               <th>Full name</th>
	               <td>${row.first_name} ${row.surname}</td>
	            </tr>
	            <tr>
	               <th>Origin</th>
	               <td>${row.origin}</td>
	            </tr>
	         </table>
	      </td>
	      <td class="picture">
	         <div><img src="./images/${playerId}.jpg" style="border: 1px solid black;" height="150" width="117"></div>
	      </td>
	      <td style="widthL 100px;"><span></span></td>
	   </tr>
	</table>
</c:forEach>
<br/><br/>

<%-- ****************************************************************
** Batting statistics
********************************************************************* --%>
<sql:query var="profile_bat" dataSource="jdbc/sw1">
	select * from dv_profile_bat where batsman = '${playerId}'
</sql:query>
<table class="stats" cellpadding="2" cellspacing="0">
	<caption>Batting Statistics</caption>

	<thead>
        <caption class="stats">Batting Statistics</caption>
        <tr>
           <th class="season" rowspan="2">Season</th>
           <th class="lb num2" rowspan="2">Inns</th>
           <th class="lb centre" colspan="9">Player</th>
           <th class="lb centre" colspan="6">Partnership</th>
           <th class="lb centre" colspan="5">Out</th>
           <th class="lb num2" rowspan="2">Dot</th>
           <th class="num2" rowspan="2">6s</th>
           <th class="num2" rowspan="2">W</th>
           <th class="num2" rowspan="2">NB</th>
           <th class="num2" rowspan="2">Ext</th>
        </tr>
        <tr class="bb">
           <th class="lb num3">Runs</th>
           <th class="num3">BF</th>
           <th class="num3">Avg</th>
           <th class="num3">SR</th>
           <th class="num2">HS</th>
           <th class="num2">LS</th>
           <th class="num2">&lt;0</th>
           <th class="num2">&gt;15</th>
           <th class="num2">&gt;25</th>
           <th class="lb num3">Runs</th>
           <th class="num3">Avg</th>
           <th class="num3">SR</th>
           <th class="num2">HS</th>
           <th class="num2">LS</th>
           <th class="num2">&gt;30</th>
           <th class="lb num2">Total</th>
           <th class="num2">B</th>
           <th class="num2">C</th>
           <th class="num2">RO</th>
           <th class="num2">S</th>
        </tr>
	</thead>

	<tbody>
		<c:forEach var="row" items="${profile_bat.rows}" varStatus="status">
  			<tr ${row.season_id == null ? "class=\"total\"" : ""}>
                   <td class="season">${row.season_id == null ? "<i>Total</i>" : row.season}</td>
                   <td class="lb num2">${row.inns}</td>
                   <td class="lb num3">${row.runs}</td>
                   <td class="num3">${row.bf}</td>
                   <td class="num3"><fmt:formatNumber value="${row.avg}" pattern="0.00" /></td>
                   <td class="num3"><fmt:formatNumber value="${row.sr}" pattern="0.0" /></td>
                   <td class="num2">${row.hs}</td>
                   <td class="num2">${row.ls}</td>
                   <td class="num2">${row.lt0}</td>
                   <td class="num2">${row.gt15}</td>
                   <td class="num2">${row.gt25}</td>

                   <td class="lb num3">${row.ps_runs}</td>
                   <td class="num3"><fmt:formatNumber value="${row.ps_avg}" pattern="0.00" /></td>
                   <td class="num3"><fmt:formatNumber value="${row.ps_sr}" pattern="0.0" /></td>
                   <td class="num2">${row.ps_hs}</td>
                   <td class="num2">${row.ps_ls}</td>
                   <td class="num2">${row.ps_gt30}</td>

                   <td class="lb num2">${row.wick}</td>
                   <td class="num2">${row.bwld}</td>
                   <td class="num2">${row.cgt}</td>
                   <td class="num2">${row.ro}</td>
                   <td class="num2">${row.st}</td>

                   <td class="lb num2">${row.dots}</td>
                   <td class="num2">${row.sevens}</td>
                   <td class="num2">${row.wd}</td>
                   <td class="num2">${row.nb}</td>
                   <td class="num2">${row.ext}</td>
  			</tr>
		</c:forEach>
	</tbody>
</table>
<br/><br/>
		
		
<%-- ********************************************************************* --%>		
<%-- Bowling profile --%>
<%-- ********************************************************************* --%>		
<sql:query var="profile_bowl" dataSource="jdbc/sw1">
select * from dv_profile_bowl where bowler = '${playerId}'
</sql:query>

<table class="stats" cellpadding="2" cellspacing="0">
   <caption class="stats">Bowling Statistics</caption>
   <tr>
      <th class="left">Season</th>
      <th class="lb num3">Overs</th>
      <th class="num3">Balls</th>
      <th class="num3">RC</th>
      <th class="num3">RPO</th>
      <th class="num3">Best</th>
      <th class="num3">Worst</th>

      <th class="lb num2">Wick</th>
      <th class="num2">B</th>
      <th class="num2">C</th>
      <th class="num2">RO</th>
      <th class="num2">S</th>
      <th class="num2">M</th>

      <th class="lb num2">Dot</th>
      <th class="num2">6s</th>

      <th class="num2">W</th>
      <th class="num2">NB</th>
      <th class="num2">Ext</th>
      <th class="lb num2">&lt;0</th>
      <th class="num2">&gt;10</th>
   </tr>
   
<c:forEach var="row" items="${profile_bowl.rows}" varStatus="status">
   <tr ${row.season_id == null ? "class=\"total\"" : ""}>
      <td class="season">${row.season}</td>
      <td class="lb num3">${row.overs}</td>
      <td class="num3">${row.bb}</td>
      <td class="num3">${row.rc}</td>
      <td class="num3"><fmt:formatNumber value="${row.rpo}" pattern="0.00" /></td>
      <td class="num3">${row.best}</td>
      <td class="num3">${row.worst}</td>
      <td class="lb num2">${row.wickets}</td>
      <td class="num2">${row.bwld}</td>
      <td class="num2">${row.cgt}</td>
      <td class="num2">${row.ro}</td>
      <td class="num2">${row.st}</td>
      <td class="num2">${row.m}</td>
      <td class="lb num2">${row.dots}</td>
      <td class="num2">${row.sevens}</td>
      <td class="num2">${row.wd}</td>
      <td class="num2">${row.nb}</td>
      <td class="num2">${row.ext}</td>
      <td class="lb num2">${row.lt0}</td>
      <td class="num2">${row.gt10}</td>
   </tr>
</c:forEach>
</table>
<br/><br/>
		
	

<%-- ********************************************************************* --%>		
<%--  Batting graphs --%>
<%-- ********************************************************************* --%>		
<sql:query var="ps_pos" dataSource="jdbc/sw1">
select pos, count(*) inns, sum(runs) runs, sum(bf) bf, avg(runs) avg, (sum(runs) / sum(bf) * 100) sr, max(runs) hs, min(runs) ls
from iv_partnerships
where (b1='${playerId}' or b2='${playerId}')
group by pos
</sql:query>

<table class="stats" cellpadding="2" cellspacing="0">
	<caption>Partnership averages by position</caption>
	<tr>
		<th class="number">Position</th>
		<th class="number">Inns</th>
		<th class="number">Runs</th>
		<th class="number">Balls</th>
		<th class="number">Avg</th>
		<th class="number">S/R</th>
		<th class="number">High</th>
		<th class="number">Low</th>
	</tr>
	<c:forEach items="${ps_pos.rows}" var="row">
		<tr>
			<td class="number">${row.pos}</td>
			<td class="number">${row.inns}</td>
			<td class="number">${row.runs}</td>
			<td class="number">${row.bf}</td>
            <td class="number"><fmt:formatNumber value="${row.avg}" pattern="0.00" /></td>
            <td class="number"><fmt:formatNumber value="${row.sr}" pattern="0.00" /></td>
			<td class="number">${row.hs}</td>
			<td class="number">${row.ls}</td>
		</tr>
	</c:forEach>
</table>

<br/><br/>

<%-- ********************************************************************* --%>		
<%--  Batting graphs --%>
<%-- ********************************************************************* --%>		
<sql:query var="bat_career" dataSource="jdbc/sw1">
select score, ps_runs
from iv_batting_detailed a, iv_matches b
where batsman = '${playerId}' and a.match_id = b.match_id
order by b.date asc
</sql:query>

<table class="stats" cellpadding="2" cellspacing="0">
	<caption>Career Innings and Average</caption>
	<tr>
		<td>
<c:set var="dataSet"><%= BattingChart.chart((ResultImpl)pageContext.getAttribute("bat_career")) %></c:set>
<img src="http://chart.apis.google.com/chart?chbh=a&cht=bvg&chm=D,0033FF,1,0,1,1&${dataSet}&chs=650x200" />
		</td>
	</tr>
</table>
<br/><br/>


<%-- ********************************************************************* --%>		
<%--  Batting Histogram - By ball type --%>
<%-- ********************************************************************* --%>		
<sql:query var="bat_histo" dataSource="jdbc/sw1">
	select ex, w, (r7+r8+r9) r7, (r5+r6) r5, r4, r3, r2, r1, r0
	from iv_batting_detailed_agg
	where batsman = '${playerId}'
</sql:query>

<c:forEach var="row" items="${bat_histo.rows}" varStatus="status">
	<table class="stats" cellpadding="2" cellspacing="0">
		<caption>Batting Histogram - By Ball</caption>
		<tr>
			<th class="numleft">7+</th><td class="number">${row.r7}</td>
			<td rowspan="9" style="border-left: solid 1px #aaa;">

<c:set var="dataSet">${row.r7},${row.r5},${row.r4},${row.r3},${row.r2},${row.r1},${row.r0},${row.w},${row.ex}</c:set>
<c:set var="labels">7s|5s|4s|3s|2s|1s|Dots|Wickets|Extras</c:set>
<c:set var="colours">00ff00|00cf00|00af00|008f00|006f00|004f00|0000ff|ff0000|ffff00</c:set>
<img src="http://chart.apis.google.com/chart?cht=p3&chd=t:${dataSet}&chs=400x175&chl=${labels}&chco=${colours}" />

			</td>
		</tr>
		<tr><th class="numleft">5+</th><td class="number">${row.r5}</td></tr>
		<tr><th class="numleft">4</th><td class="number">${row.r4}</td></tr>
		<tr><th class="numleft">3</th><td class="number">${row.r3}</td></tr>
		<tr><th class="numleft">2</th><td class="number">${row.r2}</td></tr>
		<tr><th class="numleft">1</th><td class="number">${row.r1}</td></tr>
		<tr><th class="numleft">Dot</th><td class="number">${row.r0}</td></tr>
		<tr><th class="numleft">Out</th><td class="number">${row.w}</td></tr>
		<tr><th class="numleft">Extra</th><td class="number">${row.ex}</td></tr>
	</table>
</c:forEach>		
<br/><br/>

<%-- ********************************************************************* --%>		
<%-- Batting histogram - By Innings --%>
<%-- ********************************************************************* --%>		
<sql:query var="bat_histo_inns" dataSource="jdbc/sw1">
	select * from dv_inns_histo where batsman='${playerId}'
</sql:query>
<c:forEach var="row" items="${bat_histo_inns.rows}" varStatus="status">
	<table class="stats" cellpadding="2" cellspacing="0">
		<caption>Batting Histogram - By Innings</caption>
		<tr>
			<th class="text">More than 40</th><td class="number">${row.p40}</td>
			<td rowspan="12" style="border-left: solid 1px #aaa;">

<c:set var="dataSet">${row.m},${row.m10},${row.m5},${row.p0},${row.p5},${row.p10},${row.p15},${row.p20},${row.p25},${row.p30},${row.p35},${row.p40}</c:set>
<c:set var="labels">&lt;-10|-10|-5|0|5|10|15|20|25|30|35|&gt;40</c:set>
<img src="http://chart.apis.google.com/chart?cht=bvg&chd=t:${dataSet}&chs=400x175&chl=${labels}&chxt=y&chxr=0,0,15,3&chds=0,15" />

			</td>
		</tr>
		<tr><th class="text">35 to 39</th><td class="number">${row.p35}</td></tr>
		<tr><th class="text">30 to 34</th><td class="number">${row.p30}</td></tr>
		<tr><th class="text">25 to 29</th><td class="number">${row.p25}</td></tr>
		<tr><th class="text">20 to 24</th><td class="number">${row.p20}</td></tr>
		<tr><th class="text">15 to 19</th><td class="number">${row.p15}</td></tr>
		<tr><th class="text">10 to 14</th><td class="number">${row.p10}</td></tr>
		<tr><th class="text">5 to 9</th><td class="number">${row.p5}</td></tr>
		<tr><th class="text">0 to 5</th><td class="number">${row.p0}</td></tr>
		<tr><th class="text">-5 to -1</th><td class="number">${row.m5}</td></tr>
		<tr><th class="text">-10 to -5</th><td class="number">${row.m10}</td></tr>
		<tr><th class="text">Less than -10</th><td class="number">${row.m}</td></tr>
	</table>
</c:forEach>		

		
<%@ include file="inc/post.jspf" %>

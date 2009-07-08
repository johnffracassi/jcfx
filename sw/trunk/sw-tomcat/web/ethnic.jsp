<%@ include file="inc/pre.jspf" %>

<sql:query var="players" dataSource="jdbc/sw1">
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
	and origin not in ('au', 'nz', 'england', 'scotland', 'wales')
</sql:query>

		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>Batsman Grouped By Origin</caption>
		
			<thead>
				<th class="numleft">Origin</th>
				<th class="number">Inns</th>
				<th class="number">Runs</th>
				<th class="number">Balls</th>
				<th class="number">Avg</th>
				<th class="number">S/R</th>
			</thead>
		
			<tbody>
				<c:forEach var="row" items="${players.rows}" varStatus="status">
		  			<tr class="${status.count mod 2 == 0 ? "even" : "odd" }">
		  				<td class="numleft">${row.origin}</td>
		  				<td class="number">${row.inns}</td>
		  				<td class="number">${row.runs}</td>
		  				<td class="number">${row.balls}</td>
		  				<td class="number"><fmt:formatNumber value="${row.avg}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.sr}" pattern="0.00" /></td>
		  			</tr>
				</c:forEach>
			</tbody>
		</table>

<%@ include file="inc/post.jspf" %>

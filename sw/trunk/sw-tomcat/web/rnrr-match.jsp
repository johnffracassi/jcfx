<%@ include file="inc/pre.jspf" %>

<sql:query var="best" dataSource="jdbc/sw1">
	select batsman, batting, bowling, player_netrr, match_netrr, rnrr, match_id, opposition, date, if(date > DATE_ADD(curdate(), INTERVAL -7 DAY),'Y','N') is_new 
	from dv_net_match_rnrr where season_id in (9) 
	order by rnrr desc limit 20
</sql:query>
<sql:query var="worst" dataSource="jdbc/sw1">
	select batsman, batting, bowling, player_netrr, match_netrr, rnrr, match_id, opposition, date, if(date > DATE_ADD(curdate(), INTERVAL -7 DAY),'Y','N') is_new 
	from dv_net_match_rnrr where season_id in (9) 
	order by rnrr asc limit 20
</sql:query>

		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>Best Individual Match Performances For the Season (Based on RNRR)</caption>
		
			<thead>
				<th>#</th>
				<th class="text">Player</th>
				<th class="number">Bat SR</th>
				<th class="number">Bowl ER</th>
				<th class="number">Net RR</th>
				<th class="number">Match NRR</th>
				<th class="number">RNRR</th>
				<th class="text">Opposition</th>
			</thead> 
		
			<tbody>
				<c:forEach var="row" items="${best.rows}" varStatus="status">
		  			<tr class="${status.count mod 2 == 0 ? "even" : "odd" }">
		  				<td>${status.count}. ${sw:isNewMatch(row.match_id)}</td>
		  				<td class="text" nowrap="nowrap">${sw:name(row.batsman)}</td>
		  				<td class="number"><fmt:formatNumber value="${row.batting}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.bowling}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.player_netrr}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.match_netrr}" pattern="0.00" /></td>
		  				<td class="number"><b><fmt:formatNumber value="${row.rnrr}" pattern="0.00" /></b></td>
		  				<td class="text" nowrap="nowrap">${sw:match(row.match_id)}</td>
		  			</tr>
				</c:forEach>
			</tbody>
		</table>

		<br/><br/>

		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>Worst Individual Match Performances For the Season (Based on RNRR)</caption>
		
			<thead>
				<th>#</th>
				<th class="text">Player</th>
				<th class="number">Bat SR</th>
				<th class="number">Bowl ER</th>
				<th class="number">Net RR</th>
				<th class="number">Match NRR</th>
				<th class="number">RNRR</th>
				<th class="text">Opposition</th>
			</thead> 
		
			<tbody>
				<c:forEach var="row" items="${worst.rows}" varStatus="status">
		  			<tr class="${status.count mod 2 == 0 ? "even" : "odd" }">
		  				<td>${status.count}. ${sw:isNewMatch(row.match_id)}</td>
		  				<td class="text" nowrap="nowrap">${sw:name(row.batsman)}</td>
		  				<td class="number"><fmt:formatNumber value="${row.batting}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.bowling}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.player_netrr}" pattern="0.00" /></td>
		  				<td class="number"><fmt:formatNumber value="${row.match_netrr}" pattern="0.00" /></td>
		  				<td class="number"><b><fmt:formatNumber value="${row.rnrr}" pattern="0.00" /></b></td>
		  				<td class="text" nowrap="nowrap">${sw:match(row.match_id)}</td>
		  			</tr>
				</c:forEach>
			</tbody>
		</table>

<%@ include file="inc/post.jspf" %>

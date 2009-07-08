<%@ include file="inc/pre.jspf" %>

<sql:query var="best" dataSource="jdbc/sw1">
select match_id,us,them,opposition,date,result,margin from dv_match_results order by margin desc limit 10;
</sql:query>
<sql:query var="worst" dataSource="jdbc/sw1">
select match_id,us,them,opposition,date,result,margin from dv_match_results order by margin asc limit 10;
</sql:query>
<sql:query var="closest" dataSource="jdbc/sw1">
select match_id,us,them,opposition,date,result,margin from dv_match_results order by abs(margin) asc limit 10;
</sql:query>

		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>Biggest Wins</caption>
		
			<thead>
				<th>#</th>
				<th class="number">Margin</th>
				<th class="text">Score</th>
				<th class="text">Opposition</th>
			</thead>
		
			<tbody>
				<c:forEach var="row" items="${best.rows}" varStatus="status">
		  			<tr>
		  				<td>${status.count}. ${sw:isNewMatch(row.match_id)}</td>
		  				<td class="number"><b>${row.margin}</b></td>
		  				<td class="text">${row.us} to ${row.them}</td>
		  				<td class="text" nowrap="nowrap">${sw:match(row.match_id)}</td>
		  			</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<br/><br/>
		
		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>Closest Games</caption>
		
			<thead>
				<th>#</th>
				<th class="number">Margin</th>
				<th class="text">Score</th>
				<th class="text">Opposition</th>
			</thead>
		
			<tbody>
				<c:forEach var="row" items="${closest.rows}" varStatus="status">
		  			<tr>
		  				<td>${status.count}. ${sw:isNewMatch(row.match_id)}</td>
		  				<td class="number"><b>${row.margin}</b></td>
		  				<td class="text">${row.us} to ${row.them}</td>
		  				<td class="text" nowrap="nowrap">${sw:match(row.match_id)}</td>
		  			</tr>
				</c:forEach>
			</tbody>

		</table>
		
		<br/><br/>
		
		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>Biggest Defeats</caption>
		
			<thead>
				<th>#</th>
				<th class="number">Margin</th>
				<th class="text">Score</th>
				<th class="text">Opposition</th>
			</thead>
		
			<tbody>
				<c:forEach var="row" items="${worst.rows}" varStatus="status">
		  			<tr>
		  				<td>${status.count}. ${sw:isNewMatch(row.match_id)}</td>
		  				<td class="number"><b>${row.margin}</b></td>
		  				<td class="text">${row.us} to ${row.them}</td>
		  				<td class="text" nowrap="nowrap">${sw:match(row.match_id)}</td>
		  			</tr>
				</c:forEach>
			</tbody>

		</table>

<%@ include file="inc/post.jspf" %>

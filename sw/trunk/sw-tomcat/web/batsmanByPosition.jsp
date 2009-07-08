<%@ include file="inc/pre.jspf" %>

<sql:query var="players" dataSource="jdbc/sw1">
	select pos, batsman, inns, runs, avg, hs, ls from dv_bat_in_pos where rank <= 3
</sql:query>

<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
	<caption>Best Batsman In Each Position</caption>

	<thead>
		<th class="numleft">Pos</th>
		<th class="text">Batsman</th>
		<th class="number">Inns</th>
		<th class="number">Runs</th>
		<th class="number">Avg</th>
		<th class="number">HS</th>
		<th class="number">LS</th>
	</thead>

	<tbody>
		<c:forEach var="row" items="${players.rows}" varStatus="status">
  			<tr class="[${row.batsman}]">
  				<td class="numleft ">${row.pos}</td>
  				<td class="text" nowrap="nowrap">${sw:name(row.batsman)}</td>
  				<td class="number">${row.inns}</td>
  				<td class="number">${row.runs}</td>
  				<td class="number"><fmt:formatNumber value="${row.avg}" pattern="0.0" /></td>
  				<td class="number">${row.hs}</td>
  				<td class="number">${row.ls}</td>
  			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="inc/post.jspf" %>

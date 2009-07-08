<%@ include file="inc/pre.jspf" %>

<sql:query var="players" dataSource="jdbc/sw1">
			SELECT game_time, us, them, p, w, t, l, margin FROM dv_match_time
</sql:query>

<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
	<caption>Results By Game Time</caption>

	<thead>
		<th class="numleft">Game Time</th>
		<th class="number">Us</th>
		<th class="number">Them</th>
		<th class="number">P</th>
		<th class="number">W</th>
		<th class="number">T</th>
		<th class="number">L</th>
		<th class="number">Margin</th>
	</thead>

	<tbody>
		<c:forEach var="row" items="${players.rows}">
  			<tr>
  				<td class="numleft">${row.game_time}</td>
  				<td class="number"><fmt:formatNumber value="${row.us}" pattern="0.0" /></td>
  				<td class="number"><fmt:formatNumber value="${row.them}" pattern="0.0" /></td>
  				<td class="number">${row.p}</td>
  				<td class="number">${row.w}</td>
  				<td class="number">${row.t}</td>
  				<td class="number">${row.l}</td>
  				<td class="number"><fmt:formatNumber value="${row.margin}" pattern="0.0" /></td>
  			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="inc/post.jspf" %>

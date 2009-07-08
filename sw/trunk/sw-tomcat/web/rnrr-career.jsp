<%@ include file="inc/pre.jspf" %>

<sql:query var="rnrr" dataSource="jdbc/sw1">
select batsman, matches, ir, ibf, irc, ibb, inrr, tr, tbf, trc, tbb, tnrr, rnrr, (select count(*)+1 from dv_rnrr_career b where a.rnrr < b.rnrr) rank
from dv_rnrr_career a
</sql:query>

		<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
			<caption>RNRR Career (Minimum 3 matches)</caption>
		
			<thead>
				<tr>
					<th rowspan="2" class="numleft">#</th>
					<th rowspan="2" class="text">Player</th>
					<th rowspan="2" class="number">Matches</th>
					<th class="text" colspan="5">Individual</th>
					<th class="text" colspan="5">Team (while player has been playing)</th>
					<th rowspan="2" class="number">RNRR</th>
				</tr>
				<tr>
					<th class="number">Runs</th>
					<th class="number">Balls Faced</th>
					<th class="number">Runs Conceeded</th>
					<th class="number">Balls Bowled</th>
					<th class="number">Net RR</th>
					<th class="number">Runs</th>
					<th class="number">Balls Faced</th>
					<th class="number">Runs Conceeded</th>
					<th class="number">Balls Bowled</th>
					<th class="number">Net RR</th>
				</tr>
			</thead>
		
			<tbody>
				<c:forEach var="row" items="${rnrr.rows}" varStatus="status">
		  			<tr>
		  				<td class="numleft">${row.rank}</td>
		  				<td class="text" nowrap="nowrap"${sw:name(row.batsman)}</td>
		  				<td class="number">${row.matches}</td>
		  				<td class="number">${row.ir}</td>
		  				<td class="number">${row.ibf}</td>
		  				<td class="number">${row.irc}</td>
		  				<td class="number">${row.ibb}</td>
		  				<td class="number"><fmt:formatNumber value="${row.inrr}" pattern="0.00" /></td>
		  				<td class="number">${row.tr}</td>
		  				<td class="number">${row.tbf}</td>
		  				<td class="number">${row.trc}</td>
		  				<td class="number">${row.tbb}</td>
		  				<td class="number"><fmt:formatNumber value="${row.tnrr}" pattern="0.00" /></td>
		  				<td class="number"><b><fmt:formatNumber value="${row.rnrr}" pattern="0.00" /></b></td>
		  			</tr>
				</c:forEach>
			</tbody>
		</table>
		
<%@ include file="inc/post.jspf" %>

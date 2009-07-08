do <%@ include file="inc/pre.jspf" %>


<%@page import="com.siebentag.sw.web.BattingChart"%>
<%@page import="org.apache.taglibs.standard.tag.common.sql.ResultImpl"%><c:set var="playerId" value="${param['playerId']}" />

<%-- ****************************************************************
** Player profile
********************************************************************* --%>
<c:choose>
	<c:when test="${!empty param['season']}">
		<sql:query var="table" dataSource="jdbc/sw1">
			select * from iv_batting_detailed_agg_season where season_id=${param['season']} order by runs desc
		</sql:query>
	</c:when>
	<c:otherwise>
		<sql:query var="table" dataSource="jdbc/sw1">
			select * from iv_batting_detailed_agg where batsman is not null order by runs desc
		</sql:query>
	</c:otherwise>
</c:choose>
							
<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
	<caption>Batting - Season ${param['season']}</caption>

	<thead>
		<th class="text">Player</th>
		<th class="number">Matches</th>
		<th class="number">Innings</th>
		<th class="number">Runs</th>
		<th class="number">BF</th>
		<th class="number">Avg</th>
		<th class="number">S/R</th>
		<th class="number">HS</th>
		<th class="number">LS</th>
		<th class="number">PS Runs</th>
		<th class="number">PS Avg</th>
	</thead> 

	<tbody>
		<c:forEach var="row" items="${table.rows}" varStatus="status">
 			<tr class="${status.count mod 2 == 0 ? "even" : "odd" }">
 				<td class="text" nowrap="nowrap">${sw:name(row.batsman)}</td>
 				<td class="number"><fmt:formatNumber value="${row.matches}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.inns}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.runs}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.bf}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.avg}" pattern="0.00" /></td>
 				<td class="number"><fmt:formatNumber value="${row.sr}" pattern="0.00" /></td>
 				<td class="number"><fmt:formatNumber value="${row.hs}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.ls}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.ps_runs}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.ps_avg}" pattern="0.00" /></td>
 			</tr>
	</c:forEach>
	</tbody>
</table>

<br/><br/>
		
<%@ include file="inc/post.jspf" %>

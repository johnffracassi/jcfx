<%@ include file="inc/pre.jspf" %>

<%@page import="com.siebentag.sw.web.BattingChart"%>
<%@page import="org.apache.taglibs.standard.tag.common.sql.ResultImpl"%><c:set var="playerId" value="${param['playerId']}" />

<%-- ****************************************************************
** Player profile
********************************************************************* --%>
<sql:query var="table" dataSource="jdbc/sw1">
	select * from dv_back_net order by back_net_perc desc
</sql:query>
							
<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
	<caption>Batting - Percentage of balls dispatched to the back net (min 5 innings)</caption>

	<thead>
		<th class="text">Player</th>
		<th class="number">Innings</th>
		<th class="number">Balls</th>
		<th class="number">Back Net</th>
		<th class="number">Back Net%</th>
		<th class="number">Dots</th>
		<th class="number">Dots%</th>
		<th class="number">Others</th>
		<th class="number">Others%</th>
	</thead> 

	<tbody>
		<c:forEach var="row" items="${table.rows}" varStatus="status">
 			<tr class="${status.count mod 2 == 0 ? "even" : "odd" }">
 				<td class="text" nowrap="nowrap">${sw:name(row.batsman)}</td>
 				<td class="number"><fmt:formatNumber value="${row.inns}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.bf}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.back_net}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.back_net_perc}" pattern="0.00%" /></td>
 				<td class="number"><fmt:formatNumber value="${row.dots}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.dots_perc}" pattern="0.00%" /></td>
 				<td class="number"><fmt:formatNumber value="${row.other}" pattern="0" /></td>
 				<td class="number"><fmt:formatNumber value="${row.other_perc}" pattern="0.00%" /></td>
 			</tr>
		</c:forEach>
	</tbody>
</table>

<br/><br/>
		
<%@ include file="inc/post.jspf" %>

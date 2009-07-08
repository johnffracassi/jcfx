<%@ include file="inc/pre.jspf" %>

<%@page import="com.siebentag.sw.web.MatchResult"%>
<%@page import="com.siebentag.sw.web.SWFunctions"%>
<%@page import="java.util.SortedMap"%>
<%@page import="org.apache.taglibs.standard.tag.common.sql.ResultImpl"%>

<%-- ****************************************************************
** Player profile
********************************************************************* --%>
<sql:query var="details" dataSource="jdbc/sw1">
	select * from dv_result_details where match_id = ${param['matchId']}
</sql:query>
<sql:query var="summary" dataSource="jdbc/sw1">
	select * from dv_result_summary where match_id = ${param['matchId']} order by nrr desc
</sql:query>
<sql:query var="batting" dataSource="jdbc/sw1">
	select * from iv_result_batting where match_id = ${param['matchId']}
</sql:query>
<sql:query var="bowling" dataSource="jdbc/sw1">
	select * from iv_result_bowling where match_id = ${param['matchId']}
</sql:query>

<%!
public static int overOrdinal(int ball)
{
	if(ball <= 6) return 1;
	if(ball <= 12) return 2;
	if(ball <= 18) return 3;
	else return 4;
}
%>

<c:forEach var="match" items="${details.rows}" varStatus="status">

<c:set var="result">
	<c:choose>
		<c:when test="${match.us_score gt match.them_score}">Won by ${match.us_score - match.them_score}</c:when>
		<c:when test="${match.us_score lt match.them_score}">Lost by ${match.them_score - match.us_score}</c:when>
		<c:otherwise>Tied</c:otherwise>
	</c:choose>
</c:set>

<c:set var="teamNrr" value="${match.nrr}" />

<table class="stats2" style="border: 1px solid black;" cellspacing="1" cellpadding="3">
	<caption>
		<small><a href="result.jsp?matchId=${param['matchId']-1}">&lt; </a></small>
		&nbsp;&nbsp; ${match.us} vs ${match.them} &nbsp;&nbsp;
		<small><a href="result.jsp?matchId=${param['matchId']+1}"> &gt;</a></small>
	</caption>
	<tr>
		<th class="text">Date</th>
		<td class="text"><fmt:formatDate value="${match.date}" pattern="d-MMM-yy" /></td>
	</tr>
	<tr>
		<th class="text">Time</th>
		<td class="text">${match.time}</td>
	</tr>
	<tr>
		<th class="text">Season</th>
		<td class="text">${match.season}</td>
	</tr>
	<tr>
		<th class="text">Round</th>
		<td class="text">${match.rnd}</td>
	</tr>
	<tr>
		<th class="text">Result</th>
		<td class="text">${result}</td>
	</tr>
	<tr>
		<th class="text">Score</th>
		<td class="text">${match.us_score} to ${match.them_score}</td>
	</tr>
	<tr>
		<th class="text">Batting Run Rate</th>
		<td class="text"><fmt:formatNumber value="${match.bat_sr}" pattern="0.00" /></td>
	</tr>
	<tr>
		<th class="text">Bowling Run Rate</th>
		<td class="text"><fmt:formatNumber value="${match.bowl_sr}" pattern="0.00" /></td>
	</tr>
	<tr>
		<th class="text">Team Net Run Rate</th>
		<td class="text"><fmt:formatNumber value="${teamNrr}" pattern="0.00" /></td>
	</tr>
</table>
</c:forEach>

<br/><br/>

<table class="stats2" style="border: 1px solid black;" cellspacing="1" cellpadding="3">
	<caption>Match Summary</caption>
	<tr>
	   <th class="number left" rowspan="2">#</th>
	   <th class="left" rowspan="2">Player</th>
	   <th class="centre" colspan="3">Bat</th>
	
	   <th class="centre" colspan="3">Bowl</th>
	   <th class="centre" colspan="3">Net</th>
	</tr>
	<tr>
	   <th class="number">Runs</th>
	   <th class="number">Balls</th>
		<th class="number">SR</th>
	   <th class="number">Runs</th>
	   <th class="number">Balls</th>
	   <th class="number">ER</th>
	   <th class="number">Net</th>
	   <th class="number">Net RR</th>
	   <th class="number">RNRR</th>
	</tr>

<c:forEach var="row" items="${summary.rows}" varStatus="status">
	<tr>
		<td>${status.count}</td>
		<td>${sw:name(row.batsman)}</td>
		<td class="number">${row.score}</td>
		<td class="number">${row.bf}</td>
		<td class="number"><fmt:formatNumber value="${row.sr}" pattern="0.00" /></td>
		<td class="number">${row.rc}</td>
		<td class="number">${row.bb}</td>
		<td class="number"><fmt:formatNumber value="${row.er}" pattern="0.00" /></td>
		<td class="number">${row.net}</td>
		<td class="number"><fmt:formatNumber value="${row.nrr}" pattern="0.00" /></td>
		<td class="number"><fmt:formatNumber value="${row.nrr - teamNrr}" pattern="0.00" /></td>
	</tr>
</c:forEach>
</table>

<br/><br/>

<table class="match-result" cellspacing="0">
	<caption>Steamboat Willies Batting</caption>
	<%= MatchResult.batting((ResultImpl)pageContext.getAttribute("batting")) %>
</table>

<br/><br/>

<table class="match-result" cellspacing="0">
	<caption>Steamboat Willies Bowling</caption>

<% // MatchResult.bowling((ResultImpl)pageContext.getAttribute("bowling"))

		ResultImpl rs = (ResultImpl)pageContext.getAttribute("bowling");
		int[] totals = new int[16];
		String[][] table = new String[16][9];
		String[][] styles = new String[16][9];
		
		String[] bowlers = new String[16];
		
		SortedMap[] rows = rs.getRows();

		// initialise table
		for(int i=0; i<table.length; i++)
		{
			for(int j=0; j<table[i].length; j++)
			{
				table[i][j] = "&nbsp;";
				styles[i][j] = "ball";
			}
		}
		
		// build the data table
		for(SortedMap row : rows)
		{
			Integer overOrdinal = (Integer)row.get("over_ordinal");
			String bowler = (String)row.get("bowler");
			Integer score = (Integer)row.get("runs");
			Integer ballOrdinal = (Integer)row.get("ball_ordinal");
			Integer nb = (Integer)row.get("noball");
			Integer wide = (Integer)row.get("wide");
			String wicket = (String)row.get("wicket_type");
			String boundary = (String)row.get("boundary");

			int overIdx = overOrdinal - 1;
			int ballIdx = ballOrdinal - 1;
			bowlers[overIdx] = bowler;
			
			if(wicket != null)
			{
				table[overIdx][ballIdx] += wicket.charAt(0);
				styles[overIdx][ballIdx] +=  " wick";
			}
			else
			{
				if(nb != null && nb > 0)
				{
					table[overIdx][ballIdx] = "NB" + (score>2?score-2:"");
					styles[overIdx][ballIdx] +=  " nb";
				}
				else if(wide != null && wide > 0)
				{
					table[overIdx][ballIdx] = "W" + (score>2?score-2:"");
					styles[overIdx][ballIdx] +=  " wd";
				}
				else
				{
					table[overIdx][ballIdx] = String.valueOf(score);
				}

				if("SIX".equals(boundary)) styles[overIdx][ballIdx] += " six";
				if("FOUR".equals(boundary)) styles[overIdx][ballIdx] += " four";
			}
			
			totals[overIdx] += score;
		}
		
		// assemble the table
		StringBuffer buf = new StringBuffer();
		for(int row = 0; row < 16; row++)
		{
			buf.append("<tr>\n");
			buf.append("<td class=\"indName\">").append(SWFunctions.name(bowlers[row])).append("</td>\n");

			int limit = 9;
			for(int col=0; col<limit; col++)
			{
				String style = styles[row][col];
				
				if(col == limit-1)
				{
					style += " over-break";
				}
				
				buf.append("<td class=\"").append(style).append("\">");
				buf.append(table[row][col]);
				buf.append("</td>\n");
			}
			
			buf.append("<td class=\"indOver\">").append(totals[row]).append("</td>\n");
			
			if(row%4==0)
			{
				buf.append("<td class=\"psTotal\" rowspan=\"4\">").append(totals[row]+totals[row+1]+totals[row+2]+totals[row+3]).append("</td>");
			}
			
			buf.append("</tr>\n");
		}

		out.write(buf.toString());
%>	
</table>

<%@ include file="inc/post.jspf" %>

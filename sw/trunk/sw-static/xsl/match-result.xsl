<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:sw="http://www.zendurl.com/sw"
				xmlns:exsl="http://exslt.org/common" 
				extension-element-prefixes="exsl" >


	<!-- ================================================================================================= -->	
	<!-- Match -->
	<!-- ================================================================================================= -->
	<xsl:template match="match" mode="match-result">
		<xsl:call-template name="output">
			<xsl:with-param name="file">result-<xsl:value-of select="@id" /></xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:variable name="seasonId" select="@seasonId" />
				<xsl:variable name="bat-runs" select="sum(.//innings/ball/@runs)" />
				<xsl:variable name="bowl-runs" select="sum(.//over/ball/@runs)" />
				<xsl:variable name="bat-bf" select="count(.//innings/ball/@runs)" />
				<xsl:variable name="bowl-bf" select="count(.//over/ball/@runs)" />

				<xsl:variable name="bat-sr" select="($bat-runs div $bat-bf) * 100" />
				<xsl:variable name="bowl-sr" select="($bowl-runs div $bowl-bf) * 100" />
				<xsl:variable name="net-sr" select="$bat-sr - $bowl-sr" />

				<table class="stats2" style="border: 1px solid black;" cellspacing="1" cellpadding="3">
					<caption>
						<small><a href="result-{@id - 1}.html">&lt; </a></small>&#160;&#160;
						<xsl:value-of select="@us" /> vs <xsl:value-of select="@opposition" />
						&#160;&#160;<small><a href="result-{@id + 1}.html"> &gt;</a></small>
					</caption>
					<tr>
						<th class="text">Result</th>
						<td class="text">
							<xsl:choose>
								<xsl:when test="$bat-runs &gt; $bowl-runs">
									Won by <xsl:value-of select="$bat-runs - $bowl-runs" />
								</xsl:when>
								<xsl:when test="$bat-runs &lt; $bowl-runs">
									Lost by <xsl:value-of select="$bowl-runs - $bat-runs" />
								</xsl:when>
								<xsl:when test="$bat-runs = $bowl-runs">
									Tied
								</xsl:when>
							</xsl:choose>
						</td>
					</tr>
					<tr>
						<th class="text">Score</th>
						<td class="text">
							<xsl:value-of select="$bat-runs" /> to <xsl:value-of select="$bowl-runs" />
						</td>
					</tr>
					<tr>
						<th class="text">Net Run Rate</th>
						<td class="text">
							<xsl:value-of select="format-number($bat-sr, '#.00')" /> - 
							<xsl:value-of select="format-number($bowl-sr, '#.00')" /> = 
							<xsl:value-of select="format-number($net-sr, '#.00')" />
						</td>
					</tr>
					<tr>
						<th class="text">Season</th>
						<td class="text">
							<xsl:value-of select="//season[@id = $seasonId]/@name" />&#160;
							<xsl:value-of select="//season[@id = $seasonId]/@year" />&#160;
							(<xsl:value-of select="//season[@id = $seasonId]/@grade" />)
						</td>
					</tr>
					<tr><th class="text">Round</th><td class="text"><xsl:value-of select="@round" /></td></tr>
					<tr><th class="text">Date</th><td class="text"><xsl:value-of select="@date" /></td></tr>
					<tr><th class="text">Time</th><td class="text"><xsl:value-of select="@time" /></td></tr>
				</table>
			
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
						<th class="number">RPO</th>
						<th class="number">Net</th>
						<th class="number">Net RR</th>
						<th class="number">RNRR</th>
					</tr>
					<xsl:call-template name="perf-summary">
						<xsl:with-param name="match-set" select="." />
						<xsl:with-param name="rnrr" select="$net-sr" />
					</xsl:call-template>
				</table>
			
				<br/><br/>

				<table class="match-result" cellspacing="0">
					<caption>Batting</caption>
					<xsl:apply-templates select="partnership/innings" mode="match-result">
						<xsl:sort select="@ordinal" order="ascending" data-type="number" />
					</xsl:apply-templates>
				</table>

				<br/><br/>

				<table class="match-result" cellspacing="0">
					<caption>Bowling</caption>
					<xsl:apply-templates select="over" mode="match-result">
						<xsl:sort select="@ordinal" order="ascending" data-type="number" />
					</xsl:apply-templates>
				</table>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="perf-summary">
		<xsl:param name="match-set" />
		<xsl:param name="rnrr" />
		
		<xsl:variable name="net">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$match-set" />
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:for-each-group select="$net//result" group-by="@player">
			<xsl:sort select="sum(@net)" order="descending" data-type="number" />
			<tr id="{concat('[', @player, ']')}">
				<xsl:attribute name="class">
					<xsl:if test="position() mod 2 = 0">even</xsl:if>
					<xsl:if test="position() mod 2 = 1">odd</xsl:if>
				</xsl:attribute>
				
				<xsl:variable name="batsr" select="sum(current-group()/@bat) div sum(current-group()/@bf)" />
				<xsl:variable name="bowlsr" select="sum(current-group()/@bowl) div sum(current-group()/@bb)" />
				
				<td class="num2 left"><xsl:value-of select="position()" /></td>
				<td class="text"><xsl:copy-of select="sw:fullname(current-group()[1]/@player)" /></td>
				<td class="number"><xsl:value-of select="sum(current-group()/@bat)" /></td>
				<td class="number"><xsl:value-of select="sum(current-group()/@bf)" /></td>
				<td class="number"><xsl:value-of select="format-number($batsr * 100, '#.00')" /></td>
				<td class="number"><xsl:value-of select="sum(current-group()/@bowl)" /></td>
				<td class="number"><xsl:value-of select="sum(current-group()/@bb)" /></td>
				<td class="number"><xsl:value-of select="format-number($bowlsr * 6, '#.00')" /></td>
				<td class="number"><xsl:value-of select="sum(current-group()/@net)" /></td>
				<xsl:variable name="nrr" select="($batsr - $bowlsr) * 100" />
				<td class="number"><xsl:value-of select="format-number($nrr, '#.00')" /></td>
				<td class="number"><xsl:value-of select="format-number($nrr - $rnrr, '#.00')" /></td>
			</tr>
		</xsl:for-each-group>
	</xsl:template>

	<xsl:template match="over" mode="match-result">
		<xsl:variable name="row-class">
			<xsl:if test="position() mod 4 != 0">odd</xsl:if>
			<xsl:if test="position() mod 4 = 0">even</xsl:if>
		</xsl:variable>
	
		<tr class="{$row-class}">
			<td class="number left"><xsl:value-of select="sw:ordinal(position())" /></td>
			<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
			<xsl:copy-of select="sw:ball(ball, 1)" />
			<xsl:copy-of select="sw:ball(ball, 2)" />
			<xsl:copy-of select="sw:ball(ball, 3)" />
			<xsl:copy-of select="sw:ball(ball, 4)" />
			<xsl:copy-of select="sw:ball(ball, 5)" />
			<xsl:copy-of select="sw:ball(ball, 6)" />
			<xsl:copy-of select="sw:ball(ball, 7)" />
			<xsl:copy-of select="sw:ball(ball, 8)" />
			<xsl:copy-of select="sw:ball(ball, 9)" />
			<td class="right over-break" style="width: 35px;"><xsl:value-of select="sum(ball/@runs)" /></td>
		</tr>
	</xsl:template>

	<xsl:template match="innings" mode="match-result">
		<xsl:variable name="row-class">
			<xsl:if test="position() mod 2 != 0">odd</xsl:if>
			<xsl:if test="position() mod 2 = 0">even</xsl:if>
		</xsl:variable>

		<tr class="{$row-class}">
			<td class="number left"><xsl:value-of select="sw:ordinal(position())" /></td>
			<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
			<xsl:copy-of select="sw:ball(ball, 1)" />
			<xsl:copy-of select="sw:ball(ball, 2)" />
			<xsl:copy-of select="sw:ball(ball, 3)" />
			<xsl:copy-of select="sw:ball(ball, 4)" />
			<xsl:copy-of select="sw:ball(ball, 5)" />
			<xsl:copy-of select="sw:ball(ball, 6)" />
			<xsl:if test="position() mod 2 != 0">
				<xsl:copy-of select="sw:ball-summary(ball, 1, 6)" />
			</xsl:if>
			<xsl:copy-of select="sw:ball(ball, 7)" />
			<xsl:copy-of select="sw:ball(ball, 8)" />
			<xsl:copy-of select="sw:ball(ball, 9)" />
			<xsl:copy-of select="sw:ball(ball, 10)" />
			<xsl:copy-of select="sw:ball(ball, 11)" />
			<xsl:copy-of select="sw:ball(ball, 12)" />
			<xsl:if test="position() mod 2 != 0">
				<xsl:copy-of select="sw:ball-summary(ball, 7, 12)" />
			</xsl:if>
			<xsl:copy-of select="sw:ball(ball, 13)" />
			<xsl:copy-of select="sw:ball(ball, 14)" />
			<xsl:copy-of select="sw:ball(ball, 15)" />
			<xsl:copy-of select="sw:ball(ball, 16)" />
			<xsl:copy-of select="sw:ball(ball, 17)" />
			<xsl:copy-of select="sw:ball(ball, 18)" />
			<xsl:if test="position() mod 2 != 0">
				<xsl:copy-of select="sw:ball-summary(ball, 13, 18)" />
			</xsl:if>
			<xsl:copy-of select="sw:ball(ball, 19)" />
			<xsl:copy-of select="sw:ball(ball, 20)" />
			<xsl:copy-of select="sw:ball(ball, 21)" />
			<xsl:copy-of select="sw:ball(ball, 22)" />
			<xsl:copy-of select="sw:ball(ball, 23)" />
			<xsl:copy-of select="sw:ball(ball, 24)" />
			<xsl:copy-of select="sw:ball(ball, 25)" />
			<xsl:copy-of select="sw:ball(ball, 26)" />
			<xsl:copy-of select="sw:ball(ball, 27)" />
			<xsl:if test="position() mod 2 != 0">
				<xsl:copy-of select="sw:ball-summary(ball, 19, 27)" />
			</xsl:if>
			<td class="right over-break" style="width: 50px;"><xsl:value-of select="sum(ball/@runs)" /> (<xsl:value-of select="count(ball/@runs)" />)</td>
		</tr>
	</xsl:template>

	<xsl:function name="sw:ball-summary">
		<xsl:param name="ball" />
		<xsl:param name="from" />
		<xsl:param name="to" />
		
		<td rowspan="2" class="ball-summary">
			<xsl:value-of select="sum($ball[1]/../..//innings//ball[@ordinal &gt;= $from and @ordinal &lt;= $to]/@runs)" />
		</td>
	</xsl:function>

	<xsl:function name="sw:ball">
		<xsl:param name="ball" />
		<xsl:param name="ordinal" />
		<xsl:variable name="score" select="$ball[@ordinal = $ordinal]/@score" />
		
		<xsl:variable name="border-class">
			<xsl:if test="$ordinal mod 6 = 1">over-break</xsl:if>
			<xsl:if test="$ordinal mod 6 != 1">non-over-break</xsl:if>
		</xsl:variable>
		
		<xsl:if test="$ball[@ordinal = $ordinal]">
			<td class="ball ball-{$score} {$border-class}">
				<xsl:value-of select="$score" />
			</td>
		</xsl:if>
		<xsl:if test="not($ball[@ordinal = $ordinal])">
			<td class="ball {$border-class}">&#160;</td>
		</xsl:if>
	</xsl:function>

</xsl:stylesheet>
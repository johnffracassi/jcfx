<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw" 
				extension-element-prefixes="exsl" >

	<xsl:template name="bowl-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-figures-best</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Top <xsl:value-of select="$ind-row-count" /> Bowling Figures</caption>
					<xsl:call-template name="detailed-bowl-header">
						<xsl:with-param name="col-pos" select="'false'" />
					</xsl:call-template>
					<xsl:call-template name="bowl-top-figures">
						<xsl:with-param name="order" select="'ascending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-figures-worst</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Worst <xsl:value-of select="$ind-row-count" /> Bowling Figures</caption>
					<xsl:call-template name="detailed-bowl-header">
						<xsl:with-param name="col-pos" select="'false'" />
					</xsl:call-template>
					<xsl:call-template name="bowl-top-figures">
						<xsl:with-param name="order" select="'descending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-over-best</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Top <xsl:value-of select="$ind-row-count" /> Overs</caption>
					<xsl:call-template name="detailed-bowl-header">
						<xsl:with-param name="col-pos" select="'true'" />
					</xsl:call-template>
					<xsl:apply-templates select="//matches" mode="top-overs" />
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-over-worst</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Bottom <xsl:value-of select="$ind-row-count" /> Overs</caption>
					<xsl:call-template name="detailed-bowl-header">
						<xsl:with-param name="col-pos" select="'true'" />
					</xsl:call-template>
					<xsl:apply-templates select="//matches" mode="bottom-overs" />
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-most-runs</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Most Runs Conceeded</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//over" />
						<xsl:with-param name="ball-set" select="//over/ball" />
						<xsl:with-param name="group-func" select="'aggregate'" />
						<xsl:with-param name="collection-header" select="'Overs'" />
						<xsl:with-param name="value-header" select="'Runs'" />
					</xsl:call-template>
				</table>
					</xsl:with-param>
		</xsl:call-template>
		

		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-most-6s</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Most 6s Conceeded</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//over" />
						<xsl:with-param name="ball-set" select="//over/ball[@runs > 6]" />
						<xsl:with-param name="group-func" select="'frequency'" />
						<xsl:with-param name="collection-header" select="'Overs'" />
						<xsl:with-param name="value-header" select="'6s'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		

		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-most-dots</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Most Dot Balls</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//over" />
						<xsl:with-param name="ball-set" select="//over/ball[@runs = 0]" />
						<xsl:with-param name="group-func" select="'frequency'" />
						<xsl:with-param name="collection-header" select="'Overs'" />
						<xsl:with-param name="value-header" select="'Dots'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		

		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-most-extras</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Most Extras Conceeded</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//over" />
						<xsl:with-param name="ball-set" select="//over/ball[starts-with(@score, 'W') or starts-with(@score, 'NB')]" />
						<xsl:with-param name="group-func" select="'frequency'" />
						<xsl:with-param name="collection-header" select="'Overs'" />
						<xsl:with-param name="value-header" select="'W/NB'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		

		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-most-outs</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling - Most Wickets Taken</caption>
					<xsl:call-template name="most-out">
						<xsl:with-param name="summary-set" select="//over" />
						<xsl:with-param name="collection-header" select="'Overs'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		

		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-summary-career</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling Summary - Career</caption>
					<xsl:call-template name="bowl-team-summary">
						<xsl:with-param name="summary-set" select="//over" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bowl-summary-season</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Bowling Summary - Season</caption>
					<xsl:call-template name="bowl-team-summary">
						<xsl:with-param name="summary-set" select="//over[../@seasonId &gt;= $current-season]" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
 	</xsl:template>

	<xsl:template match="matches" mode="top-overs">
		<xsl:apply-templates select="//over" mode="detailed">
			<xsl:sort select="@score" order="ascending" data-type="number" />
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="matches" mode="bottom-overs">
		<xsl:apply-templates select="//over" mode="detailed">
			<xsl:sort select="@score" order="descending" data-type="number" />
		</xsl:apply-templates>
	</xsl:template>
	
	
	<!-- ======================================================================================= -->	
	<!-- Team bowling summary -->
	<!-- ======================================================================================= -->	
	<xsl:template name="bowl-team-summary">
		<xsl:param name="summary-set" />

		<thead>
			<tr>
				<th abbr="num" class="number left">#</th>
				<th abbr="link" class="text">Bowler</th>
				<th abbr="num" class="number">Overs</th>
				<th abbr="num" class="number">Balls</th>
				<th abbr="num" class="number">Runs</th>
				<th abbr="num" class="number">Wickets</th>
				<th abbr="num" class="number">RPO</th>
				<th abbr="num" class="number">SR</th>
			</tr>
		</thead>

		<tbody>	
			<xsl:for-each-group select="$summary-set" group-by="@player">
				<xsl:sort select="count(current-group()/ball[@runs = -5])" data-type="number" order="descending" />
				<xsl:sort select="count(current-group()/@score)" data-type="number" order="ascending" />
			
				<xsl:variable name="player" select="@player" />
				<xsl:variable name="overs" 	select="count(current-group())" />
				<xsl:variable name="runs" 	select="sum(current-group()/@score)" />
				<xsl:variable name="bb"   	select="count(current-group()/ball)" />
				<xsl:variable name="wick"   select="count(current-group()/ball[@runs = -5])" />
				
				<tr id="{concat('[',@player,']')}">
					<td class="numleft"><xsl:value-of select="position()" /></td>
					<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
					<td class="number"><xsl:value-of select="$overs" /></td>
					<td class="number"><xsl:value-of select="$bb" /></td>
					<td class="number"><xsl:value-of select="$runs" /></td>
					<td class="focus-col number"><xsl:value-of select="$wick" /></td>
					<td class="number"><xsl:value-of select="format-number($runs div $overs, '0.00')" /></td>
					<td class="number">
						<xsl:choose>
							<xsl:when test="$wick &gt; 0">
								<xsl:value-of select="format-number($bb div $wick, '##0.00')" />
							</xsl:when>
							<xsl:otherwise>0</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
			</xsl:for-each-group>
		</tbody>
	</xsl:template>
	
	
	<!-- ======================================================================================= -->	
	<!-- Team bowling summary -->
	<!-- ======================================================================================= -->	
	<xsl:template name="detailed-bowl-header">
		<xsl:param name="col-pos" select="'true'" />
	
		<tr>
			<th class="numleft">#</th>
			<th class="text">Bowler</th>
			<th class="number">Runs</th>
			<th class="number">Balls</th>
			<th class="number">SR</th>
			<xsl:if test="$col-pos = 'true'">
				<th class="number">Pos</th>
			</xsl:if>
			<xsl:copy-of select="sw:match-header()" />
		</tr>
	</xsl:template>
	
	<xsl:template name="bowl-top-figures">
		<xsl:param name="summary-set" select="//match" />
		<xsl:param name="order" />
		
		<xsl:variable name="nodeset">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$summary-set" />
			</xsl:call-template>
		</xsl:variable> 
		
		<xsl:apply-templates select="exsl:node-set($nodeset)//result[@bat != 0 and @bowl != 0]" mode="bowl">
			<xsl:sort select="@bowl div @bb" order="{$order}" data-type="number" />
			<xsl:sort select="@player" order="ascending" data-type="text" />
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="result" mode="bowl">
		<xsl:if test="position() &lt;= $ind-row-count">
			<xsl:variable name="match-id" select="matchId" />
			
			<tr id="[{@player}]">
				<td class="numleft" nowrap="nowrap">
					<xsl:value-of select="position()" />
					<xsl:copy-of select="sw:is-new(@date)" />
				</td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number"><xsl:value-of select="@bowl" /></td>
				<td class="number"><xsl:value-of select="@bb" /></td>
				<td class="focus-col number"><xsl:value-of select="format-number(@bowl div @bb * 100, '0.00')" /></td>
				<xsl:copy-of select="sw:match-details-id(@matchId)" />
			</tr>
		</xsl:if>
	</xsl:template>

	<xsl:template match="over" mode="detailed">
		<xsl:if test="position() &lt;= $ind-row-count">
			<tr id="[{@player}]">
				<xsl:variable name="runs" select="@score" />
				<xsl:variable name="bf"   select="count(ball)" />
				<xsl:variable name="seasonId" select="../@seasonId" />
				
				<td class="numleft" nowrap="nowrap">
					<xsl:value-of select="position()" />
					<xsl:copy-of select="sw:is-new(.)" />
				</td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number focus-col"><xsl:value-of select="@score" /></td>
				<td class="number"><xsl:value-of select="count(ball)" /></td>
				<td class="number"><xsl:value-of select="format-number(($runs div $bf) * 100, '#0.00')" /></td>
				<td class="number"><xsl:value-of select="sw:ordinal(@ordinal)" /></td>
				<xsl:copy-of select="sw:match-details(..)" />
			</tr>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw" 
				extension-element-prefixes="exsl" >

	<xsl:template name="weighted-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">weighted-career</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Weighted Player Rankings - Career (10 matches for full ranking)</caption>
					<xsl:call-template name="weighted">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="fully-qualified-matches" select="10" />
					</xsl:call-template>
				</table>
				<xsl:call-template name="weighted-exp" />
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="output">
			<xsl:with-param name="file">weighted-season</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Weighted Player Rankings - Season (3 matches for full ranking)</caption>
					<xsl:call-template name="weighted">
						<xsl:with-param name="match-set" select="//match[@seasonId &gt;= $current-season]" />
						<xsl:with-param name="fully-qualified-matches" select="3" />
					</xsl:call-template>
				</table>
				<xsl:call-template name="weighted-exp" />
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template name="weighted-exp">
		<p>		
		Weighted Average is calculated by ranking players against their teammates in each individual game.<br/>
		The best player for a single match will always receive 10pts. The worst player (aka Armadillo) is always given 0pts. The remainder
		of players are allocated points based on how close to the best/worst players.<br/>
		For example, say P1 scores +25, P2 scores -10, P3 scores +20 and P4 scores +1. Firstly, all scores are normalised to start from 0, 
		in this case +10 is added to all the scores (P1=35,P2=0,P3=30,P4=11). The scores are then scaled by the same amount so that
		the highest score is exactly 10 (in this case the scale is 10/35=0.286). Then we end up with P1=10,P2=0,P3=5.72,P4=3.15<br/>
		</p>
		
	</xsl:template>

	<xsl:template name="weighted">
		<xsl:param name="match-set" />
		<xsl:param name="fully-qualified-matches" />

		<xsl:variable name="net">
			<xsl:call-template name="get-net">
				<xsl:with-param name="summary-set" select="$match-set" />
			</xsl:call-template>
		</xsl:variable>

		<xsl:variable name="results">
			<results>
				<xsl:for-each select="$match-set">
					<xsl:variable name="match-id" select="@id" />
					<xsl:variable name="team-net" select="sum($net//@net[../@matchId = $match-id])" />
					<xsl:variable name="best-net" select="max($net//@net[../@matchId = $match-id])" />
					<xsl:variable name="worst-net" select="min($net//@net[../@matchId = $match-id])" />
					<xsl:variable name="upper-bound" select="10" />
					<xsl:variable name="offset" select="0 - $worst-net" />
					<xsl:variable name="scale" select="$upper-bound div ($best-net - $worst-net)" />
		
					<xsl:for-each select="$net//result[@matchId = $match-id]">
						<xsl:sort select="@net" order="descending" data-type="number" />
						<xsl:sort select="@netrr" order="descending" data-type="number" />

						<xsl:variable name="pts">
							<xsl:choose>
								<xsl:when test="position() = 1">3</xsl:when>
								<xsl:when test="position() = 2">2</xsl:when>
								<xsl:when test="position() = 3">1</xsl:when>
								<xsl:otherwise>0</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
		
						<result>
							<xsl:copy-of select="@player | @net | @batsr | @bowlsr | @netrr" />
							<xsl:attribute name="perc"><xsl:value-of select="sw:perc(@net, $team-net)" /></xsl:attribute>
							<xsl:attribute name="weighted"><xsl:value-of select="(@net + $offset) * $scale" /></xsl:attribute>
							<xsl:attribute name="pts"><xsl:value-of select="$pts" /></xsl:attribute>
						</result>
					</xsl:for-each>
				</xsl:for-each>
			</results>
		</xsl:variable>
		
		<thead>
			<th abbr="link_column" class="text">Player</th>
			<th abbr="num" class="number">Matches</th>
			<th abbr="num" class="number">Net</th>
			
			<th abbr="num" class="number">Match Weighting</th>
			<th abbr="num" class="number">Weighted</th>
			<th abbr="num" class="number">Avg Wt</th>
			<th abbr="num" class="number">Rating</th>
			
			<th abbr="num" class="num2">3</th>
			<th abbr="num" class="num2">2</th>
			<th abbr="num" class="num2">1</th>
			<th abbr="num" class="num2">Tot</th>
			<th abbr="num" class="number">Avg</th>
		</thead>
		
		<tbody>
			<xsl:for-each-group select="$results/results/result" group-by="@player">
				<xsl:sort select="sw:weighting-by-match(count(current-group()), $fully-qualified-matches) * sum(current-group()/@weighted) div count(current-group())" order="descending" data-type="number" />
				
				<xsl:variable name="matches" select="count(current-group())" />
				<xsl:variable name="weighting" select="sw:weighting-by-match($matches, $fully-qualified-matches)" />
				<xsl:variable name="weighted-pts" select="sum(current-group()/@weighted)" />
				
				<tr id="[{@player}]">
					<td class="text"><xsl:copy-of select="sw:fullname(current-group()[1]/@player)" /></td>
					<td class="number"><xsl:value-of select="$matches" /></td>
					<td class="number"><xsl:value-of select="sum(current-group()/@net)" /></td>
					<td class="number"><xsl:value-of select="format-number($weighting * 100, '0')" />%</td>
					<td class="number"><xsl:value-of select="format-number($weighted-pts, '0.000')" /></td>
					<td class="number"><xsl:value-of select="format-number($weighted-pts div $matches, '0.000')" /></td>
					<td class="number"><b><xsl:value-of select="format-number($weighting * $weighted-pts div $matches, '0.000')" /></b></td>

					<td class="num2"><xsl:value-of select="count(current-group()[@pts = 3])" /></td>
					<td class="num2"><xsl:value-of select="count(current-group()[@pts = 2])" /></td>
					<td class="num2"><xsl:value-of select="count(current-group()[@pts = 1])" /></td>
					<td class="num2"><xsl:value-of select="sum(current-group()/@pts)" /></td>
					<td class="number"><xsl:value-of select="sw:divide(sum(current-group()/@pts), count(current-group()))" /></td>
				</tr>
			</xsl:for-each-group>
		</tbody>
		
	</xsl:template>
</xsl:stylesheet>
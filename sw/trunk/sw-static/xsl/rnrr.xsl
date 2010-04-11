<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >


	<!-- ================================================================================================= -->	
	<!-- Matches -->
	<!-- ================================================================================================= -->
	<xsl:template name="rnrr">
	
		<xsl:call-template name="output">
			<xsl:with-param name="file">rnrr</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
			
				<xsl:variable name="rnrr-matches">
					<results>
						<xsl:apply-templates select="//match" mode="rnrr" />
					</results>
				</xsl:variable>
				
				<xsl:variable name="data">
					<innings>
						<xsl:apply-templates select="//innings" mode="rnrr">
							<xsl:with-param name="rnrr" select="$rnrr-matches" />
						</xsl:apply-templates>
					</innings>
					<overs>
						<xsl:apply-templates select="//over" mode="rnrr">
							<xsl:with-param name="rnrr" select="$rnrr-matches" />
						</xsl:apply-templates>
					</overs>
				</xsl:variable>

				<xsl:variable name="rnrr">
					<xsl:call-template name="merge">
						<xsl:with-param name="data" select="$data" />
					</xsl:call-template>
				</xsl:variable>

				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Relative Net Run Rate - All Time (Minimum 7 inns)</caption>
					
					<tr>
						<th rowspan="2">Player</th>
						<th rowspan="2">Inns</th>
						<th colspan="3">Player</th>
						<th colspan="3">Team</th>
						<th rowspan="2">Bat RNRR</th>
						<th rowspan="2">Overs</th>
						<th colspan="3">Player</th>
						<th colspan="3">Team</th>
						<th rowspan="2">Bowl RNRR</th>
						<th rowspan="2">RNRR</th>
					</tr>
					
					<tr>
						<th>Runs</th>
						<th>BF</th>
						<th>Bat RR</th>
						<th>Runs</th>
						<th>BF</th>
						<th>Bat RR</th>
						<th>RC</th>
						<th>Balls</th>
						<th>Bowl RR</th>
						<th>RC</th>
						<th>Balls</th>
						<th>Bowl RR</th>
					</tr>
					
					<xsl:apply-templates select="$rnrr//player[@inns gt 7 and @player ne 'f1']" mode="rnrr">
						<xsl:sort select="@holy-grail" data-type="number" order="descending" />
					</xsl:apply-templates>
				</table>
								
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="player" mode="rnrr">
		<tr>
			<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>

			<td class="number"><xsl:value-of select="@inns" /></td>
			<td class="number"><xsl:value-of select="@runs-scored" /></td>
			<td class="number"><xsl:value-of select="@balls-faced" /></td>
			<td class="number"><xsl:value-of select="format-number(@bat-rr, '0.00')" /></td>
			<td class="number"><xsl:value-of select="@team-runs-scored" /></td>
			<td class="number"><xsl:value-of select="@team-balls-faced" /></td>
			<td class="number"><xsl:value-of select="format-number(@team-bat-rr, '0.00')" /></td>
			<td class="number"><b><xsl:value-of select="format-number(@bat-rnrr, '0.00')" /></b></td>

			<td class="number"><xsl:value-of select="@overs" /></td>
			<td class="number"><xsl:value-of select="@runs-conceeded" /></td>
			<td class="number"><xsl:value-of select="@balls-bowled" /></td>
			<td class="number"><xsl:value-of select="format-number(@bowl-rr, '0.00')" /></td>
			<td class="number"><xsl:value-of select="@team-runs-conceeded" /></td>
			<td class="number"><xsl:value-of select="@team-balls-bowled" /></td>
			<td class="number"><xsl:value-of select="format-number(@team-bowl-rr, '0.00')" /></td>
			<td class="number"><b><xsl:value-of select="format-number(@bowl-rnrr, '0.00')" /></b></td>

			<td class="number"><b><xsl:value-of select="format-number(@holy-grail, '0.00')" /></b></td>
		</tr>
	</xsl:template>

	<xsl:template name="merge">
		<xsl:param name="data" />
		
		<rnrr>
			<xsl:for-each select="//player">
				<xsl:variable name="player" select="@id" />
				
				<xsl:variable name="inns" select="$data//innings[@player = $player]" />
				<xsl:variable name="runs-scored" select="sum($inns/@runs)" />
				<xsl:variable name="balls-faced" select="sum($inns/@balls)" />

				<xsl:if test="$balls-faced &gt; 0">
					<xsl:variable name="team-runs-scored" select="sum($inns/@team-score)" />
					<xsl:variable name="team-balls-faced" select="sum($inns/@team-balls)" />
					<xsl:variable name="bat-rr" select="$runs-scored div $balls-faced * 100.0" />
					<xsl:variable name="team-bat-rr" select="$team-runs-scored div $team-balls-faced * 100.0" />
					
					<xsl:variable name="overs" select="$data//over[@player = $player]" />
					<xsl:variable name="runs-conceeded" select="sum($overs/@runs)" />
					<xsl:variable name="balls-bowled" select="sum($overs/@balls)" />
					<xsl:variable name="team-runs-conceeded" select="sum($overs/@team-score)" />
					<xsl:variable name="team-balls-bowled" select="sum($overs/@team-balls)" />
					<xsl:variable name="bowl-rr" select="$runs-conceeded div $balls-bowled * 100.0" />
					<xsl:variable name="team-bowl-rr" select="$team-runs-conceeded div $team-balls-bowled * 100.0" />

					<xsl:variable name="bat-rnrr" select="$bat-rr - $team-bat-rr" />
					<xsl:variable name="bowl-rnrr" select="$bowl-rr - $team-bowl-rr" />
					<xsl:variable name="holy-grail" select="$bat-rnrr - $bowl-rnrr" />
					
					<xsl:element name="player">
						<xsl:attribute name="player" select="$player" />
						<xsl:attribute name="inns" select="count($inns)" />
						<xsl:attribute name="runs-scored" select="$runs-scored" />
						<xsl:attribute name="balls-faced" select="$balls-faced" />
						<xsl:attribute name="team-runs-scored" select="$team-runs-scored" />
						<xsl:attribute name="team-balls-faced" select="$team-balls-faced" />
						<xsl:attribute name="bat-rr" select="$bat-rr" />
						<xsl:attribute name="team-bat-rr" select="$team-bat-rr" />
						<xsl:attribute name="bat-rnrr" select="$bat-rnrr" />

						<xsl:attribute name="overs" select="count($overs)" />
						<xsl:attribute name="runs-conceeded" select="$runs-conceeded" />
						<xsl:attribute name="balls-bowled" select="$balls-bowled" />
						<xsl:attribute name="team-runs-conceeded" select="$team-runs-conceeded" />
						<xsl:attribute name="team-balls-bowled" select="$team-balls-bowled" />
						<xsl:attribute name="bowl-rr" select="$bowl-rr" />
						<xsl:attribute name="team-bowl-rr" select="$team-bowl-rr" />
						<xsl:attribute name="bowl-rnrr" select="$bowl-rnrr" />
						
						<xsl:attribute name="holy-grail" select="$holy-grail" />
					</xsl:element>
				</xsl:if>
			</xsl:for-each>
		</rnrr>
	</xsl:template>

	<xsl:template match="over" mode="rnrr">
		<xsl:param name="rnrr" />

		<xsl:element name="over">
			<xsl:variable name="matchId" select="../@id" />
			<xsl:variable name="rr" select="@score div @balls * 100.0" />
			<xsl:variable name="trr" select="$rnrr//match[@match-id = $matchId]/@bowl-rr" />
			<xsl:variable name="rrr" select="$rr - $trr" />
			<xsl:variable name="team-score" select="$rnrr//match[@match-id = $matchId]/@bowl-runs" />
			<xsl:variable name="team-balls" select="$rnrr//match[@match-id = $matchId]/@balls-bowled" />
			
			<xsl:attribute name="player" select="@player" />
			<xsl:attribute name="over-id" select="@id" />
			<xsl:attribute name="match-id" select="$matchId" />
			<xsl:attribute name="season-id" select="../@seasonId" />
			<xsl:attribute name="runs" select="@score" />
			<xsl:attribute name="balls" select="@balls" />
			<xsl:attribute name="rr" select="$rr" />
			<xsl:attribute name="trr" select="$trr" />
			<xsl:attribute name="rrr" select="$rrr" />
			<xsl:attribute name="team-score" select="$team-score" />
			<xsl:attribute name="team-balls" select="$team-balls" />
		</xsl:element>	
	</xsl:template>

	<xsl:template match="innings" mode="rnrr">
		<xsl:param name="rnrr" />

		<xsl:element name="innings">
			<xsl:variable name="matchId" select="../../@id" />
			<xsl:variable name="rr" select="@score div @balls * 100.0" />
			<xsl:variable name="trr" select="$rnrr//match[@match-id = $matchId]/@bat-rr" />
			<xsl:variable name="rrr" select="$rr - $trr" />
			<xsl:variable name="team-score" select="$rnrr//match[@match-id = $matchId]/@bat-runs" />
			<xsl:variable name="team-balls" select="$rnrr//match[@match-id = $matchId]/@balls-faced" />
			
			<xsl:attribute name="player" select="@player" />
			<xsl:attribute name="innings-id" select="@id" />
			<xsl:attribute name="match-id" select="$matchId" />
			<xsl:attribute name="season-id" select="../../@seasonId" />
			<xsl:attribute name="runs" select="@score" />
			<xsl:attribute name="balls" select="@balls" />
			<xsl:attribute name="rr" select="$rr" />
			<xsl:attribute name="trr" select="$trr" />
			<xsl:attribute name="rrr" select="$rrr" />
			<xsl:attribute name="team-score" select="$team-score" />
			<xsl:attribute name="team-balls" select="$team-balls" />
		</xsl:element>	
	</xsl:template>

	<xsl:template match="match" mode="rnrr">
	
		<xsl:element name="match">
			<xsl:variable name="bat-runs" select="sum(.//innings/ball/@runs)" />
			<xsl:variable name="bowl-runs" select="sum(.//over/ball/@runs)" />
			<xsl:variable name="balls-faced" select="count(.//innings/ball)" />
			<xsl:variable name="balls-bowled" select="count(.//over/ball)" />
			<xsl:variable name="bat-rr" select="$bat-runs div $balls-faced * 100.0" />
			<xsl:variable name="bowl-rr" select="$bowl-runs div $balls-bowled * 100.0" />
			
			<xsl:attribute name="match-id" select="@id" />
			<xsl:attribute name="bat-runs" select="$bat-runs" />
			<xsl:attribute name="bowl-runs" select="$bowl-runs" />
			<xsl:attribute name="balls-faced" select="$balls-faced" />
			<xsl:attribute name="balls-bowled" select="$balls-bowled" />
			<xsl:attribute name="bat-rr" select="$bat-rr" />
			<xsl:attribute name="bowl-rr" select="$bowl-rr" />
			<xsl:attribute name="nrr" select="$bat-rr - $bowl-rr" />		
			<xsl:attribute name="result">
				<xsl:choose>
					<xsl:when test="$bat-runs &gt; $bowl-runs">W</xsl:when>
					<xsl:when test="$bat-runs &lt; $bowl-runs">L</xsl:when>
					<xsl:when test="$bat-runs = $bowl-runs">T</xsl:when>
				</xsl:choose>
			</xsl:attribute>
		</xsl:element>	
	</xsl:template>

</xsl:stylesheet>
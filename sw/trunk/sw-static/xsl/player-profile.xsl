<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				xmlns:Math="http://www.oracle.com/XSL/Transform/java/java.lang.Math"
				extension-element-prefixes="exsl" >
	

	<!-- ================================================================================================= -->	
	<!-- Player Profile -->
	<!-- ================================================================================================= -->
	<xsl:template match="players">
		<xsl:for-each select="player">
			<xsl:call-template name="output">
				<xsl:with-param name="file">profile-<xsl:value-of select="@id" /></xsl:with-param>
				<xsl:with-param name="doc">
			
					<xsl:apply-templates select="." mode="summary" />
					<br/>
					<xsl:apply-templates select="." mode="batting-record" />
					<br/>
					<xsl:apply-templates select="." mode="bowling-record" />
					<br/>
					<xsl:apply-templates select="." mode="profile" />
					<br/>
					<xsl:apply-templates select="." mode="partners" />
					<br/>
		
					<hr/>
		
					<center>
						<br/><img src="charts/png/bat-history-career-{@id}.png" /><br/>
						<br/><img src="charts/png/bowl-history-career-{@id}.png" /><br/>
						<br/><img src="charts/png/net-history-career-{@id}.png" /><br/>
					</center>
				</xsl:with-param>
			</xsl:call-template>			
		</xsl:for-each>
	</xsl:template>
	

	<!-- ================================================================================================= -->	
	<!-- Player Profile / Summary -->
	<!-- ================================================================================================= -->
	<xsl:template match="player" mode="summary">
		<br/>
		<table class="stats">
			<caption class="stats">
				<xsl:element name="img">
					<xsl:attribute name="src">images/<xsl:value-of select="flag" /></xsl:attribute>
					<xsl:attribute name="class">flag</xsl:attribute>
				</xsl:element>
				
				<span style="font-size: 18pt; padding-left: 18px; padding-right: 18px; padding-top: 6px; padding-bottom: 6px;">
					<xsl:value-of select="fullName" />
				</span>

				<xsl:element name="img">
					<xsl:attribute name="src">images/<xsl:value-of select="flag" /></xsl:attribute>
				</xsl:element>
			</caption>
			<tr>
				<td>
					<table class="profile" style="width: 100%;">
						<tr><th>Full name</th><td><xsl:value-of select="fullName" /></td></tr>
						<tr>
							<th>Origin</th>
							<td>
								<xsl:value-of select="origin" />
							</td>
						</tr>
						<tr><th>DOB</th><td><xsl:value-of select="dob" /></td></tr>
						<tr><th>Age</th><td><xsl:value-of select="currentAge" /></td></tr>
						<tr><th>Major Teams</th><td><xsl:value-of select="teams" /></td></tr>
						<tr><th>Team Role</th><td><xsl:value-of select="role" /></td></tr>
						<tr><th>Batting Style</th><td><xsl:value-of select="battingStyle" /></td></tr>
						<tr><th>Bowling Style</th><td><xsl:value-of select="bowlingStyle" /></td></tr>
						<tr><th>Height</th><td><xsl:value-of select="height" /></td></tr>
					</table>
				</td>
				<td class="picture">
					<div>  
						<xsl:element name="img">
							<xsl:variable name="id" select="@id" />
							<xsl:choose>
								<xsl:when test="//player[@id = $id]/picture">
									<xsl:attribute name="src">./images/<xsl:value-of select="//player[@id = $id]/picture" /></xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="src">./images/bob.jpg</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:attribute name="style">border: 1px solid black;</xsl:attribute>
							<xsl:attribute name="height">150</xsl:attribute>
							<xsl:attribute name="width">117</xsl:attribute>
						</xsl:element>
					</div>				
				</td>
				<td style="widthL 100px;">
					<span> </span>
				</td>
			</tr>
		</table>
	</xsl:template>

	<!-- ================================================================================================= -->	
	<!-- Batting records -->
	<!-- ================================================================================================= -->
	<xsl:template match="player" mode="batting-record">
		<xsl:variable name="id" select="@id" />
	
		<table class="stats" cellpadding="2" cellspacing="0">
			<caption class="stats">Batting Statistics</caption>
			<tr>
				<th class="season" rowspan="2">Season</th>
				<th class="lb num2"   rowspan="2">Inns</th>
				<th class="lb centre" colspan="8">Player</th>
				<th class="lb centre" colspan="5">Partnership</th>
				<th class="lb centre" colspan="5">Out</th>

				<th class="lb num2" rowspan="2">Dot</th>
				<th class="num2" rowspan="2">6s</th>
				<th class="num2" rowspan="2">W</th>
				<th class="num2" rowspan="2">NB</th>
				<th class="num2" rowspan="2">Ext</th>
			</tr>
			<tr class="bb">
				<th class="lb num3">Runs</th>
				<th class="num3">BF</th>
				<th class="num3">Avg</th>
				<th class="num2">HS</th>
				<th class="num2">LS</th>
				<th class="num2">&lt;0</th>
				<th class="num2">&gt;15</th>
				<th class="num2">&gt;25</th>
				
				<th class="lb num3">Runs</th>
				<th class="num3">Avg</th>
				<th class="num2">HS</th>
				<th class="num2">LS</th>
				<th class="num2">&gt;30</th>
				
				<th class="lb num2">Total</th>
				<th class="num2">B</th>
				<th class="num2">C</th>
				<th class="num2">RO</th>
				<th class="num2">S</th>
			</tr>
			
			<xsl:for-each select="//season">
				<xsl:variable name="seasonId" select="@id" />
				
				<xsl:if test="count(//innings[@player = $id and ../../@seasonId = $seasonId])">
					<tr>
						<td class="season"><xsl:value-of select="@name" /><xsl:value-of select="' '" /><xsl:value-of select="@year" /> (<xsl:value-of select="@grade" />)</td>
						<xsl:call-template name="bat-summary-line">
							<xsl:with-param name="innsSet" select="//innings[@player = $id and ../../@seasonId = $seasonId]" />
							<xsl:with-param name="psSet" select="//partnership[innings/@player = $id and ../@seasonId = $seasonId]" />
							<xsl:with-param name="id" select="$id" />
						</xsl:call-template>
					</tr>
				</xsl:if>
			</xsl:for-each>
			<tr class="total">
				<td> <i> Total</i></td>
				<xsl:call-template name="bat-summary-line">
					<xsl:with-param name="innsSet" select="//innings[@player = $id]" />
					<xsl:with-param name="psSet" select="//partnership[innings/@player = $id]" />
					<xsl:with-param name="id" select="$id" />
				</xsl:call-template>
			</tr>
		</table>
	</xsl:template>

	<xsl:template name="bat-summary-line">
		<xsl:param name="innsSet" />
		<xsl:param name="psSet" />
		<xsl:param name="id" />

		<xsl:variable name="balls" select="$innsSet//ball[../@player = $id]" />
		<xsl:variable name="inns" select="count($innsSet)" />
		<xsl:variable name="runs" select="sum($innsSet/@score)" />
		<xsl:variable name="bf" select="count($balls)" />
		<xsl:variable name="psRuns" select="sum($psSet/@score)" />
		
		<td class="lb num2"><xsl:value-of select="$inns" /></td>
		<td class="lb num3"><xsl:value-of select="$runs" /></td>
		<td class="num3"><xsl:value-of select="$bf" /></td>
		<td class="num3"><xsl:value-of select="sw:divide($runs, $inns)" /></td>
		<td class="num2"><xsl:value-of select="max($innsSet/@score)" /></td>
		<td class="num2"><xsl:value-of select="min($innsSet/@score)" /></td>
		<td class="num2"><xsl:value-of select="count($innsSet[number(@score) &lt;= 0])" /></td>
		<td class="num2"><xsl:value-of select="count($innsSet[number(@score) &gt;= 15 and number(@score) &lt; 25])" /></td>
		<td class="num2"><xsl:value-of select="count($innsSet[number(@score) &gt;= 25])" /></td>

		<td class="lb num3"><xsl:value-of select="$psRuns" /></td>
		<td class="num3"><xsl:value-of select="sw:divide($psRuns, $inns)" /></td>
		<td class="num2"><xsl:value-of select="max($psSet/@score)" /></td>
		<td class="num2"><xsl:value-of select="min($psSet/@score)" /></td>
		<td class="num2"><xsl:value-of select="count($psSet[number(@score) &gt;= 30])" /></td>
		
		<td class="lb num2"><xsl:value-of select="count($balls[@runs = '-5'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'B'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'C'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'R'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'S'])" /></td>

		<td class="lb num2"><xsl:value-of select="count($balls[@score = '0'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[number(@score) > 6])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[starts-with(@score, 'W')])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[starts-with(@score, 'NB')])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[starts-with(@score, 'W') or starts-with(@score, 'NB')]) * 2" /></td>
		
	</xsl:template>
	
	
	<!-- ================================================================================================= -->	
	<!-- Bowling records -->
	<!-- ================================================================================================= -->
	<xsl:template match="player" mode="bowling-record">
		<xsl:variable name="id" select="@id" />
	
		<table class="stats" cellpadding="2" cellspacing="0">
			<caption class="stats">Bowling Statistics</caption>
			<tr>
				<th class="left">Season</th>
				
				<th class="lb num3">Overs</th>
				<th class="num3">Balls</th>
				<th class="num3">RC</th>
				<th class="num3">RPO</th>
				<th class="num3">Best</th>
				<th class="num3">Worst</th>
				
				<th class="lb num2">Wick</th>
				<th class="num2">B</th>
				<th class="num2">C</th>
				<th class="num2">RO</th>
				<th class="num2">S</th>

				<th class="lb num2">Dot</th>
				<th class="num2">6s</th>

				<th class="num2">W</th>
				<th class="num2">NB</th>
				<th class="num2">Ext</th>
				
				<th class="lb num2">&lt;0</th>
				<th class="num2">&gt;12</th>
			</tr>
			
			<xsl:for-each select="//season">
				<xsl:variable name="seasonId" select="@id" />
				
				<xsl:if test="count(//over[@player = $id and ../@seasonId = $seasonId])">
					<tr>
						<td class="season"><xsl:value-of select="@name" /><xsl:value-of select="' '" /><xsl:value-of select="@year" /> (<xsl:value-of select="@grade" />)</td>
						<xsl:call-template name="bowl-summary-line">
							<xsl:with-param name="overSet" select="//over[@player = $id and ../@seasonId = $seasonId]" />
							<xsl:with-param name="id" select="$id" />
						</xsl:call-template>
					</tr>
				</xsl:if>
			</xsl:for-each>
			<tr class="total">
				<td class="season"> <i> Total</i></td>
				<xsl:call-template name="bowl-summary-line">
					<xsl:with-param name="overSet" select="//over[@player = $id]" />
					<xsl:with-param name="id" select="$id" />
				</xsl:call-template>
			</tr>
		</table>
	</xsl:template>

	<xsl:template name="bowl-summary-line">
		<xsl:param name="overSet" />
		<xsl:param name="id" />

		<xsl:variable name="balls" select="$overSet//ball[../@player = $id]" />
		<xsl:variable name="overs" select="count($overSet)" />
		<xsl:variable name="runs" select="sum($overSet/@score)" />
		<xsl:variable name="bf" select="count($balls)" />
		
		<td class="lb num3"><xsl:value-of select="$overs" /></td>
		<td class="num3"><xsl:value-of select="$bf" /></td>
		<td class="num3"><xsl:value-of select="$runs" /></td>
		<td class="num3"><xsl:value-of select="sw:divide($runs, $overs)" /></td>
		<td class="num3"><xsl:value-of select="min($overSet/@score)" /></td>
		<td class="num3"><xsl:value-of select="max($overSet/@score)" /></td>

		<td class="lb num2"><xsl:value-of select="count($balls[@runs = '-5'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'B'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'C'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'R'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[@score = 'S'])" /></td>

		<td class="lb num2"><xsl:value-of select="count($balls[@score = '0'])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[number(@score) > 6])" /></td>

		<td class="num2"><xsl:value-of select="count($balls[starts-with(@score, 'W')])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[starts-with(@score, 'NB')])" /></td>
		<td class="num2"><xsl:value-of select="count($balls[starts-with(@score, 'W') or starts-with(@score, 'NB')]) * 2" /></td>
		
		<td class="lb num2"><xsl:value-of select="count($overSet[number(@score) &lt;= 0])" /></td>
		<td class="num2"><xsl:value-of select="count($overSet[number(@score) &gt;= 12])" /></td>
	</xsl:template>


	<!-- ================================================================================================= -->	
	<!-- Player profile / Batting partners 																   -->
	<!-- ================================================================================================= -->
	<xsl:template match="player" mode="partners">
		<xsl:variable name="id" select="@id" />
		<xsl:variable name="ps-set" select="//partnership[innings/@player = $id]" />
		
		<table class="stats" cellpadding="2" cellspacing="0">
			<caption class="stats">Batting Partners</caption>
			<thead>
				<tr>
					<th class="num" rowspan="2">Pos</th>
					<th class="text" rowspan="2">Partner</th>
					<th class="num" rowspan="2">Inns</th>
					<th class="num" rowspan="2">Runs</th>
					<th class="num" rowspan="2">Balls</th>
					<th style="text-align:center;" colspan="5">Contribution</th>
				</tr>
				<tr>
					<th class="num">Avg</th>
					<th class="num">Runs</th>
					<th class="num">BF</th>
					<th class="num">Runs%</th>
					<th class="num">BF%</th>
				</tr>
			</thead>

			<tbody>
				<xsl:for-each-group select="$ps-set" group-by="innings[@player != $id]/@player">
					<xsl:sort select="sum(current-group()/@score)" data-type="number" order="descending" />
					
					<xsl:variable name="partner" select="current-group()[1]/innings[@player !=  $id]/@player" />
					<xsl:variable name="totRuns" select="sum(current-group()/@score)" />
					<xsl:variable name="totBf" select="count(current-group()//ball)" />
					
					<tr>
						<td class="num"><xsl:value-of select="position()" /></td>
						<td class="text"><xsl:copy-of select="sw:fullname($partner)" /></td>
						<td class="number"><xsl:value-of select="count(current-group())" /></td>
						<td class="number"><xsl:value-of select="$totRuns" /></td>
						<td class="number"><xsl:value-of select="$totBf" /></td>
						<td class="number"><xsl:value-of select="sw:divide(sum(current-group()/@score), count(current-group()))" /></td>
						<td class="number"><xsl:value-of select="sum(current-group()/innings[@player =  $id]/@score)" /></td>
						<td class="number"><xsl:value-of select="sum(current-group()/innings[@player =  $id]/@balls)" /></td>
						<td class="number"><xsl:value-of select="sw:perc(sum(current-group()/innings[@player =  $id]/@score), $totRuns, '0.0')" />%</td>
						<td class="number"><xsl:value-of select="sw:perc(sum(current-group()/innings[@player =  $id]/@balls), $totBf, '0.0')" />%</td>
					</tr>
				</xsl:for-each-group>
			</tbody>
			
			<tfoot>
				<xsl:variable name="totRuns2" select="sum($ps-set/@score)" />
				<xsl:variable name="totBf2" select="count($ps-set//ball)" />
				<tr>
					<th class="text" colspan="2">Total</th>
					<th class="number"><xsl:value-of select="count($ps-set)" /></th>
					<th class="number"><xsl:value-of select="sum($ps-set/@score)" /></th>
					<th class="number"><xsl:value-of select="sum($ps-set/innings/@balls)" /></th>
					<th class="number"><xsl:value-of select="sw:divide(sum($ps-set/@score), count($ps-set))" /></th>
					<th class="number"><xsl:value-of select="sum($ps-set/innings[@player =  $id]/@score)" /></th>
					<th class="number"><xsl:value-of select="sum($ps-set/innings[@player =  $id]/@balls)" /></th>
					<th class="number"><xsl:value-of select="sw:perc(sum($ps-set/innings[@player =  $id]/@score), $totRuns2, '0.0')" />%</th>
					<th class="number"><xsl:value-of select="sw:perc(sum($ps-set/innings[@player =  $id]/@balls), $totBf2, '0.0')" />%</th>
				</tr>
			</tfoot>
		</table>
	</xsl:template>

	<!-- ================================================================================================= -->	
	<!-- Player profile / profile -->
	<!-- ================================================================================================= -->
	<xsl:template match="player" mode="profile">
		<xsl:variable name="id" select="@id" />
	
		<table class="stats" cellpadding="2" cellspacing="0">
			<caption class="stats">Profile</caption>
			<tr><td class="profile">Debut</td><td><xsl:value-of select="debut" /></td></tr>
			<tr><td class="profile">Last Game</td><td><xsl:value-of select="lastGame" /></td></tr>
			<tr><td class="profile">Net Runs</td><td><xsl:value-of select="sum(//innings[@player = $id]/@score) - sum(//over[@player = $id]/@score)" /></td></tr>
			<tr><td class="profile">Special Move</td><td><xsl:value-of select="specialMove" /></td></tr>
			<tr><td class="profile">Profile</td><td><xsl:value-of select="profile" /></td></tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >

	<!-- ======================================================================================= -->	
	<!-- All batting records -->
	<!-- ======================================================================================= -->	
	<xsl:template name="bat-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-inns-best</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Top <xsl:value-of select="$ind-row-count" /> Innings</caption>
					<xsl:call-template name="detailed-header" />
					<xsl:apply-templates select="//innings" mode="detailed">
						<xsl:with-param name="rows" select="$ind-row-count" />
						<xsl:sort select="@score" order="descending" data-type="number" />
						<xsl:sort select="count(ball)" order="ascending" data-type="number" />
						<xsl:sort select="@ordinal" order="ascending" data-type="number" />
					</xsl:apply-templates>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-bf-inns</xsl:with-param>
			<xsl:with-param name="doc">
			<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Top <xsl:value-of select="$ind-row-count" /> Balls Faced In An Innings</caption>
					<xsl:call-template name="detailed-header" />
					<xsl:apply-templates select="//innings" mode="detailed">
						<xsl:with-param name="rows" select="$ind-row-count" />
						<xsl:sort select="@balls" order="descending" data-type="number" />
						<xsl:sort select="@score" order="ascending" data-type="number" />
						<xsl:sort select="@ordinal" order="ascending" data-type="number" />
					</xsl:apply-templates>
				</table>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-least-bf-inns</xsl:with-param>
			<xsl:with-param name="doc">
			<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Lowest <xsl:value-of select="$ind-row-count" /> Balls Faced In An Innings</caption>
					<xsl:call-template name="detailed-header" />
					<xsl:apply-templates select="//innings" mode="detailed">
						<xsl:with-param name="rows" select="$ind-row-count" />
						<xsl:sort select="@balls" order="ascending" data-type="number" />
						<xsl:sort select="@score" order="descending" data-type="number" />
						<xsl:sort select="@ordinal" order="ascending" data-type="number" />
					</xsl:apply-templates>
				</table>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-inns-worst</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<xsl:call-template name="detailed-header" />
					<caption>Batting - Bottom <xsl:value-of select="$ind-row-count" /> Innings</caption>
					<xsl:apply-templates select="//innings" mode="detailed">
						<xsl:with-param name="rows" select="$ind-row-count" />
						<xsl:sort select="@score" order="ascending" data-type="number" />
						<xsl:sort select="count(ball)" order="ascending" data-type="number" />
						<xsl:sort select="@ordinal" order="ascending" data-type="number" />
					</xsl:apply-templates>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		

		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-runs</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Runs</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//innings" />
						<xsl:with-param name="ball-set" select="//innings/ball" />
						<xsl:with-param name="group-func" select="'aggregate'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-bf</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Balls Faced</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//innings" />
						<xsl:with-param name="ball-set" select="//innings/ball" />
						<xsl:with-param name="group-func" select="'frequency'" />
						<xsl:with-param name="collection-header" select="'Innings'" />
						<xsl:with-param name="value-header" select="'Balls'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-6s</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most 6s Hit</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//innings" />
						<xsl:with-param name="ball-set" select="//innings/ball[@runs > 6]" />
						<xsl:with-param name="group-func" select="'frequency'" />
						<xsl:with-param name="collection-header" select="'Innings'" />
						<xsl:with-param name="value-header" select="'6s'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-dots</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Dot Balls</caption>
					<xsl:call-template name="generic-most">
						<xsl:with-param name="summary-set" select="//innings" />
						<xsl:with-param name="ball-set" select="//innings/ball[@runs = 0]" />
						<xsl:with-param name="group-func" select="'frequency'" />
						<xsl:with-param name="collection-header" select="'Innings'" />
						<xsl:with-param name="value-header" select="'Dots'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-outs</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Times Out</caption>
					<xsl:call-template name="most-out">
						<xsl:with-param name="summary-set" select="//innings" />
						<xsl:with-param name="collection-header" select="'Inns'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		  

		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-run-outs</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>
						Batting - Run-out Liability Factors<br/>
						<small>(Higher ratio = bigger liability)</small>
					</caption>
					<xsl:call-template name="run-outs">
						<xsl:with-param name="summary-set" select="//innings" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		  

		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-summary-career</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting Summary - Career</caption>
					<xsl:call-template name="bat-team-summary">
						<xsl:with-param name="summary-set" select="//innings" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-summary-season</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting Summary - Season</caption>
					<xsl:call-template name="bat-team-summary">
						<xsl:with-param name="summary-set" select="//innings[../../@seasonId &gt;= $current-season]" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-best-over</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Runs Off Over</caption>
					<xsl:call-template name="bat-best-over">
						<xsl:with-param name="summary-set" select="//partnership" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>



	<!-- ======================================================================================= -->	
	<!-- Best Over while batting -->
	<!-- ======================================================================================= -->	
	<xsl:template name="bat-best-over">
		<xsl:param name="summary-set" />
		
		<tr>
			<th class="left num3">#</th>
			<th class="centre" colspan="4">Batsmen / Runs</th>
			<th class="num3">Runs</th>
			<th class="num3">P'ship</th>
			<th class="num3">Over</th>
			<xsl:copy-of select="sw:match-header()" />
		</tr>
		
		<xsl:for-each-group select="$summary-set/innings/ball" group-by="concat(../../@id, '-', sw:over-ordinal(@ordinal))">
			<xsl:sort select="sum(current-group()/@runs)" order="descending" data-type="number" />
			
			<xsl:variable name="p1" select="current-group()/../../@player1" />
			<xsl:variable name="p2" select="current-group()/../../@player2" />

			<xsl:if test="sum(current-group()/@runs) &gt; 16">
				<tr id="[{$p1}][{$p2}]">
					<td class="numleft" nowrap="nowrap">
						<xsl:value-of select="position()" />
						<xsl:copy-of select="sw:is-new(../../../@date)" />
					</td>
					<td class="text"><xsl:copy-of select="sw:fullname($p1)" /></td>
					<td class="num3"><xsl:value-of select="sum(current-group()[../@player = $p1]/@runs)" /></td>
					<td class="text"><xsl:copy-of select="sw:fullname($p2)" /></td>
					<td class="num3"><xsl:value-of select="sum(current-group()[../@player = $p2]/@runs)" /></td>
					<td class="num3 focus-col"><xsl:value-of select="sum(current-group()/@runs)" /></td>
					<td class="num3"><xsl:value-of select="sw:ordinal(../../@ordinal)" /></td>
					<td class="num3"><xsl:value-of select="sw:ordinal(sw:over-ordinal(@ordinal))" /></td>
					<xsl:copy-of select="sw:match-details(../../..)" />
				</tr>
			</xsl:if>
		</xsl:for-each-group>
	</xsl:template>

	<!-- ======================================================================================= -->	
	<!-- Team batting summary -->
	<!-- ======================================================================================= -->	
	<xsl:template name="bat-team-summary">
		<xsl:param name="summary-set" />

		<thead>
			<tr>
				<th abbr="num" class="numleft">#</th>
				<th abbr="link" class="text">Batsman</th>
				<th abbr="num" class="number">Inns</th>
				<th abbr="num" class="number">Runs</th>
				<th abbr="num" class="number">Balls</th>
				<th abbr="num" class="number">Avg</th>
				<th abbr="num" class="number">SR</th>
				<th abbr="num" class="number">Wick</th>
				<th abbr="num" class="number">6s</th>
				<th abbr="num" class="number">Ext</th>
				<th abbr="num" class="number">Gross Runs</th>
				<th abbr="num" class="number">Gross Avg</th>
				<th abbr="num" class="number">Gross S/R</th>				
			</tr>
		</thead>

		<tbody>
			<xsl:for-each-group select="$summary-set" group-by="@player">
				<xsl:sort select="sum(current-group()/@score)" data-type="number" order="descending" />
				<xsl:sort select="count(current-group())" data-type="number" order="ascending" />
			
				<xsl:variable name="player" select="@player" />
				<xsl:variable name="inns" select="count(current-group())" />
				<xsl:variable name="runs" select="sum(current-group()/@score)" />
				<xsl:variable name="bf"   select="count(current-group()/ball)" />
				<xsl:variable name="wick" select="count(current-group()/ball[@runs = -5])" />
				<xsl:variable name="sixes" select="count(current-group()/ball[@runs &gt;= 7])" />
				<xsl:variable name="ext" select="count(current-group()/ball[starts-with(@score, 'W') or starts-with(@score, 'NB')])" />
				
				<tr id="[{@player}]">
					<td class="numleft"><xsl:value-of select="position()" /></td>
					<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
					<td class="number"><xsl:value-of select="$inns" /></td>
					<td class="focus-col number"><xsl:value-of select="$runs" /></td>
					<td class="number"><xsl:value-of select="$bf" /></td>
					<td class="number"><xsl:value-of select="format-number($runs div $inns, '##0.00')" /></td>
					<td class="number"><xsl:value-of select="format-number(($runs div $bf) * 100, '#0.00')" /></td>
					<td class="number"><xsl:value-of select="$wick" /></td>
					<td class="number"><xsl:value-of select="$sixes" /></td>
					<td class="number"><xsl:value-of select="$ext" /></td>
					<td class="number"><xsl:value-of select="$runs + ($wick * 5)" /></td>
					<td class="number"><xsl:value-of select="format-number(($runs + ($wick * 5)) div $wick, '0.00')" /></td>
					<td class="number"><xsl:value-of select="format-number(($runs + ($wick * 5)) div ($bf div 100), '0.00')" /></td>
				</tr>
			</xsl:for-each-group>
		</tbody>
	</xsl:template>
	

	<!-- ====================================================================================== -->	
	<!-- Display a detailed line for a single innings 											-->
	<!-- ====================================================================================== -->	
	<xsl:template name="detailed-header">
		<tr>
			<th class="number left">#</th>
			<th class="text">Batsman</th>
			<th class="number">Runs</th>
			<th class="number">Balls</th>
			<th class="number">SR</th>
			<th class="number">Pos</th>
			<th class="number">PS</th>
			<xsl:copy-of select="sw:match-header()" />
		</tr>
	</xsl:template>
	
	<xsl:template match="innings" mode="detailed">
		<xsl:param name="rows" select="1" />
	
		<xsl:if test="position() &lt;= $rows">
			<tr id="[{@player}]">
				<xsl:variable name="runs" select="@score" />
				<xsl:variable name="bf"   select="count(ball)" />
				<xsl:variable name="seasonId" select="../../@seasonId" />
				
				<td class="numleft" nowrap="nowrap">
					<xsl:value-of select="position()" />
					<xsl:copy-of select="sw:is-new(.)" />
				</td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number focus-col"><xsl:value-of select="@score" /></td>
				<td class="number"><xsl:value-of select="count(ball)" /></td>
				<td class="number"><xsl:value-of select="format-number(($runs div $bf) * 100, '#0.00')" /></td>
				<td class="number"><xsl:value-of select="sw:ordinal(../@ordinal)" /></td>
				<td class="number"><xsl:value-of select="../@score" /></td>
				<xsl:copy-of select="sw:match-details(../..)" />
			</tr>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
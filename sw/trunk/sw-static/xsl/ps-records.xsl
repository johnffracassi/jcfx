<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >

	<!-- ======================================================================================= -->	
	<!-- All batting records -->
	<!-- ======================================================================================= -->	
	<xsl:template name="ps-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-ps-best</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Top <xsl:value-of select="$ps-row-count" /> Partnerships</caption>
					<xsl:call-template name="partnerships">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="order" select="'descending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-ps-worst</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Bottom <xsl:value-of select="$ps-row-count" /> Partnerships</caption>
					<xsl:call-template name="partnerships">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="order" select="'ascending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-most-ps-runs</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Partnership Runs</caption>
					<xsl:call-template name="ps-agg">
						<xsl:with-param name="summary-set" select="//partnership" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-ps-combo-agg</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table id="sortTable" class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Batting - Most Successful Partnership Combinations</caption>
					<xsl:call-template name="ps-combo-agg">
						<xsl:with-param name="summary-set" select="//partnership" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-ps-worst-against</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption><xsl:value-of select="$ps-row-count" /> Biggest Partnerships Conceeded</caption>
					<xsl:call-template name="ps-against">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="order" select="'descending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">bat-ps-best-against</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption><xsl:value-of select="$ps-row-count" /> Smallest Partnerships Conceeded</caption>
					<xsl:call-template name="ps-against">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="order" select="'ascending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">ps-skin-win</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Biggest Skin Wins</caption>
					<xsl:call-template name="ps-skins">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="order" select="'descending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">ps-skin-loss</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Biggest Skin Losses</caption>
					<xsl:call-template name="ps-skins">
						<xsl:with-param name="match-set" select="//match" />
						<xsl:with-param name="order" select="'ascending'" />
					</xsl:call-template>
				</table>
			</xsl:with-param>
		</xsl:call-template>
		
	</xsl:template>

	
	<!-- ======================================================================================= -->	
	<!-- Partnership combination aggregates -->
	<!-- ======================================================================================= -->	
	<xsl:template name="ps-combo-agg">
		<xsl:param name="summary-set" />
	
		<thead>
			<tr>
				<th abbr="num" class="number left">Rank</th>
				<th class="text">Batsman 1</th>
				<th class="num">Runs</th>
				<th class="num">BF</th>
				<th class="num">Runs%</th>
				<th class="num">BF%</th>
				<th class="text">Batsman 2</th>
				<th class="num">Runs</th>
				<th class="num">BF</th>
				<th class="num">Runs%</th>
				<th class="num">BF%</th>
				<th abbr="num">Inns</th>
				<th abbr="num">Runs</th>
				<th abbr="num">Average</th>
				<th abbr="num">High</th>
				<th abbr="num">Low</th>
			</tr>
		</thead>
	
		<tbody>
			<xsl:for-each-group select="$summary-set" group-by="concat(@player1, '-', @player2)">
				<xsl:sort select="sum(current-group()/@score)" order="descending" data-type="number" />
			
				<xsl:if test="position() &lt;= $ps-row-count">
					<xsl:element name="tr">
						<xsl:attribute name="id">
							<xsl:value-of select="concat('[', innings[1]/@player, '][', innings[2]/@player, ']')" />
						</xsl:attribute>
	
						<xsl:variable name="p1" select="@player1" />
						<xsl:variable name="p2" select="@player2" />
						<xsl:variable name="runs" select="sum(current-group()/@score)" />
						<xsl:variable name="bf" select="count(current-group()//ball)" />
					
						<td class="numleft"><xsl:value-of select="position()" /></td>

						<td class="text"><xsl:copy-of select="sw:fullname(@player1)" /></td>
						<td class="number"><xsl:value-of select="sum(current-group()/innings[@player = $p1]/@score)" /></td>
						<td class="number"><xsl:value-of select="count(current-group()/innings[@player = $p1]//ball)" /></td>
						<td class="number"><xsl:value-of select="sw:perc(sum(current-group()/innings[@player = $p1]/@score), $runs, '0.0')" />%</td>
						<td class="number"><xsl:value-of select="sw:perc(count(current-group()/innings[@player = $p1]//ball), $bf, '0.0')" />%</td>
						
						<td class="text"><xsl:copy-of select="sw:fullname(@player2)" /></td>
						<td class="number"><xsl:value-of select="sum(current-group()/innings[@player = $p2]/@score)" /></td>
						<td class="number"><xsl:value-of select="count(current-group()/innings[@player = $p2]//ball)" /></td>
						<td class="number"><xsl:value-of select="sw:perc(sum(current-group()/innings[@player = $p2]/@score), $runs, '0.0')" />%</td>
						<td class="number"><xsl:value-of select="sw:perc(count(current-group()/innings[@player = $p2]//ball), $bf, '0.0')" />%</td>

						<td class="number"><xsl:value-of select="count(current-group())" /></td>
						<td class="number"><xsl:value-of select="sum(current-group()/@score)" /></td>
						<td class="number"><xsl:value-of select="format-number(sum(current-group()/@score) div count(current-group()), '0.00')" /></td>
						<td class="number"><xsl:value-of select="max(current-group()/@score)" /></td>
						<td class="number"><xsl:value-of select="min(current-group()/@score)" /></td>
					</xsl:element>
				</xsl:if>
			</xsl:for-each-group>
		</tbody>
	</xsl:template>
	
	
	<!-- ======================================================================================= -->	
	<!-- Partnership aggregates -->
	<!-- ======================================================================================= -->	
	<xsl:template name="ps-agg">
		<xsl:param name="summary-set" select="//partnership" />

		<xsl:variable name="nodeset">
			<xsl:element name="results">
				<xsl:for-each select="//player">
					<xsl:variable name="player" select="@id" />
					<xsl:element name="result">
						<xsl:attribute name="player"><xsl:value-of select="@id" /></xsl:attribute>
						<xsl:attribute name="value"><xsl:value-of select="sum($summary-set[innings/@player = $player]/@score)" /></xsl:attribute>
						<xsl:attribute name="inns"><xsl:value-of select="count($summary-set[innings/@player = $player])" /></xsl:attribute>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:variable> 

		<xsl:for-each select="exsl:node-set($nodeset)//result[@value > 0]">
			<xsl:sort select="@value"  order="descending" data-type="number" />
			<xsl:sort select="@inns"   order="ascending"  data-type="number" />
			<xsl:sort select="@player" order="ascending"  data-type="text" />
			
			<xsl:element name="tr">
				<xsl:attribute name="id">
					<xsl:value-of select="concat('[', innings[1]/@player, '][', innings[2]/@player, ']')" />
				</xsl:attribute>
				<td class="number"><xsl:value-of select="position()" /></td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number"><xsl:value-of select="@inns" /></td>
				<td class="number"><xsl:value-of select="@value" /></td>
				<td class="number"><xsl:value-of select="format-number((@value div @inns), '0.00')" /></td>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>

	
	<!-- ======================================================================================= -->	
	<!-- Partnerships -->
	<!-- ======================================================================================= -->	
	<xsl:template name="partnerships">
		<xsl:param name="match-set" />
		<xsl:param name="order" />
		
		<tr>
			<th class="number">#</th>
			<th class="text" colspan="2" width="250">Batsman</th>
			<th class="number">Pos</th>
			<th class="number">Runs</th>
			<xsl:copy-of select="sw:match-header()" />
		</tr>

		<xsl:for-each select="$match-set//partnership">
			<xsl:sort select="@score" order="{$order}" data-type="number" />
			
			<xsl:if test="position() &lt;= $ps-row-count">
				<tr id="[{@player1}][{@player2}]">
					<xsl:variable name="seasonId" select="../@seasonId" />
					
					<td class="numleft" nowrap="nowrap">
						<xsl:value-of select="position()" />
						<xsl:copy-of select="sw:is-new(.)" />
					</td>
					<td class="text"><xsl:copy-of select="sw:fullname(@player1)" /> (<xsl:value-of select="innings[1]/@score" />)</td>
					<td class="text"><xsl:copy-of select="sw:fullname(@player2)" /> (<xsl:value-of select="innings[2]/@score" />)</td>
					<td class="number"><xsl:value-of select="@ordinal" /></td>
					<td class="focus-col number"><xsl:value-of select="@score" /></td>
					<xsl:copy-of select="sw:match-details(..)" />
				</tr>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>


	<!-- ======================================================================================= -->	
	<!-- Partnerships -->
	<!-- ======================================================================================= -->	
	<xsl:template name="ps-against">
		<xsl:param name="match-set" />
		<xsl:param name="order" />

		<tr>
			<th class="numleft">#</th>
			<th class="numleft">Position</th>
			<th class="number">Score</th>
			<xsl:copy-of select="sw:match-header()" />
			<th class="text">Courtesy Of...</th>
		</tr>
		
		<xsl:for-each-group select="$match-set//over" group-by="(../@id * 100) + sw:over-to-ps-ord(@ordinal)" >
			<xsl:sort select="sum(current-group()/@score)" data-type="number" order="{$order}" />
			
			<xsl:if test="position() &lt; $ps-row-count">
				<xsl:variable name="id">
					<xsl:for-each select="current-group()">[<xsl:value-of select="@player" />]</xsl:for-each>
				</xsl:variable>

				<tr id="{$id}">
					<td class="numleft" nowrap="nowrap">
						<xsl:value-of select="position()" />
						<xsl:copy-of select="sw:is-new(current-group()[1]/../@date)" />
					</td>
					<td class="numleft"><xsl:value-of select="sw:ordinal(sw:over-to-ps-ord(@ordinal))" /></td>
					<td class="number"><xsl:value-of select="sum(current-group()/@score)" /></td>
					<xsl:copy-of select="sw:match-details(current-group()[1]/..)" />
					
					<td>
						<xsl:for-each select="current-group()">
							<xsl:copy-of select="sw:fullname(@player)" /> (<xsl:value-of select="@score" />)<xsl:if test="position() != last()">, </xsl:if>
						</xsl:for-each>
					</td>
				</tr>
			</xsl:if>
		</xsl:for-each-group>
	</xsl:template>

	<xsl:template name="ps-skins">
		<xsl:param name="match-set" />
		<xsl:param name="order" />
		
		<xsl:variable name="set">
			<xsl:call-template name="ps-skins-set">
				<xsl:with-param name="match-set" select="$match-set" />
			</xsl:call-template>
		</xsl:variable>

		<tr>
			<th class="numleft">#</th>
			<th colspan="2" class="text">Batsmen</th>
			<th class="number">Margin</th>
			<th colspan="2" class="text" style="width: 250px;">Bowlers</th>
			<xsl:copy-of select="sw:match-header()" />
		</tr>		
		
		<xsl:for-each select="$set/results/result">
			<xsl:sort select="@us - @them" order="{$order}" data-type="number" />
			
			<xsl:variable name="id" select="@match-id" />

			<xsl:if test="position() &lt;= $ps-row-count">
				<tr id="[{@bat1}][{@bat2}][{@bwl1}][{@bwl2}][{@bwl3}][{@bwl4}]">
					<td class="numleft" nowrap="nowrap">
						<xsl:value-of select="position()" />
						<xsl:copy-of select="sw:is-new($doc//match[@id = $id]/@date)" />
					</td>
					<td class="text"><xsl:copy-of select="sw:fullname(@bat1)" />, <xsl:copy-of select="sw:fullname(@bat2)" /></td>
					<td class="number"><xsl:value-of select="@us" /></td>
					<td class="number focus-col"><xsl:value-of select="@us - @them" /></td>
					<td class="number"><xsl:value-of select="@them" /></td>
					<td class="text"><xsl:copy-of select="sw:fullname(@bwl1)" />, <xsl:copy-of select="sw:fullname(@bwl2)" />, <xsl:copy-of select="sw:fullname(@bwl3)" />, <xsl:copy-of select="sw:fullname(@bwl4)" /></td>
					<xsl:copy-of select="sw:match-details-id($id)" />
				</tr>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="ps-skins-set">
		<xsl:param name="match-set" />
		
		<results>
			<xsl:for-each-group select="$match-set//over" group-by="(../@id * 100) + sw:over-to-ps-ord(@ordinal)" >
				<xsl:variable name="id" select="current-group()[1]/../@id" />
				<xsl:variable name="ord" select="sw:over-to-ps-ord(@ordinal)" />
				<xsl:variable name="ps" select="$match-set[@id = $id]//partnership[@ordinal = $ord]" />
				
				<result>
					<xsl:attribute name="match-id"><xsl:value-of select="$id" /></xsl:attribute>
					<xsl:attribute name="ordinal"><xsl:value-of select="$ord" /></xsl:attribute>
					<xsl:attribute name="us"><xsl:value-of select="$ps/@score" /></xsl:attribute>
					<xsl:attribute name="bat1"><xsl:value-of select="$ps/@player1" /></xsl:attribute>
					<xsl:attribute name="bat2"><xsl:value-of select="$ps/@player2" /></xsl:attribute>
					<xsl:attribute name="them"><xsl:value-of select="sum(current-group()/@score)" /></xsl:attribute>
					<xsl:for-each select="current-group()">
						<xsl:attribute name="bwl{position()}"><xsl:value-of select="@player" /></xsl:attribute>
					</xsl:for-each>
				</result>
			</xsl:for-each-group>
		</results>
	</xsl:template>


</xsl:stylesheet>
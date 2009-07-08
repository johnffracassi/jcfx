<?xml version="1.0"?>
  
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >
   
	
	<!-- ====================================================================================== -->	
	<!-- Player higlighting selection list 														-->
	<!-- ====================================================================================== -->	
	<xsl:template name="highlight">
		<div align="right" width="100%">
			<form name="playerSelect">
				Highlight player: 
				<select name="player" onChange="playerChanged()">
					<option value="xxx">- Select player -</option>
					<xsl:for-each select="//player">
						<xsl:sort select="sw:realname(@id)"/>
						
						<xsl:element name="option">
							<xsl:attribute name="value"><xsl:value-of select="@id" /></xsl:attribute>
							<xsl:value-of select="sw:realname(@id)" />
						</xsl:element>
					</xsl:for-each>
				</select>
			</form>
		</div>
	</xsl:template>
	
	
	<!-- ====================================================================================== -->	
	<!-- Generic template for number of wickets in a set of balls								-->
	<!-- ====================================================================================== -->	
	<xsl:template name="most-out">
		<xsl:param name="summary-set" />
		<xsl:param name="ball-set" select="$summary-set/ball" />
		<xsl:param name="collection-header" />
	
		<tr>
			<th class="text">Player</th>
			<th class="number"><xsl:value-of select="$collection-header" /></th>
			<th class="number">Bowled</th>
			<th class="number">Caught</th>
			<th class="number">Run Out</th>
			<th class="number">Stumped</th>
			<th class="number">Total</th>
			<th class="number">Avg</th>
		</tr>
	
		<xsl:for-each-group select="$ball-set" group-by="../@player">
			<xsl:sort select="count(current-group()[@runs = '-5'])" order="descending" data-type="number" />
		
			<xsl:variable name="player" select="current-group()[1]/../@player" />
			<xsl:variable name="inns" select="count($summary-set[@player = $player])" />
			<xsl:variable name="out" select="count(current-group()[@runs = '-5'])" />
			
			<tr id="[{$player}]">
				<td class="text"><xsl:copy-of select="sw:fullname($player)" /></td>
				<td class="number"><xsl:value-of select="$inns" /></td>
				<td class="number"><xsl:value-of select="count(current-group()[@score = 'B'])" /></td>
				<td class="number"><xsl:value-of select="count(current-group()[@score = 'C'])" /></td>
				<td class="number"><xsl:value-of select="count(current-group()[@score = 'R'])" /></td>
				<td class="number"><xsl:value-of select="count(current-group()[@score = 'S'])" /></td>
				<td class="focus-col number"><xsl:value-of select="$out" /></td>
				<td class="number"><xsl:value-of select="format-number(($out div $inns), '0.000')" /></td>
			</tr>
		</xsl:for-each-group>
	</xsl:template>


	<xsl:template name="run-outs">
		<xsl:param name="summary-set" />
		<xsl:param name="inns-set" select="$summary-set" />
	
		<tr>
			<th class="text">Player</th>
			<th class="number">Inns</th>
			<th class="number">Times Run Outs</th>
			<th class="number">Partner Run Out</th>
			<th class="number">Total Runs Outs</th>
			<th class="number">RO per P'ship</th>
			<th class="number">Ratio</th>
		</tr>
	
		<xsl:for-each-group select="$inns-set" group-by="@player">
			<xsl:sort data-type="number" order="descending" select="sw:perc(count(current-group()/..//ball[@score = 'R']) - count(current-group()//ball[@score = 'R']), count(current-group()//ball[@score = 'R']), '0.00')" />
		
			<xsl:variable name="player" select="current-group()[1]/@player" />
			<xsl:variable name="inns" select="count($summary-set[@player = $player])" />
			<xsl:variable name="ro" select="count(current-group()//ball[@score = 'R'])" />
			<xsl:variable name="pro" select="count(current-group()/..//ball[@score = 'R']) - $ro" />
			
			<tr id="[{$player}]">
				<td class="text"><xsl:copy-of select="sw:fullname($player)" /></td>
				<td class="number"><xsl:value-of select="$inns" /></td>
				<td class="number"><xsl:value-of select="$ro" /></td>
				<td class="number"><xsl:value-of select="$pro" /></td>
				<td class="number"><xsl:value-of select="$pro + $ro" /></td>
				<td class="number"><xsl:value-of select="sw:perc($pro + $ro, $inns, '0.0')" /></td>
				<td class="focus-col number"><xsl:value-of select="sw:perc($pro, $ro, '0.00')" /></td>
			</tr>
		</xsl:for-each-group>
	</xsl:template>


	<!-- ====================================================================================== -->	
	<!-- Generic template for 'Most xyz' type records 											-->
	<!-- ====================================================================================== -->	
	<xsl:template name="generic-most">
		<xsl:param name="summary-set" select="//innings" />
		<xsl:param name="ball-set" select="$summary-set/ball" />
		<xsl:param name="group-func" />
		<xsl:param name="value-header" />
		<xsl:param name="collection-header" />
		
		<tr>
			<th class="number left">#</th>
			<th class="text">Player</th>
			<th class="number"><xsl:value-of select="$collection-header" /></th>
			<th class="number"><xsl:value-of select="$value-header" /></th>
			<th class="number">Average</th>
		</tr>
		
		<xsl:variable name="nodeset">
			<xsl:element name="results">
				<xsl:for-each select="//player">
					<xsl:variable name="player" select="@id" />
					<xsl:element name="result">
						<xsl:attribute name="player"><xsl:value-of select="$player" /></xsl:attribute>
						
						<xsl:choose>
							<xsl:when test="string($group-func) = string('aggregate')">
								<xsl:attribute name="value"><xsl:value-of select="sum($ball-set[../@player = $player]/@runs)" /></xsl:attribute>
							</xsl:when>
							<xsl:when test="string($group-func) = string('frequency')">
								<xsl:attribute name="value"><xsl:value-of select="count($ball-set[../@player = $player])" /></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="value">ERROR</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:variable> 

		<xsl:for-each select="exsl:node-set($nodeset)//result[@value > 0]">
			<xsl:sort select="@value" order="descending" data-type="number" />
			<xsl:sort select="@player" order="ascending" data-type="text" />
			
			<xsl:variable name="player" select="@player" />
			<xsl:variable name="inns" select="count($summary-set[@player = $player])" />
			
			<tr id="{concat('[', @player, ']')}">
				<td class="number left"><xsl:value-of select="position()" /></td>
				<td class="text"><xsl:copy-of select="sw:fullname(@player)" /></td>
				<td class="number"><xsl:value-of select="$inns" /></td>
				<td class="number"><xsl:value-of select="@value" /></td>
				<td class="number"><xsl:value-of select="format-number((@value div $inns), '0.000')" /></td>
			</tr>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
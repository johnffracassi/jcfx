<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw" 
				extension-element-prefixes="exsl" >


	<xsl:template name="random-all">
		<xsl:call-template name="output">
			<xsl:with-param name="file">most-nicknames</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Most Nicknames</caption>
					<xsl:call-template name="most-nicknames" />
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">most-beers-won</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Most Beers Won</caption>
					<xsl:call-template name="most-beers-won" />
				</table>
			</xsl:with-param>
		</xsl:call-template>


		<xsl:call-template name="output">
			<xsl:with-param name="file">most-lbws</xsl:with-param>
			<xsl:with-param name="doc">
				<xsl:call-template name="highlight" />
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Most Number of LBWs While Batting</caption>
					<xsl:call-template name="most-lbws" />
				</table>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>


	<xsl:template name="most-nicknames">
		<tr>
			<th>#</th>
			<th class="text">Player</th>
			<th>Nicknames</th>
		</tr>
		
		<xsl:for-each select="//player">
			<xsl:sort select="count(nicknames/nickname)" order="descending" data-type="number" />
			
			<tr id="[{@id}]">
				<td class="num2 left"><xsl:value-of select="position()" /></td>
				<td class="text"><xsl:copy-of select="sw:fullname(@id)" /></td>
				<td class="number"><xsl:value-of select="count(nicknames/nickname)" /></td>
			</tr>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="most-beers-won">
		<tr>
			<th class="text">Player</th>
			<th class="num3">Beers</th>
		</tr>
		
		<xsl:for-each select="//player">
			<xsl:sort select="number(beersWon)" order="descending" data-type="number" />
			
			<tr id="[{@id}]">
				<td class="text"><xsl:copy-of select="sw:fullname(@id)" /></td>
				<td class="num3">
					<xsl:choose>
						<xsl:when test="beersWon"><xsl:value-of select="beersWon" /></xsl:when>
						<xsl:otherwise>0</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="most-lbws">
		<tr>
			<th class="text">Player</th>
			<th class="num3">LBWs</th>
		</tr>
		
		<xsl:for-each select="//player">
			<xsl:sort select="number(lbws)" order="descending" data-type="number" />
			
			<tr id="[{@id}]">
				<td class="text"><xsl:copy-of select="sw:fullname(@id)" /></td>
				<td class="num3">
					<xsl:choose>
						<xsl:when test="lbws"><xsl:value-of select="number(lbws)" /></xsl:when>
						<xsl:otherwise>0</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
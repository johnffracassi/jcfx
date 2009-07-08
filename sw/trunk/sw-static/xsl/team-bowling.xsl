<?xml version="1.0"?>
 
<xsl:stylesheet version="1.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw" 
				extension-element-prefixes="exsl" >

	<xsl:template name="team-bowl-all">
		<xsl:call-template name="best-over-breakdown" />
	</xsl:template>

	<xsl:template name="best-over-breakdown">
		<xsl:param name="over-set" select="//over" />
		
		<xsl:call-template name="output">
			<xsl:with-param name="file">over-breakdown</xsl:with-param>
			<xsl:with-param name="doc">
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Overs by Ordinal</caption>
				
					<tr>
						<th class="numleft">Over</th>
						<th class="number">Count</th>
						<th class="number">Runs</th>
						<th class="number">Avg</th>
						<th class="centre" colspan="2">Best</th>
						<th class="centre" colspan="2">Worst</th>
					</tr>
				
					<xsl:for-each-group select="$over-set" group-by="@ordinal">
						<xsl:sort select="@ordinal" data-type="number" order="ascending" />
			
						<xsl:variable name="overs" select="current-group()" />
						<xsl:variable name="cnt" select="count($overs)" />
						<xsl:variable name="runs" select="sum($overs/@score)" />
						<xsl:variable name="best" select="min($overs/@score)" />
						<xsl:variable name="worst" select="max($overs/@score)" />
						<xsl:variable name="best-overs" select="$overs[@score = $best]" />
						<xsl:variable name="worst-overs" select="$overs[@score = $worst]" />
						
						<xsl:element name="tr">
							<xsl:attribute name="class">
								<xsl:if test="position() mod 2 = 0">even</xsl:if>
								<xsl:if test="position() mod 2 = 1">odd</xsl:if>
							</xsl:attribute>
						
							<td class="numleft"><xsl:value-of select="sw:ordinal(@ordinal)" /></td>
							<td class="number"><xsl:value-of select="$cnt" /></td>
							<td class="number"><xsl:value-of select="$runs" /></td>
							<td class="number"><xsl:value-of select="format-number($runs div $cnt, '0.00')" /></td>
							<td class="number"><xsl:value-of select="$best" /></td>
							<td class="text">
								<xsl:for-each select="$best-overs/@player">
									<xsl:copy-of select="sw:fullname(.)" />
									<xsl:if test="position() &lt; last()">, </xsl:if>
								</xsl:for-each>
							</td>
							<td class="number"><xsl:value-of select="$worst" /></td>
							<td class="text">
								<xsl:for-each select="$worst-overs/@player">
									<xsl:copy-of select="sw:fullname(.)" />
									<xsl:if test="position() &lt; last()">, </xsl:if>
								</xsl:for-each>
							</td>
						</xsl:element>
					</xsl:for-each-group>
				</table>
			</xsl:with-param>
		</xsl:call-template>
	
	</xsl:template>
	
	
</xsl:stylesheet>
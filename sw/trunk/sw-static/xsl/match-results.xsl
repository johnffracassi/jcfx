<?xml version="1.0"?>
 
<xsl:stylesheet version="2.0" 
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:exsl="http://exslt.org/common" 
				xmlns:sw="http://www.zendurl.com/sw"
				extension-element-prefixes="exsl" >


	<!-- ================================================================================================= -->	
	<!-- Matches -->
	<!-- ================================================================================================= -->
	<xsl:template match="matches" mode="results">
	
		<xsl:call-template name="output">
			<xsl:with-param name="file">results</xsl:with-param>
			<xsl:with-param name="doc">
				<table class="stats2" border="0" cellspacing="1" cellpadding="3">
					<caption>Match Results</caption>
					
					<tr>
						<th class="number left">Scoresheet</th>
						<th class="text">Team</th>
						<th class="text">Opposition</th>
						<th class="number">For</th>
						<th class="number">Against</th>
						<th class="text">Result</th>
						<th class="date">Date</th>
						<th class="date">Time</th>
					</tr>
					<xsl:apply-templates select="match" mode="results">
						<xsl:sort select="position()" order="descending" data-type="number" />
					</xsl:apply-templates>
				</table>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="match" mode="results">
		<xsl:variable name="bat-runs" select="sum(.//innings/ball/@runs)" />
		<xsl:variable name="bowl-runs" select="sum(.//over/ball/@runs)" />
	
		<xsl:variable name="row-class">
			<xsl:choose>
				<xsl:when test="$bat-runs &gt; $bowl-runs">win</xsl:when>
				<xsl:when test="$bat-runs &lt; $bowl-runs">loss</xsl:when>
				<xsl:when test="$bat-runs = $bowl-runs">tie</xsl:when>
			</xsl:choose>
		</xsl:variable>
	
		<tr class="{$row-class}">
			<td class="number left">
				<a href="result-{@id}.html"><img border="0" src="images/scorecard.png" /></a>
				<xsl:copy-of select="sw:is-new(@date)" />
			</td>
			<td class="text"><xsl:value-of select="@us" /></td>
			<td class="text"><xsl:value-of select="@opposition" /></td>
			<td class="number"><xsl:value-of select="$bat-runs" /></td>
			<td class="number"><xsl:value-of select="$bowl-runs" /></td>
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
			<td class="date"><xsl:value-of select="@date" /></td>
			<td class="date"><xsl:value-of select="@time" /></td>
		</tr>
	</xsl:template>

</xsl:stylesheet>